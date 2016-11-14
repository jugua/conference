function SignIn($http, $q, $window, $rootScope) {
  let userInfo;

  function login(user) {
    const deferred = $q.defer();

    $http.post('/api/login/', user).then(({ data }) => {
      userInfo = {
        token: data.token
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