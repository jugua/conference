class SignIn {
  constructor($http, $q, $window, $rootScope) {
    'ngInject'

    this.userInfo = {};
    this.http = $http;
    this.q = $q;
    this.window = $window;
    this.rootScope = $rootScope;
  }

  login(user) {
    const deferred = this.q.defer();

    this.http.post('/api/login/', user).then(({ data }) => {
      this.userInfo = {
        token: data.token
      };

      this.window.localStorage.setItem('userInfo', JSON.stringify(this.userInfo));
      deferred.resolve(this.userInfo);
    }, (error) => {
      deferred.reject(error);
    });

    return deferred.promise;
  }

  callTheEvent() {
    this.rootScope.$broadcast('signInEvent');
  }
}

export default SignIn;