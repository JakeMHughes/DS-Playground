
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




