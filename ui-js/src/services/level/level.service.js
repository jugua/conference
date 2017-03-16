export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/api/level');
  }

  query() {
    return this.res.query();
  }

  save(name) {
    return this.res.save(name);
  }
}