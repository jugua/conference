export default class NewEmailConfirmService {
  constructor($http) {
    'ngInject';

    this.http = $http;
  }

  emailConfirm(token) {
    return this.http.get(`/api/newEmailConfirm/${token}`);
  }
}