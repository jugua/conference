
function Current($resource, $q, $rootScope, LocalStorage) {
  'ngInject';

  const users = $resource('/api/user/current', {}, {
    getCurrentUser: {
      method: 'GET',
      headers: {
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    },
    updateCurrentUser: {
      method: 'POST',
      headers: {
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
      window.localStorage.removeItem('userInfo');
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