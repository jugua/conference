export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/api/type');
    this.resForTalksPage = $resource('/api/talk/type');
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