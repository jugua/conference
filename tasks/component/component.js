import <%= camelName %>Component from './<%= kebabName %>.component';

export default (app) => {
  app.config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise('/');

    $stateProvider
      .state('<%= camelName %>', {
        url: '/<%= kebabName %>',
        template: '<<%= kebabName %>></<%= kebabName %>>' // Essentially Treats the Home Directive as the Route View.
      });
  }).component('<%= camelName %>', <%= camelName %>Component);
};
