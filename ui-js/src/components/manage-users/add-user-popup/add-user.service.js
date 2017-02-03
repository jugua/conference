class AddUserService {
  constructor($http, $resource) {
    'ngInject';
    this.$resource = $resource;
    this.$http = $http;
    this.Userz = this.$resource('/api/user/create', {}, {
      add: {
        method: 'POST',
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      }
    });
  }

  addUser() {
    return this.Userz;
  }
}

export default AddUserService;
