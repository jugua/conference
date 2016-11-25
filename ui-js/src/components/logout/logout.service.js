/* global angular */
/* global FormData */

class Logout {
  constructor($window, $q, $http) {
    'ngInject';

    this.q = $q;
    this.window = $window;
    this.http = $http;
  }

  logout() {
    const deferred = this.q.defer();

    this.http.post('/api/logout', {
      headers: {
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