export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/submitTalk/getLevels');
    this.resForTalksPage = $resource('/talks/getLevels');
  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name) {
    return $resource('/api/level').save(name);
  }
}