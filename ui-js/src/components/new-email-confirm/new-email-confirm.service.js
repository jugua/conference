export default class NewEmailConfirmService {
  constructor($http) {
    'ngInject';

    this.http = $http;
    this.wat = 'wat';
  }

  emailConfirm(token) {
    console.log(`sending request to API: /api/newEmailConfirm/${token}`);
    return this.http.get(`/api/newEmailConfirm/${token}`);
  }
}