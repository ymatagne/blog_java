basePath = '../../';

files = [
  JASMINE,
  JASMINE_ADAPTER,
  'webapp/lib/angular/angular.js',
  'webapp/lib/angular/angular-*.js',
  'test/lib/angular/angular-mocks.js',
  'webapp/js/**/*.js',
  'test/unit/**/*.js'
];

autoWatch = true;

browsers = ['Chrome'];

junitReporter = {
  outputFile: 'test_out/unit.xml',
  suite: 'unit'
};
