export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/submitTalk/getTopics');
    this.resForTalksPage = $resource('/talks/getTopics');
  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name, successCallback) {
    return $resource('/api/topic').save(
      { name },
      () => { successCallback(); });
  }
}