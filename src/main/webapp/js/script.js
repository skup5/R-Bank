var countdown;

countdown = function(minutes, id) {
  var mins, seconds, tick;
  seconds = 60;
  mins = minutes;
  tick = function() {
    var counter, current_minutes;
    counter = document.getElementById(id);
    if (counter != null) {
      return;
    }
    current_minutes = mins - 1;
    seconds--;
    counter.innerHTML = current_minutes.toString() + ':' + (seconds < 10 ? '0' : '') + String(seconds);
    if (seconds > 0) {
      setTimeout(tick, 1000);
    } else {
      if (mins > 1) {
        countdown(mins - 1, id);
      }
    }
  };
  tick();
};

$(document).ready(function() {
  $('[data-toggle="tooltip"]').tooltip();
  $().alert();
});
