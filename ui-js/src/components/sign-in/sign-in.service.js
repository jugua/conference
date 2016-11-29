function SignIn($http, $q, $window, $rootScope) {
  let userInfo;
  const utf8ToB64 = str => $window.btoa(unescape(encodeURIComponent(str)));

  function login(user) {
    const deferred = $q.defer();
    let headers;
    let body;

    if (document.cookie.indexOf('XSRF') === -1) {
      headers = {};
      body = user;
    } else {
      headers = user ? { authorization: 'Basic '+ utf8ToB64(user.mail + ':' + user.password) } : { };
      body = {};
    }

    $http.post('/api/login/', body, { headers }).then(({ data }) => {
      if (data.token) {
        userInfo = {
          token: data.token
        };
      } else {
        userInfo = {
          token: 'auth'
        };
      }
      $window.localStorage.setItem('userInfo', JSON.stringify(userInfo));
      deferred.resolve(userInfo);
    }, (error) => {
      $window.localStorage.removeItem('userInfo');
      deferred.reject(error);
    });

    return deferred.promise;
  }

  function callTheEvent() {
    $rootScope.$broadcast('signInEvent');
  }

  return {
    login,
    callTheEvent
  };
}

export default SignIn;