export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/topics');
    this.resForTalksPage = $resource('/topics');

  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name, successCallback) {
    return $resource('/topic').save(
      { name },
      () => { successCallback(); });
  }
}