coffeePath = "src/main/coffee/"
webappPath = "src/main/webapp/"
pugPath = webappPath + "WEB-INF/pug/"
jadePath = webappPath + "WEB-INF/jade/"
hiddenHtml = "WEB-INF/pages/"

module.exports = (grunt) ->
  grunt.initConfig
    pkg: grunt.file.readJSON "package.json"
#COFFEE
    coffee:
      compile:
        options:
          bare: true
          join: true
# expand: true
# flatten: false
# cwd: coffeePath
# src: [
#   "*.coffee"
# ]
# dest: webappPath+"js/"
# ext: ".js"
        files:
          "src/main/webapp/js/script.js": [coffeePath + "*.coffee"]
#PUG
    pug:
      compileHidden:
        options:
          pretty: true
        expand: true
        flatten: false
        cwd: pugPath
        src: [
          "user/userpage.pug"
          "admin/adminpage.pug"
        ]
        dest: webappPath + hiddenHtml
        ext: ".html"

      compileVisible:
        options:
          pretty: true
        files:
          "src/main/webapp/index.html": pugPath + "index.pug"
#JADE
    jade:
#      compileHidden:
#        options:
#          pretty: true
#        expand: true
#        flatten: false
#        cwd: jadePath
#        src: [
#          "user/userpage.jade"
#          "admin/adminpage.jade"
#        ]
#        dest: webappPath + hiddenHtml
#        ext: ".html"

      compileVisible:
        options:
          pretty: true
        files:
          "src/main/webapp/index.html": jadePath + "index.jade"
          "src/main/webapp/userpage.html": jadePath + "user/userpage.jade"
#WATCH
    watch:
      pug:
        files: [pugPath + "*.pug", pugPath + "*/*.pug"]
        tasks: ["pug"]
        options:
          spawn: false
      jade:
        files: [jadePath + "*.jade", jadePath + "*/*.jade"]
        options:
          spawn: false
      coffee:
        files: [coffeePath + "*.coffee"]
        tasks: ["coffee"]
        options:
          spawn: false

  grunt.loadNpmTasks "grunt-contrib-coffee"
  #  grunt.loadNpmTasks "grunt-contrib-pug"
  grunt.loadNpmTasks "grunt-contrib-jade"
  grunt.loadNpmTasks "grunt-contrib-watch"
  # grunt.registerTask "default", ["pug", "coffee"]
  # grunt.registerTask "daemon", ["pug", "coffee", "watch"]
  grunt.registerTask "default", ["jade", "coffee"]
  grunt.registerTask "daemon", ["jade", "coffee", "watch"]