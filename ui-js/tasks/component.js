var path = require('path');
var gulp = require('gulp');
var yargs = require('yargs');
var template = require('gulp-template');
var rename = require('gulp-rename');
var lodash = require('lodash');
var fs = require('fs');
var gutil = require('gulp-util');

var blankTemplates = path.join(__dirname, 'component/*');

gulp.task('component', () => {
  const name = yargs.argv.name;
  const destPath = path.join(__dirname, '/../src/components/', lodash.kebabCase(name));

  fs.stat(destPath, function (err, stats) {
    if (stats && stats.isDirectory()) {
      return gutil.log(gutil.colors.red(
        '[ERROR] Component directory already exists [' + destPath + ']. \n' +
        'If you wish to re-create the component, remove the destination directory manually first.'
      ));
    }
    return gulp.src(blankTemplates)
      .pipe(template({
        pascalName: lodash.upperFirst(name).replace(/\s/g,''),
        camelName: lodash.camelCase(name).replace(/\s/g,''),
        kebabName: lodash.kebabCase(name).replace(/\s/g,'')
      }))
      .pipe(rename((path) => {
        path.basename = path.basename.replace('component', lodash.kebabCase(name));
      }))
      .pipe(gulp.dest(destPath))
      .on('end', function () {
        gutil.log(gutil.colors.green('[SUCCESS] Component created [' + destPath + '].'));
      });
  });
});

