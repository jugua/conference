const SignUp = function ($http, $q) {
  let userInfo;

  function signUp(user) {
    const deferred = $q.defer();

    console.log('going to post');

    $http.post('/api/users/', user)
      .then((result) => {
        deferred.resolve(result);
      }, (error) => {
        deferred.reject(error);
      });

    return deferred.promise;
  }

  return {
    signUp
  };
};

export default SignUp;