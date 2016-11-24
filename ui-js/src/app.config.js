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
      $mdDateLocaleProvider.formatDate = (date) => {
        if (!date) {
          return '';
        }
        return date.toLocaleString('es', { day: '2-digit', month: '2-digit', year: 'numeric' });
      };
    });
};
