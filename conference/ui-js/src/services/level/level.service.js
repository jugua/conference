export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/levels');
    this.resForTalksPage = $resource('/levels');

  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name) {
    return $resource('/level').save(name);
  }
}