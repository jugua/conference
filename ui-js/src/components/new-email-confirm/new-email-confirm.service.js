export default class NewEmailConfirmService {
  constructor($http) {
    'ngInject';

    this.http = $http;
  }

  emailConfirm(token) {
    console.log(`sending request to API: /api/newEmailConfirm/${token}`);
    return this.http.get(`/api/newEmailConfirm/${token}`);
  }
}