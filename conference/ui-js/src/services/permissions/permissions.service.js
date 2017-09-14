
function Permissions($rootScope, $location, $state, $stateParams, LocalStorage) {
  'ngInject';

  function permitted(role, user) {
    if (!user || user.roles.indexOf(role) === -1) {
      LocalStorage.setItem('redirectStateName', $state.current.name);
      LocalStorage.setItem('redirectStateParams', JSON.stringify($stateParams));
      $rootScope.$broadcast('signInEvent');
    }
  }

  return {
    permitted
  };
}

export default Permissions;