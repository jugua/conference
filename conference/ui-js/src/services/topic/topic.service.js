export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/submitTalk/getTopics');
    this.resForTalksPage = $resource('/submitTalk/getTopics');

  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name, successCallback) {
    return $resource('/submitTalk/getTopics').save(
      { name },
      () => { successCallback(); });
  }
}