export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/talk/getLanguages');
    this.resForTalksPage = $resource('/talk/getLanguages');

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