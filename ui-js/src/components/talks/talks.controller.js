export default class TalksController {
  constructor(Current, Talks, User, Topic) {
    'ngInject';

    this.current = Current.current;

    this.talksService = Talks;
    this.userService = User;

    this.topicService = Topic;

    this.unfilteredTalks = Talks.getAll();
    this.talks = this.unfilteredTalks;    // talks list is initially unfiltered

    this.filter = {};
    this.showFilters = true;

    if (this.reviewTalkId) {  // review specific talk right away
      this.reviewTalkObj = Talks.get(this.reviewTalkId);
      this.showReviewPopup = true;
    } else {
      this.reviewTalkObj = {};
      this.showReviewPopup = false;
    }

    this.userInfoObj = {};   // user object to pass to popup controller
    this.showUserInfoPopup = false;

    this.statuses = Talks.statusStrings;
    this.topics = Topic.query();
  }
  showSettings() {
    this.showFilters = !this.showFilters;
  }
  review(talk) {  // talk object passed
    this.reviewTalkObj = talk;
    this.showReviewPopup = true;
  }
  hideReviewPopup() {
    this.reviewTalkObj = {};
    this.showReviewPopup = false;
  }
  userInfo(id) {
    this.userInfoObj = {};
    this.showUserInfoPopup = false;
    this.userInfoObj = this.userService.get(id);
    this.showUserInfoPopup = true;
  }
  hideUserInfoPopup() {
    this.userInfoId = null;
    this.showUserInfoPopup = false;
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
