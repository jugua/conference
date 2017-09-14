class EditEmailService {
  constructor($http, LocalStorage) {
    'ngInject';

    this.http = $http;
    this.localStorage = LocalStorage;

    this.messages = {
      errInvalidEmail: 'Please enter a valid email address',
      errEmailIsTheSame: 'The new e-mail address you have provided is the same as your current e-mail address',
      errEmailAlreadyExists: 'There is an existing account associated with {{email}}',
      confirmationSent: `We've sent a confirmation link to your new email address,
                         please check your inbox and confirm your changes`
    };
  }

  updateEmail(newEmail) {
    return this.http.post('settings/email', { mail: newEmail }, {
      headers: {
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache',
      }
    });
  }

  checkPendingUpdate() {
    return this.http.get('settings/email', {
      headers: {
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache',
      }
    });
  }

  getMessages() {
    return this.messages;
  }
}

export default EditEmailService;