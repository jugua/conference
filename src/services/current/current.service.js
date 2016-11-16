
function Current($resource, $q, $rootScope, LocalStorage) {
  'ngInject';

  const users = $resource('/api/users/current', {}, {
    getCurrentUser: {
      method: 'GET',
      headers: {
        token: LocalStorage.getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    },
    updateCurrentUser: {
      method: 'POST',
      headers: {
        token: LocalStorage.getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    }
  });

  function getInfo() {
    const current = $q.defer();
    users.getCurrentUser({}, (data) => {
      current.resolve(data);
    },
    () => {
      current.resolve(null);
    });

    this.current = current.promise;
  }

  function updateInfo(userInfo) {
    users.updateCurrentUser(userInfo, () => {
    },
    () => {
      $rootScope.$broadcast('signInEvent');
    });
  }

  function clear() {
    this.current = null;
  }

  return {
    clear,
    getInfo,
    updateInfo,
  };
}

export default Current;