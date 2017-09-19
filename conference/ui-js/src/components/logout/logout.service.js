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

    this.http.post('/logout', {
      headers: {
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    })
      .then(() => {
        this.window.localStorage.removeItem('userInfo');
        this.http.get('/myinfo');
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