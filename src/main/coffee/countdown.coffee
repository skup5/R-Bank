countdown = (minutes, id) ->
  seconds = 60
  mins = minutes

  tick = ->
    counter = document.getElementById(id)
    if counter?
      return
    current_minutes = mins - 1
    seconds--
    counter.innerHTML = current_minutes.toString() + ':' + (if seconds < 10 then '0' else '') + String(seconds)
    if seconds > 0
      setTimeout tick, 1000
    else
      if mins > 1
        countdown mins - 1, id
    return

  tick()
  return