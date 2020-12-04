//Edit each editor to set the font size
$(function() {
   var editor;
   $('.editor').each(function( index ) {
     editor = ace.edit(this);
     editor.setFontSize("18px");
   });
})

//initialize ace js objects
var beautify = ace.require("ace/ext/beautify"); // get reference to extension
var langTools = ace.require("ace/ext/language_tools");

var urlHost = location.protocol+'//'+location.hostname+(location.port ? ':'+location.port: '');

//initialize ace js editors
var payloadEditor = getEditor("input-editor", "ace/mode/json", false, false);
var dsEditor =  getEditor("datasonnet-editor", "ace/mode/json5", true, false);
var outEditor = getEditor("output-editor", "ace/mode/json5", false, true);

//get keywords from api
var entries=null;
getKeywords();

//build initial completers for autocomplete
var staticWordCompleter = buildCompleter(entries);
var scriptVariablesCompleter = buildCompleter([]);
var payloadVariablesCompleter = buildCompleter([{"name": "payload","value":"payload", "package":""},
                                                {"name": "payload.message","value":"payload.message", "package":""}]);

langTools.setCompleters([staticWordCompleter, scriptVariablesCompleter, payloadVariablesCompleter]);
dsEditor.completers = [staticWordCompleter, scriptVariablesCompleter, payloadVariablesCompleter];

/*******Handles the input change and then posts the data********/
dsEditor.on("input", function(){
    postTransform();
    postGetVariables("/keywords/script", dsEditor.getValue(),"script");
});

payloadEditor.on("input", function(){
    postTransform();
    postGetVariables("/keywords/payload", payloadEditor.getValue(),"payload");
});

function postTransform(){
    var payloadName="payload";
    var payloadContent = Base64.encode(payloadEditor.getValue());
    var payloadContentType="application/json";

    var script=dsEditor.getValue();
    var input={name:payloadName,content:payloadContent,contentType:payloadContentType};
    var postData={inputs:[input],resources:script};

    console.log("Posting...");
    $.ajax({
        method:"POST",
        url: urlHost+"/transform",
        data:JSON.stringify(postData),
        contentType:"application/json; charset=utf-8",
        dataType:"json"
    }).done(function( msg ) {
        console.log(msg);
        if(msg.success){

            if(msg.result.contentType == "application/json"){
                var json = JSON.parse(msg.result.content);
                outEditor.getSession().setMode("ace/mode/json");
                outEditor.setValue(JSON.stringify(json, null, 2));
            }
            else if(msg.result.contentType == "application/xml"){
                outEditor.getSession().setMode("ace/mode/xml");
                outEditor.setValue(prettifyXml(msg.result.content));
            }
            else if(msg.result.contentType == "application/yaml"){
                outEditor.getSession().setMode("ace/mode/yaml");
                outEditor.setValue(msg.result.content);
            }
            else{
                outEditor.getSession().setMode("ace/mode/text");
                outEditor.setValue(msg.result.content);
            }
            outEditor.clearSelection();
        }else{
            outEditor.getSession().setMode("ace/mode/json");
            outEditor.setValue(msg.error.message);
            outEditor.clearSelection();
        }

        var type = msg.success ? msg.result.inputType : msg.error.inputType;
        switch(type){
            case "application/json":
                payloadEditor.getSession().setMode("ace/mode/json");
                break;
            case "application/xml":
                payloadEditor.getSession().setMode("ace/mode/xml");
                break;
            case "application/yaml":
                payloadEditor.getSession().setMode("ace/mode/yaml");
                break;
            default:
                payloadEditor.getSession().setMode("ace/mode/text");
                break;
        }
    });
}

function prettifyXml(xml, tab) { // tab = optional indent value, default is tab (\t)
    var formatted = '', indent= '';
    tab = tab || '\t';
    xml.split(/>\s*</).forEach(function(node) {
        if (node.match( /^\/\w/ )) indent = indent.substring(tab.length); // decrease indent by one 'tab'
        formatted += indent + '<' + node + '>\r\n';
        if (node.match( /^<?\w[^>]*[^\/]$/ )) indent += tab;              // increase indent
    });
    return formatted.substring(1, formatted.length-3);
}

function getEditor(id, mode, advancedAutoComplete, readOnly){
    var editor = ace.edit(document.getElementById(id));
    editor.setTheme(
        getQueryParameterByName("theme").toUpperCase() == "LIGHT" ? "ace/theme/kuroir" : "ace/theme/twilight"
    );
    editor.getSession().setMode(mode);
    editor.setReadOnly(readOnly);
    if(advancedAutoComplete){
        editor.setOptions({
            enableBasicAutocompletion: true,
            enableLiveAutocompletion: true,
            enableSnippets: true
        });
    }
    else{
        editor.setOptions({
            enableBasicAutocompletion: true
        });
    }
    return editor;
}

function getQueryParameterByName(name, url = window.location.href) {
    name = name.replace(/[\[\]]/g, '\\$&');
    var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)'),
        results = regex.exec(url);
    if (!results) return "";
    if (!results[2]) return '';
    return decodeURIComponent(results[2].replace(/\+/g, ' '));
}

function postGetVariables(endpoint, dataIn, type){
    $.ajax({
        method:"POST",
        url: urlHost+endpoint,
        data: dataIn,
        contentType:"text/plain; charset=utf-8",
        async: false
    }).done(function( msg ) {
        if(msg != "[]"){
            if(type == "script"){
                scriptVariablesCompleter = buildCompleter(msg);
            } else { payloadVariablesCompleter = buildCompleter(msg); }
            langTools.setCompleters([staticWordCompleter, scriptVariablesCompleter, payloadVariablesCompleter]);
            dsEditor.completers = [staticWordCompleter, scriptVariablesCompleter, payloadVariablesCompleter];
        }
    }).fail(function( msg ){});
}
/*********End input change and data post**********/

function buildCompleter(inputData){
    var temp = {
               getCompletions: function(editor, session, pos, prefix, callback) {
                   callback(null, inputData.map(function(word) {
                       return {
                           caption: word.name,
                           snippet: word.packageName == undefined ? word.value : word.packageName+"."+word.value,
                           meta: word.packageName,
                           completer: this
                       };
                   }));
               },
               insertMatch: function(editor, data) {
                   editor.forEachSelection(function() {
                       editor.insert(data.caption)
                   })
               }
           };
    return temp;
}

function getKeywords(){

    console.log("Retrieving keywords...");
    $.ajax({
        method:"GET",
        url: urlHost +"/keywords",
        async: false
    }).done(function( msg ) {
        console.log("Successfully retrieved keywords.");
        entries=msg;
    });
}


/***********Start documentation logic************/

/***********End documentation logic************/


$( "#resizeLeft" ).resizable({
    resize: function( event, ui ) {
        payloadEditor.resize();
        $("#resizeRight").width(
            $( ".editors" ).width()-($( "#resizeLeft" ).width() + $( "#resizeMid" ).width())
        );
    },
    handles: "e"
});

$( "#resizeMid" ).resizable({
    resize: function( event, ui ) {
        dsEditor.resize();
        $("#resizeRight").width(
            $( ".editors" ).width()-($( "#resizeLeft" ).width() + $( "#resizeMid" ).width())
        );
    },
    handles: "e"
});