/* global angular */
/* global FormData */

function Logout($window, $q, $http) {
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

  function logout() {
    const deferred = $q.defer();

    $http.get('/api/users/current/logout', {
      headers: {
        token: getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    })
      .then(() => {
          $window.localStorage.removeItem('userInfo');
          return deferred.$resolve();
        },
        (error) => {
          return deferred.$reject(error);
        }
      )
  }

  return {
    logout
  };
}

export default Logout;