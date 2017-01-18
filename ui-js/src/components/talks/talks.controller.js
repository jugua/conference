export default class TalksController {
  constructor(Current, Talks, Menus) {
    'ngInject';

    this.current = Current.current;

    this.talksService = Talks;
    this.talks = Talks.getAll();

    this.filter = {};
    this.menuService = Menus;
    this.showFilters = true;

    this.reviewTalkId = null;
    this.reviewTalkStatus = null;
    this.showReviewPopup = false;
  }
  showSettings() {
    this.showFilters = !this.showFilters;
  }
  review(id, status) {
    this.reviewTalkId = id;
    this.reviewTalkStatus = status;
    this.showReviewPopup = true;
  }
  hideReviewPopup() {
    this.reviewTalkId = null;
    this.showReviewPopup = false;
    this.refresh();
  }
  refresh() {
    this.talks = this.talksService.getAll();
  }
}
