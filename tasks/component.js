'use strict';

import path from 'path';
import gulp from 'gulp';
import yargs from 'yargs';
import template from 'gulp-template';
import rename from 'gulp-rename';
import lodash from 'lodash';
import fs from 'fs';
import gutil from 'gulp-util';

let blankTemplates = path.join(__dirname, 'component/*');

gulp.task('component', () => {
  const name = yargs.argv.name;
  const destPath = path.join(__dirname, '/../src/components/', lodash.kebabCase(name));

  fs.stat(destPath, (err, stats) => {
    if (stats && stats.isDirectory()) {
      return gutil.log(gutil.colors.red('[ERROR] Component directory already exists [' + destPath + ']. \nIf you wish to re-create the component, remove the destination directory manually first.'));
    } else {
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
        .on('end', () => {
          gutil.log(gutil.colors.green('[SUCCESS] Component created [' + destPath + '].'));
        });
    }
  });
});

