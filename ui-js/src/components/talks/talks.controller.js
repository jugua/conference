export default class TalksController {
  constructor(Current, Talks, Menus, $scope) {
    'ngInject';

    this.current = Current.current;

    this.talks = Talks.getAll();

    this.filter = {};
    this.menuService = Menus;
    this.showFilters = true;

    this.reviewTalkId = null;
    this.showReviewPopup = false;

    $scope.$on('closeReviewPopup', () => {
      this.showReviewPopup = false;
    });
  }
  showSettings() {
    this.showFilters = !this.showFilters;
  }
  review(id) {
    this.reviewTalkId = id;
    this.showReviewPopup = true;
  }
}
