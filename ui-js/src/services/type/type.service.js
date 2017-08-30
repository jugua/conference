export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/submitTalk/getTypes');
    this.resForTalksPage = $resource('/api/talk/type');
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