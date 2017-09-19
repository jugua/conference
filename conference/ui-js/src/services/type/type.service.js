export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/talk/getTypes');
    this.resForTalksPage = $resource('/talk/getTypes ');

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