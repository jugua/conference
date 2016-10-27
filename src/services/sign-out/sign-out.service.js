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

export default SignOut;
