export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/api/level');
    this.resForTalksPage = $resource('/talk/level');

  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name) {
    return $resource('/api/level').save(name);
  }
}