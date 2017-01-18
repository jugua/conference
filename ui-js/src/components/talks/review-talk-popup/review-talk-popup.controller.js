export default class {
  constructor(Menus, Talks) {
    'ngInject';

    this.talksService = Talks;
    this.selectService = Menus;
    this.form = {};

    this.talk = Talks.get(this.id);

    this.commentRequired = false;
    this.confirmShown = false;
  }

  get statusEditable() {  // getter, convenient for template inline triggers
    if (this.status === 'New' || this.status === 'In Progress') {
      return true;
    }
    return false;
  }

  approve() {
    this.talksService.approve(this.id, this.talk.comment,
      () => { this.close(); });   // success callback
  }

  reject() {
    if (!this.talk.comment) { // required
      this.commentRequired = true;
      return;
    }
    this.talksService.reject(this.id, this.talk.comment,
      () => { this.close(); });   // success callback
  }

  progress() {
    this.talksService.progress(this.id, this.talk.comment,
      () => { this.close(); });   // success callback
  }

  closeCheck() {
    if (this.statusEditable && this.form.comment.$dirty) {  // comment modified, but attempting to leave
      this.showConfirm();
    } else {
      this.close();
    }
  }

  close() {
    this.onHideReviewPopup();
  }

  resetCommentRequired() {
    this.commentRequired = false;
  }

  showConfirm() {
    this.confirmShown = true;
  }

  hideConfirm() {
    this.confirmShown = false;
  }
}