/* global angular */
/* global FormData */

function Current($resource, $window, $q, $rootScope, $http) {
  function getToken() {
    let info = $window.localStorage.userInfo;
    let token;

    if (info) {
      info = JSON.parse(info);
      token = info.token;
    } else {
      token = '';
    }

    return token;
  }

  const users = $resource('/api/users/current', {}, {
    getCurrentUser: {
      method: 'GET',
      headers: {
        token: getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    },
    updateCurrentUser: {
      method: 'POST',
      headers: {
        token: getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    },
    addTalk: {
      method: 'POST',
      url: '/api/users/current/talk',
      headers: {
        token: getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    }
  });

  function addTalk(talk) {
    users.addTalk(talk, (answer) => {
      console.log(answer);
      },
      (a) => {
        console.log(a);
       // $rootScope.$broadcast('signInEvent');
      });
  }

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

  function uploadPhoto(file) {
    const formData = new FormData();
    formData.append('file', file);
    return $http.post('api/users/current/photo', formData, {
      transformRequest: angular.identity,
      headers: {
        token: getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache',
        'Content-Type': undefined
      }
    });
  }

  function logout() {
    return $http.get('/api/users/current/logout', {
      headers: {
        token: getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    });
  }

  return {
    addTalk,
    getInfo,
    updateInfo,
    uploadPhoto,
    logout
  };
}

export default Current;