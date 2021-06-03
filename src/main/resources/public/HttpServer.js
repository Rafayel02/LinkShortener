var a = 0
function foo() {
   var inputValue = document.getElementById("link").value;
   if(inputValue.length == 0) {
       alert("Please input your link!");
       return false;
   }
   return true;
}