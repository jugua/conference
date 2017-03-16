export default class MyTalksController {
  constructor(Current, Talks, Topic) {
    'ngInject';

    this.current = Current.current;
    this.talks = Talks.getAll();
    this.filter = {};
    this.showFilters = true;

    this.editTalkObj = {};
    this.showEditPopup = false;

    this.statuses = Talks.statusStrings;
    this.topics = Topic.query();
  }
  showSettings() {
    this.showFilters = !this.showFilters;
  }
  edit(talk) {  // talk object passed
    this.editTalkObj = talk;
    this.showEditPopup = true;
  }
  hideEditPopup() {
    this.editTalkObj = {};
    this.showEditPopup = false;
  }
}