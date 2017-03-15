export default class TalksController {
  constructor(Current, Talks, User, Topic) {
    'ngInject';

    this.current = Current.current;

    this.talksService = Talks;
    this.userService = User;
    this.topicService = Topic;

    this.talks = Talks.getAll();
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

  get topicNames() {
    return this.topics.map(el => el.name);
  }
}
