export default class ManageUsersService {

  constructor($resource) {
    'ngInject';

    this.users = $resource('/api/user/admin', {}, {
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