export default (app) => {
  app.run(($rootScope) => {
    'ngInject';
    $rootScope.$on('$stateChangeSuccess', () => {
      $rootScope.$broadcast('closeDropdown');
    });
  })
    .config(($mdThemingProvider, $mdDateLocaleProvider) => {
      $mdThemingProvider.disableTheming();
      $mdDateLocaleProvider.formatDate = function(date) {
      return date.toLocaleString('es', {day: 'numeric', month: 'numeric', year: 'numeric'});
    };
  });
};
