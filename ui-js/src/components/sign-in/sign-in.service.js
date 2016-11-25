function SignIn($http, $q, $window, $rootScope) {
  let userInfo;
  const utf8ToB64 = str => $window.btoa(unescape(encodeURIComponent(str)));

  function login(user) {
    const deferred = $q.defer();
    const headers = user ? { authorization: 'Basic '+ utf8ToB64(user.mail + ':' + user.password) } : { };

    $http.post('/api/login/', { }, { headers }).then(() => {
      userInfo = {
        token: 'auth'
      };

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