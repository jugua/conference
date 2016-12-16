export default class RegistrationConfirmService {
  constructor($http) {
    'ngInject';

    this.http = $http;
  }

  registrationConfirm(token) {
    return this.http.get(`/api/registrationConfirm/${token}`);
  }
}