export default (app) => {
  app.run(($rootScope) => {
    'ngInject';
    $rootScope.$on('$stateChangeSuccess', () => {
      $rootScope.$broadcast('closeDropdown');
    });
  })
    .config(($mdThemingProvider) => {
      $mdThemingProvider.disableTheming();
    });
};
