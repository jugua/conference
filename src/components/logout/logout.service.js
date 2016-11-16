/* global angular */
/* global FormData */

class Logout {
  constructor($window, $q, $http) {
    "ngInject";
    this.window = $window;
    this.q = $q;
    this.http = $http;
  }

  getToken() {
    let info = this.window.localStorage.userInfo;
    let token;

    if (info) {
      info = JSON.parse(info);
      token = info.token;
    } else {
      token = '';
    }

    return token;
  }

  logout() {
    const deferred = this.q.defer();

    this.http.get('/api/users/current/logout', {
      headers: {
        token: this.getToken.bind(this),
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    })
      .then(() => {
        this.window.localStorage.removeItem('userInfo');
        deferred.resolve();
      },
        (error) => {
          deferred.reject(error);
        }
      );

    return deferred.promise;
  }
}

export default Logout;