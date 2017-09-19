export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/submitTalk/getLanguages');
    this.resForTalksPage = $resource('/submitTalk/getLanguages');

  }

  query() {
    return this.res.query();
  }

  queryForTalkPage() {
    return this.resForTalksPage.query();
  }

  save(name) {
    return $resource('/submitTalk/getLanguages').save(name);
  }
}