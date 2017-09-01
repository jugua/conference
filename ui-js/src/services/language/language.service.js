export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/api/lang');
    this.resForTalksPage = $resource('/talk/lang');

  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name) {
    return $resource('/api/lang').save(name);
  }
}