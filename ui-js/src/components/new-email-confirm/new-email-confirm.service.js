export default class NewEmailConfirmService {
  constructor($http) {
    'ngInject';

    this.http = $http;
    this.wat = 'wat';
  }

  emailConfirm(token) {
    return this.http.get(`/api/newEmailConfirm/${token}`);
  }
}