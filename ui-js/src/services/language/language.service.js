export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/api/lang');
    this.resForTalksPage = $resource('/api/talk/lang');
  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name) {
    return this.res.save(name);
  }
}