export default class RegistrationConfirmService {
  constructor($http) {
    'ngInject';

    this.http = $http;
  }

  registrationConfirm(token) {
    return this.http.get(`/confirmation/registrationConfirm/${token}`);
  }
}