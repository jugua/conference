'use strict';

import path from 'path';
import gulp from 'gulp';
import yargs from 'yargs';
import template from 'gulp-template';
import rename from 'gulp-rename';
import lodash from 'lodash';

let blankTemplates = path.join(__dirname, 'component/*');

gulp.task('component', () => {
  const name = yargs.argv.name;
  const destPath = path.join(__dirname, '/../src/components/', lodash.kebabCase(name));

return gulp.src(blankTemplates)
  .pipe(template({
    pascalName: lodash.upperFirst(name).replace(/\s/g,''),
    camelName: lodash.camelCase(name).replace(/\s/g,''),
    kebabName: lodash.kebabCase(name).replace(/\s/g,'')
  }))
  .pipe(rename((path) => {
    path.basename = path.basename.replace('component', lodash.kebabCase(name));
  }))
  .pipe(gulp.dest(destPath));
});

