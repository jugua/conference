export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/talk/getTopics');
    this.resForTalksPage = $resource('/talk/getTopics');

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