export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/language');
    this.resForTalksPage = $resource('/languages');

  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name) {
    return $resource('/lang').save(name);
  }
}