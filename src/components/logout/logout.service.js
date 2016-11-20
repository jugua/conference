/* global angular */
/* global FormData */

class Logout {
  constructor($window, $q, $http, LocalStorage) {
    'ngInject';

    this.localStorage = LocalStorage;
    this.q = $q;
    this.window = $window;
    this.http = $http;
  }

  logout() {
    const deferred = this.q.defer();

    this.http.get('/api/logout', {
      headers: {
        token: this.localStorage.getToken,
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