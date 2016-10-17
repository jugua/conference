function SignUp($resource) {
  const resource = $resource('/api/users/:id', { id: '@_id' }, {
    update: {
      method: 'PUT' // this method issues a PUT request
    }
  });

  function signUp(user, successCallback, errorCallback) {
    console.log('going to post');
    console.log(resource);

    console.log(user.mail);

    // POST
    resource.save(user,
      (result) => {
        console.log('saved, caling callback');
        successCallback(result);
      },
      (error) => {
        console.log('error, calling callback');
        errorCallback(error);
      }
    );
  }

  return {
    signUp
  };
};

export default SignUp;