
$(document).ready ->
  $('[data-toggle="tooltip"]').tooltip()
  btn = document.getElementById("btnCardNo")
  if btn? then btn.onclick = () ->
    document.getElementById("inputCardNo").value = Math.floor(Math.random() * 9999999999999999) + 1
  btn = document.getElementById("btnBankAccountNo")
  if btn? then btn.onclick = () ->
    document.getElementById("inputBankAccountNo").value = Math.floor(Math.random() * 999999999) + 1
  return