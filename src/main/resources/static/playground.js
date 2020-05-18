
//Edit each editor to set the font size
$(function() {
   var editor;
   $('.editor').each(function( index ) {
     editor = ace.edit(this);
     editor.setFontSize("18px");
   });
})

var beautify = ace.require("ace/ext/beautify"); // get reference to extension
var langTools = ace.require("ace/ext/language_tools");


var payloadEditor = ace.edit("payload-editor");
payloadEditor.setTheme("ace/theme/twilight");
payloadEditor.getSession().setMode("ace/mode/json");
payloadEditor.setOptions({
    enableBasicAutocompletion: true
});

var dsEditor = ace.edit("ds-editor");
dsEditor.setTheme("ace/theme/twilight");
dsEditor.getSession().setMode("ace/mode/json5");
dsEditor.setOptions({
    enableBasicAutocompletion: true,
    enableLiveAutocompletion: true,
    enableSnippets: true
});

var entries=null;
getKeywords();
getDocs();

var staticWordCompleter = {
    getCompletions: function(editor, session, pos, prefix, callback) {
        //var wordList = ["DW.Core", "map", "flatMap"];
        callback(null, entries.map(function(word) {
            return {
                caption: word.name,
                snippet: word.packageName+"."+word.value,
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
}
langTools.addCompleter(staticWordCompleter);
dsEditor.completers = [staticWordCompleter];

var outEditor = ace.edit("output-editor");
outEditor.setTheme("ace/theme/twilight");
outEditor.getSession().setMode("ace/mode/json");
outEditor.setReadOnly(true)
outEditor.setOptions({
    enableBasicAutocompletion: true
});

/*******Handles the input change and then posts the data********/
var beginTime=null;
dsEditor.on("input", function(){
    beginTime=new Date().getTime();
    console.log(typeof entries)
    postTransform();
});

payloadEditor.on("input", function(){

    postTransform();
});

function postTransform(){
    var payloadName="payload";
    var payloadContent = encodBase64(payloadEditor.getValue());
    var payloadContentType="application/json";

    var script=dsEditor.getValue();
    var input={name:payloadName,content:payloadContent,contentType:payloadContentType};
    var postData={inputs:[input],resources:script};

    console.log("Posting...");
    console.log(postData);
    $.ajax({
        method:"POST",
        url:"http://localhost:8080/transform",
        data:JSON.stringify(postData),
        contentType:"application/json; charset=utf-8",
        dataType:"json"
    }).done(function( msg ) {
        console.log(msg);
        console.log("Elapsed Time:" + ((new Date().getTime()) - beginTime));
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

function encodBase64(value){
    return window.btoa(value);
}

/*********End input change and data post**********/

function getKeywords(){

    console.log("Retrieving keywords...");
    $.ajax({
        method:"GET",
        url:"http://localhost:8080/keywords"
    }).done(function( msg ) {
        console.log("Successfully retrieved keywords.");
        entries=msg;
    });
}


/***********Start documentation logic************/

function getDocs(){

    console.log("Retrieving docs...");
    $.ajax({
        method:"GET",
        url:"http://localhost:8080/docs"
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
