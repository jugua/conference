class SignUpService {
  constructor($resource) {
    'ngInject';

    this.resource = $resource('/registration', {}, {});
  }

  signUp(user, successCallback, errorCallback) {
    // POST
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

export default SignUpService;