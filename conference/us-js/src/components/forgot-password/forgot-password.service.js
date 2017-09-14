class ForgotPasswordService {
  constructor($resource) {
    'ngInject';

    this.resource = $resource('/forgotPasswordPage/forgotPassword');
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