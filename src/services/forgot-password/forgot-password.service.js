function ForgotPassword($resource) {
  'ngInject';

  const resource = $resource('/api/forgot-password');

  function restore(user, successCallback, errorCallback) {
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
    restore
  };
}

export default ForgotPassword;