function SignIn($http, $q, $window, $rootScope) {
  let userInfo;

  function login(user) {
    const deferred = $q.defer();

    const headers = user ? {authorization : "Basic "
    + btoa(user.mail + ":" + user.password)
    } : {};

    $http.post('/api/login/', {headers}).then((result) => {
      userInfo = {
        token: ''//result.data.token
      };

      $window.localStorage.setItem('userInfo', JSON.stringify(userInfo));
      deferred.resolve(userInfo);
    }, (error) => {
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