export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/submitTalk/getLanguages');
    this.resForTalksPage = $resource('/talks/getLanguages');
  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name) {
    return $resource('/api/lang').save(name);
  }
}