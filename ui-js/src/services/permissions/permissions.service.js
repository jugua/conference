
function Permissions($rootScope) {
  'ngInject';

  function permitted(role, user) {
    if (!user || user.roles.indexOf(role) === -1) {
      $rootScope.$broadcast('signInEvent');
    }
  }

  return {
    permitted
  };
}

export default Permissions;