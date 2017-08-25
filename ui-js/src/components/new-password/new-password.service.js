class newPasswordService {
  constructor($http) {
    'ngInject';

    this.http = $http;
  }

  passConfirm(token) {
    return this.http.get(`/forgotPasswordPage/changePassword/${token}`, {
      headers: {
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache',
      }
    });
  }

  changePassword(passwords, token) {
    return this.http.post(`/forgotPasswordPage/changePassword/${token}`, passwords, {
      headers: {
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache',
      }
    });
  }
}

export default newPasswordService;