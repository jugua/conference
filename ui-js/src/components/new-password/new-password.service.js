class NewPasswordService {
    constructor ($http) {
        'ngInject';

        this.http = $http;
    }

    passConfirm (token){
      return this.http.get(`/api/registrationConfirm/${token}`);
    }

    changePassword(passwords) {
        return this.http.post('api/user/current/password', passwords, {
            headers: {
                'Cache-Control': 'no-cache, no-store',
                Pragma: 'no-cache',
            }
        });
    }

    getErrors() {
        return this.errors;
    }
}

export default NewPasswordService;