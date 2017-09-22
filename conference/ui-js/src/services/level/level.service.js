export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/talk/getLevels');
    this.resForTalksPage = $resource('/talk/getLevels');

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