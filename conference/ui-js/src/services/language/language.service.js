export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/languages');
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