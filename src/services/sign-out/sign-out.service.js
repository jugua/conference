function SignOut($resource) {
  const resource = $resource('/api/logout/:id', { id: '@_id' });

  function signOut(user, successCallback, errorCallback) {
    // GET
    resource.get(user,
      (result) => {
        successCallback(result);
      },
      (error) => {
        errorCallback(error);
      }
    );
  }

  return {
    signOut
  };
}

export default SignUp;


/*
function SignOut($http, $q, $window, $rootScope) {

  let userInfo;

  function login(user) {
    const deferred = $q.defer();

    $http.post('/api/login/', user).then((result) => {
      userInfo = {
        token: result.data.token
      };

      $window.localStorage.setItem('userInfo', JSON.stringify(userInfo));
      deferred.resolve(userInfo);
    }, (error) => {
      deferred.reject(error);
    });

    return deferred.promise;
  }

  function callTheEvent() {
    $rootScope.$broadcast('signOutEvent');
  }

  return {
    login,
    callTheEvent
  };
}

export default SignOut;
*/