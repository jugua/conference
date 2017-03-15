export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/api/type', {}, {});
  }

  query() {
    return this.res.query();
  }

  save(name) {
    return this.res.save(name);
  }
}