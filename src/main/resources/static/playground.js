$(function() {
   var editor;
   $('.editor').each(function( index ) {
     editor = ace.edit(this);
     editor.setFontSize("18px");
   });
})

var beautify = ace.require("ace/ext/beautify"); // get reference to extension

        ace.require("/ace/ext/language_tools");

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
            enableBasicAutocompletion: true
        });

        var outEditor = ace.edit("output-editor");
        outEditor.setTheme("ace/theme/twilight");
        outEditor.getSession().setMode("ace/mode/json");
        outEditor.setReadOnly(true)
        outEditor.setOptions({
            enableBasicAutocompletion: true
        });

        dsEditor.on("input", function(){
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
//resize editor windows: https://ourcodeworld.com/articles/read/994/how-to-make-an-ace-editor-instance-resizable-by-the-user-dinamically-with-a-drag-and-drop-bar
/* https://gist.github.com/zeffii/2983357
        // get markdown content
        var body_location = 'markdown/README.markdown';

        function getText(myUrl){
            var result = null;
            $.ajax( { url: myUrl,
                      type: 'get',
                      dataType: 'html',
                      async: false,
                      success: function(data) { result = data; }
                    }
            );
            FileReady = true;
            return result;
        }

        var markdown_source = getText(body_location);

        // convert markdown to html
        var output = markdown.toHTML( markdown_source );
        document.write(output);
        */