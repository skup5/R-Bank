
$(document).ready ->
  $('[data-toggle="tooltip"]').tooltip()
  document.getElementById("btnCardNo").onclick = () ->
    document.getElementById("inputCardNo").value = Math.floor(Math.random() * 9999999999999999) + 1
  document.getElementById("btnBankAccountNo").onclick = () ->
    document.getElementById("inputBankAccountNo").value = Math.floor(Math.random() * 999999999) + 1
  return