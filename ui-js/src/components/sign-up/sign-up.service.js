class SignUpService {
  constructor($resource) {
    'ngInject';

    this.resource = $resource('/api/user', {}, {
      update: {
        method: 'PUT' // this method issues a PUT request
      }
    });
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