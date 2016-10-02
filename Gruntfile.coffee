pugPath = "./src/main/pug/"

module.exports = (grunt) ->
  grunt.initConfig
    pkg: grunt.file.readJSON "package.json"
    #PUG
    pug:
      compile:
        options:
          pretty: true
        files: 
          "src/main/webapp/index.html" : pugPath+"index.pug"
          "src/main/webapp/userpage.html" : pugPath+"userpage.pug"

    #WATCH
    watch:
      pug:
        files: [pugPath+"*.pug"]
        tasks: ["pug"]

  grunt.loadNpmTasks "grunt-contrib-pug"
  grunt.loadNpmTasks "grunt-contrib-watch"
  grunt.registerTask "default", ["pug", "watch"]