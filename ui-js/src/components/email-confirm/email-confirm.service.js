export default class EmailConfirmService {
  constructor($http) {
    'ngInject';

    this.http = $http;
    this.wat = 'wat';
  }

  emailConfirm(token) {
    return this.http.get(`/api/emailConfirm/${token}`);
  }
}