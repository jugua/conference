import controller from './header.controller';
import template from './header.html';

export default (app) => {
  app.config(($stateProvider, $urlRouterProvider) => {
    $urlRouterProvider.otherwise('/');
    $stateProvider
      .state('header', {
        url: '',
        resolve: {
          user: function getCurrent(Users, $q) {
            const current = $q.defer();

            Users.getCurrentUser({}, (data) => {
              current.resolve(data);
            },
              () => {
                current.resolve(null);
              });
            return current.promise;
          }
        },
        abstract: true,
        template,
        controller,
        controllerAs: 'ctrl'
      });
  });
};