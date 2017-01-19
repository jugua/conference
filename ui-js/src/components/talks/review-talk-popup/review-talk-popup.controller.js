export default class {
  constructor(Talks) {
    'ngInject';

    this.talksService = Talks;

    this.comment = this.talk.comment;   // copy prop aside, not to modify the obj itself yet

    this.form = {};

    this.commentRequired = false;
    this.confirmShown = false;
  }

  get statusEditable() {  // getter, convenient for template inline triggers
    if (this.talk.status === this.talksService.TALK_STATUS_NEW ||
        this.talk.status === this.talksService.TALK_STATUS_PROGRESS) {
      return true;
    }
    return false;
  }

  approve() {
    this.talksService.approve(this.talk._id, this.comment,
      () => {   // success callback
        this.talk.comment = this.comment;   // modify the obj itself, affect the view
        this.talk.status = this.talksService.TALK_STATUS_APPROVED;
        this.close();
      });
  }

  reject() {
    if (!this.comment) { // required
      this.commentRequired = true;
      return;
    }
    this.talksService.reject(this.talk._id, this.comment,
      () => {
        this.talk.comment = this.comment;
        this.talk.status = this.talksService.TALK_STATUS_REJECTED;
        this.close();
      });
  }

  progress() {
    this.talksService.progress(this.talk._id, this.comment,
      () => {
        this.talk.comment = this.comment;
        this.talk.status = this.talksService.TALK_STATUS_PROGRESS;
        this.close();
      });
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
