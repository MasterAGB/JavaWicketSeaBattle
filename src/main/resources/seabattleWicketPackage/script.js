function moveCell(x,y){
    //alert("moveCell:"+x+"x"+y);

    document.getElementById("fieldX").value=x;
    document.getElementById("fieldY").value=y;

}
function clickCell(x,y){
    document.getElementById("fieldX").value=x;
    document.getElementById("fieldY").value=y;
    if(document.getElementById("fieldCommand").value=="")
    document.getElementById("fieldCommand").value="0";
    document.getElementById("fieldSubmit").click();
}