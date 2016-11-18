export default (app) => {
  app.run(($rootScope) => {
    'ngInject';

    $rootScope.$on('$stateChangeSuccess', () => {
      $rootScope.$broadcast('closeDropdown');
    });
  })
    .config(($mdThemingProvider, $mdDateLocaleProvider) => {
      'ngInject';

      $mdThemingProvider.disableTheming();
      $mdDateLocaleProvider.formatDate = function (date) {
        if (!date) {
          return '';
        }
        return date.toLocaleString('es', { day: 'numeric', month: 'numeric', year: 'numeric' });
      };
    });
};
