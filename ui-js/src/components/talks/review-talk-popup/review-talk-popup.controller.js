export default class {
  constructor(Menus, Talks) {
    'ngInject';

    this.talksService = Talks;
    this.selectService = Menus;
    this.talkForm = {};

    this.talk = Talks.get(this.id);

    this.isLeaveConfirmOpen = false;
    this.isMandatoryAlertOpen = false;
  }

  approve() {
    this.talksService.approve(this.id);
    this.close();
  }

  reject() {
    this.talksService.reject(this.id, this.talk.comment);
    this.close();
  }

  progress() {
    this.talksService.progress(this.id, this.talk.comment);
    this.close();
  }

  close() {
    this.onHideReviewPopup();
  }

  closeLeaveConfirm() {
    this.isLeaveConfirmOpen = false;
  }

  closeMandatoryAlert() {
    this.isMandatoryAlertOpen = false;
  }
}