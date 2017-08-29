export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/api/topic');
    this.resForTalksPage = $resource('/talk/topic');
  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name, successCallback) {
    return this.res.save(
      { name },
      () => { successCallback(); });
  }
}