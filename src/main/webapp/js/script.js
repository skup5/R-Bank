$(document).ready(function() {
  $('[data-toggle="tooltip"]').tooltip();
  document.getElementById("btnCardNo").onclick = function() {
    return document.getElementById("inputCardNo").value = Math.floor(Math.random() * 9999999999999999) + 1;
  };
  document.getElementById("btnBankAccountNo").onclick = function() {
    return document.getElementById("inputBankAccountNo").value = Math.floor(Math.random() * 999999999) + 1;
  };
});
