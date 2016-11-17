function SignUp($resource) {
  'ngInject';
  const resource = $resource('/api/users/:id', { id: '@_id' }, {
    update: {
      method: 'PUT' // this method issues a PUT request
    }
  });

  function signUp(user, successCallback, errorCallback) {
    // POST
    resource.save(user,
      (result) => {
        successCallback(result);
      },
      (error) => {
        errorCallback(error);
      }
    );
  }

  return {
    signUp
  };
}

export default SignUp;