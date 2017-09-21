export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/types');
    this.resForTalksPage = $resource('/types ');

  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name, successCallback) {
    return $resource('/type').save(
      { name },
      () => { successCallback(); });
  }
}