pugPath = "src/main/pug/"
coffeePath = "src/main/coffee/"
webappPath = "src/main/webapp/"

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
          "src/main/webapp/js/script.js" : [coffeePath+"*.coffee"]
    #PUG
    pug:
      compile:
        options:
          pretty: true
        expand: true
        flatten: false
        cwd: pugPath
        src: [
          "index.pug"
          "userpage.pug"
        ]
        dest: webappPath
        ext: ".html"

    #WATCH
    watch:
      pug:
        files: [pugPath+"*.pug"]
        tasks: ["pug"]
        options: spawn: false
      coffee:
        files: [coffeePath+"*.coffee"]
        tasks: ["coffee"]
        options: spawn: false

  grunt.loadNpmTasks "grunt-contrib-coffee"
  grunt.loadNpmTasks "grunt-contrib-pug"
  grunt.loadNpmTasks "grunt-contrib-watch"
  grunt.registerTask "default", ["pug", "coffee", "watch"]