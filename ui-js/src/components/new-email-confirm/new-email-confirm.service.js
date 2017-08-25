export default class NewEmailConfirmService {
  constructor($http) {
    'ngInject';

    this.http = $http;
  }

  emailConfirm(token) {
    return this.http.get(`/confirmation/newEmailConfirm/${token}`);
  }
}