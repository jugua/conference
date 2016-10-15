const SignIn = function($http, $q, $window, $rootScope) {

  var userInfo;

  function login(user) {
    var deferred = $q.defer();

    $http.post("/api/login/", user).then(function(result) {
      userInfo = {
        token: result.data.token
      };
      $window.sessionStorage["userInfo"] = JSON.stringify(userInfo);
      deferred.resolve(userInfo);
    }, function(error) {
      deferred.reject(error);
    });

    return deferred.promise;
  }

  function callTheEvent() {
    $rootScope.$broadcast('signInEvent');
  }

  return {
    login: login,
    callTheEvent: callTheEvent
  };
};

export default SignIn;