class SignIn {
  constructor($http, $q, $window, $rootScope, $document) {
    'ngInject';

    this.userInfo = null;
    this.http = $http;
    this.q = $q;
    this.window = $window;
    this.rootScope = $rootScope;
    this.document = $document[0];     // $document is a jqLite collection
  }

  utf8ToB64(str) {
    return this.window.btoa(unescape(encodeURIComponent(str)));
  }

  login(user) {
    const deferred = this.q.defer();
    let headers;
    let body;

    if (this.document.cookie.indexOf('XSRF') === -1) {
      headers = {};
      body = user;
    } else {
      headers = user ? { authorization: `Basic ${this.utf8ToB64(`${user.mail}:${user.password}`)}` } : { };
      body = {};
    }

    this.http.post('/login/', body, { headers }).then(({ data }) => {
      if (data.token) {
        this.userInfo = {
          token: data.token
        };
      } else {
        this.userInfo = {
          token: 'auth'
        };
      }

      this.window.localStorage.setItem('userInfo', JSON.stringify(this.userInfo));
      deferred.resolve(this.userInfo);
    }, (error) => {
      this.window.localStorage.removeItem('userInfo');
      deferred.reject(error);
    });

    return deferred.promise;
  }

  callTheEvent() {
    this.rootScope.$broadcast('signInEvent');
  }
}

export default SignIn;