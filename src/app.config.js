export default app => {
  app.config([configFn]).
  run(($rootScope) => {
    $rootScope.$on('$stateChangeSuccess', () => {
      $rootScope.$broadcast('closeDropdown');
    });
  });

  function configFn() {

  }
};
