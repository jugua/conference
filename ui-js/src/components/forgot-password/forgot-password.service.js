class ForgotPasswordService {
  constructor($resource) {
    'ngInject';

    this.resource = $resource('/api/forgot-password');
  }

  restore(user, successCallback, errorCallback) {
    this.resource.save(user,
      (result) => {
        successCallback(result);
      },
      (error) => {
        errorCallback(error);
      }
    );
  }
}

export default ForgotPasswordService;