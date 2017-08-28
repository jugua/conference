export default class ManageUsersService {

  constructor($resource) {
    'ngInject';

    this.$resource = $resource;
    this.getUsers();
  }

  getUsers() {
    this.users = this.$resource('/manageUsers/getAllUsersForAdmin', {}, {
      getAll: {
        method: 'GET',
        isArray: true,
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      }
    });
  }

  getAll() {
    return this.users.getAll();
  }
}