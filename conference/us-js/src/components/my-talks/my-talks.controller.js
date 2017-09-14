export default class MyTalksController {
  constructor(Current, Talks, Topic) {
    'ngInject';

    this.current = Current.current;

    this.unfilteredTalks = Talks.getAll();
    this.talks = this.unfilteredTalks;    // talks list is initially unfiltered

    this.filter = {};
    this.showFilters = true;

    this.editTalkObj = {};
    this.showEditPopup = false;

    this.statuses = Talks.statusStrings;
    this.topics = Topic.queryForTalkPage();
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
  filterReset() {
    this.filter = {};
    this.talks = this.unfilteredTalks;    // point to unfiltered list
  }
  filterApply() {
    this.talks = this.unfilteredTalks;     // reset previous filters

    function dd(num) {     // Double Digit - add leading zero if needed
      const str = num.toString();
      if (str.length === 1) {
        return `0${str}`;
      }
      return str;
    }

    function formatCalendarDate(date) {
      return `${date.getFullYear()}${dd(date.getMonth() + 1)}${dd(date.getDate())}`;
    }

    function formatStoredDate(dateStr) {
      return `${dateStr.substr(0, 4)}${dateStr.substr(5, 2)}${dateStr.substr(8, 2)}`;
    }

    let fTalks = this.talks.slice();   // copy, filtered talks
    if (this.filter.status) {
      fTalks = fTalks.filter(talk => talk.status === this.filter.status);
    }
    if (this.filter.topic) {
      fTalks = fTalks.filter(talk => talk.topic === this.filter.topic);
    }
    if (this.filter.fromDate) {
      fTalks = fTalks.filter(talk => formatStoredDate(talk.date) >= formatCalendarDate(this.filter.fromDate));
    }
    if (this.filter.toDate) {
      fTalks = fTalks.filter(talk => formatStoredDate(talk.date) <= formatCalendarDate(this.filter.toDate));
    }
    this.talks = fTalks.slice();
  }
}