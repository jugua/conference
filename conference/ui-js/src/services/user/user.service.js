export default class {
  constructor($resource) {
    'ngInject';

    this.user = $resource('/api/user/:id', {}, {
      get: {
        method: 'GET',
        params: { id: '@id' },
      }
    });
  }

  get(id) {
    return this.user.get({ id });
  }
}
