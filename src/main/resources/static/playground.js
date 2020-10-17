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

//get documentation and keywords from api
var entries=null;
getKeywords();
getDocs();

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

    $.ajax({
        method:"POST",
        url: urlHost+"/transform",
        data:JSON.stringify(postData),
        contentType:"application/json; charset=utf-8",
        dataType:"json"
    }).done(function( msg ) {
        if(msg.success){
            if(msg.result.contentType == "application/json"){
                var json = JSON.parse(msg.result.content);
                outEditor.setValue(JSON.stringify(json, null, 2));
            }
            else{
                outEditor.setValue(msg.result.content);
            }
            outEditor.clearSelection();
        }else{
            outEditor.setValue(msg.error.message);
            outEditor.clearSelection();
        }
    });
}


function getEditor(id, mode, advancedAutoComplete, readOnly){
    var editor = ace.edit(document.getElementById(id));
    editor.setTheme("ace/theme/twilight");
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

function postGetVariables(endpoint, dataIn, type){
    $.ajax({
        method:"POST",
        url: urlHost+endpoint,
        data: dataIn,
        contentType:"text/plain; charset=utf-8",
        async: false
    }).done(function( msg ) {
        console.log(msg);
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

function getDocs(){

    $.ajax({
        method:"GET",
        url: urlHost+"/docs"
    }).done(function( msg ) {
        console.log("Successfully retrieved docs.");
        console.log(msg)
        createDocsPage(msg.nav,msg.docs);
    });
}

function createDocsPage(nav,doc){
    var converter = new showdown.Converter();
    document.getElementById("navDocs").innerHTML=converter.makeHtml(nav);
    document.getElementById("mainDocs").innerHTML=converter.makeHtml(doc);
}

var extended=false;
function buttonClick(){
    if(extended){
        $(".docsContainer").css('height','4vh');
        extended=false;
    }
    else{
        $(".docsContainer").css('height','45vh');
        extended=true;
    }
}

/***********End documentation logic************/

//resize editor windows: https://ourcodeworld.com/articles/read/994/how-to-make-an-ace-editor-instance-resizable-by-the-user-dinamically-with-a-drag-and-drop-bar
