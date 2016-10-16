const SignIn = function ($http, $q, $window, $rootScope) {
  let userInfo;

  function login(user) {
    const deferred = $q.defer();

    $http.post('/api/login/', user).then((result) => {
      userInfo = {
        token: result.data.token
      };
      $window.localStorage.userInfo = JSON.stringify(userInfo);
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
};

export default SignIn;