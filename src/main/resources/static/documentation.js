
getDocs();

function getDocs(){

    console.log("Retrieving docs...");
    $.ajax({
        method:"GET",
        url: urlHost+"/docs",
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
        $(".docsContainer").css('bottom', '0' );
        $(".docsContainer").css('top', '' );
        $(".docsContainer").height($( ".docsHeader" ).height())
        extended=false;
    }
    else{
        $(".docsContainer").height("45vh");
        extended=true;
    }
}


$( ".docsContainer" ).resizable({
    resize: function( event, ui ) {
        if(ui.element.css('top') != null){
            extended=true;
        }else{
            extended=false;
        }
    },
    handles: "n",
    minHeight: Math.round($( ".docsHeader" ).height()),
    maxHeight: Math.round($( window ).height() * (2/3))
});
