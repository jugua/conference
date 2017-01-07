export default class {
  constructor(Menus, Talks, $state, $scope) {
    'ngInject';

    this.talksService = Talks;
    this.scope = $scope;
    this.selectService = Menus;
    this.state = $state;
    this.talkForm = {};

    this.isShownPopup = false;

    this.talk = Talks.get(this.id);
  }

  approve() {
    this.talksService.approve(this.id);
    this.close();
  }

  reject() {
    this.talksService.reject(this.id);
    this.close();
  }

  progress() {
    this.talksService.progress(this.id);
    this.close();
  }

  close() {
    this.scope.$emit('closeReviewPopup');
  }
}