$(document).ready(function() {
  var btn;
  $('[data-toggle="tooltip"]').tooltip();
  btn = document.getElementById("btnCardNo");
  if (btn != null) {
    btn.onclick = function() {
      return document.getElementById("inputCardNo").value = Math.floor(Math.random() * 9999999999999999) + 1;
    };
  }
  btn = document.getElementById("btnBankAccountNo");
  if (btn != null) {
    btn.onclick = function() {
      return document.getElementById("inputBankAccountNo").value = Math.floor(Math.random() * 999999999) + 1;
    };
  }
});
