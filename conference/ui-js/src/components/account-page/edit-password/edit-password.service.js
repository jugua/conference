class EditPasswordService {
  constructor($http, LocalStorage) {
    'ngInject';

    this.http = $http;
    this.localStorage = LocalStorage;

    this.errors = {
      nonSpace: 'Please use at least one non-space character in your password.',
      minLength: 'Your password must be at least 6 characters long. Please try another.',
      maxLength: 'Your password should not be longer than 30 characters. Please try another.',
      require: 'All fields are mandatory. Please make sure all required fields are filled out.',
      passwordsMatch: 'Passwords do not match.',
      wrongPassword: 'You entered an invalid current password.'
    };
  }

  changePassword(passwords) {
    return this.http.post('settings/password', passwords, {
      headers: {
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache',
      }
    });
  }

  getErrors() {
    return this.errors;
  }
}

export default EditPasswordService;