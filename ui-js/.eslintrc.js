module.exports = {
  'extends': 'airbnb-base',

  rules: {
    'linebreak-style': 'off',
    'comma-dangle': 'off',
    'eol-last': 'off',
    'no-underscore-dangle': 'off',
    'no-param-reassign': ['error', { props: false }],
    'max-len': ['warn', 120, 4],
    'import/no-extraneous-dependencies': ['error', {'devDependencies': true}],
  },

  globals: {
    // Angular
    "angular": false,

    // Angular mocks
    "module": false,
    "inject": false,

    // Jasmine
    "jasmine": false,
    "describe": false,
    "beforeEach": false,
    "afterEach": false,
    "it": false,
    "expect": false,
    "spyOn": false,
    "xdescribe": false,

    // FormData Web API
    "FormData": false,

    // Own global Angular app bootstrapping function
    "include_all_modules": false,
  }
};