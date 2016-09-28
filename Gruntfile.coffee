pugPath = "src/main/pug/"

module.exports = (grunt) ->
  grunt.initConfig
    pkg: grunt.file.readJSON "package.json"
    #PUG
    pug:
      compile:
        options:
          data:
            debug: false
            pretty: true
        files: 
          "src/main/webapp/index.html" : pugPath+"index.pug"

    #WATCH
    watch:
      pug:
        files: [pugPath]
        tasks: ["pug"]
        options:
          spawn: false

  grunt.loadNpmTasks "grunt-contrib-pug"
  grunt.loadNpmTasks "grunt-contrib-watch"
  grunt.registerTask "default", ["pug"]
  grunt.registerTask "watch", ["default", "watch"]