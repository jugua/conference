
function Permissions($rootScope) {
  'ngInject';

  function permitted(role, user) {
    if (!user || !user.roles.includes(role)) {
      $rootScope.$broadcast('signInEvent');
    }
  }

  return {
    permitted
  };
}

export default Permissions;