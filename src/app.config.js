export default (app) => {
  app.run(($rootScope) => {
    $rootScope.$on('$stateChangeSuccess', () => {
      $rootScope.$broadcast('closeDropdown');
    });
  })
    .config(($mdThemingProvider) => {
      $mdThemingProvider.disableTheming();
    });
};
