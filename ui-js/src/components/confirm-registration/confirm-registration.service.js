export default class ConfirmRegistrationService {
  constructor($http) {
    'ngInject';

    this.http = $http;
  }

  confirmRegistration(token) {
    return this.http.get(`api/registrationConfirm/${token}`);
  }
}