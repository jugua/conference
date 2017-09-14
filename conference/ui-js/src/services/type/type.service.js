export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/api/type');
    this.resForTalksPage = $resource('/talk/type');

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