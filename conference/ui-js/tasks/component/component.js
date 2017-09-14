import <%= camelName %>Component; from; './<%= kebabName %>.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('<%= camelName %>', {
        url: '/<%= kebabName %>',
        template: '<<%= kebabName %>></<%= kebabName %>>'
      });
  }).component('<%= camelName %>', < %= camelName % > Component;
    )
};
