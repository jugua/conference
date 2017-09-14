export default class {
  constructor(Talks, Current, TalkFile) {
    'ngInject';

    this.talksService = Talks;

    this.comment = this.talk.comment;   // copy prop aside, not to modify the obj itself yet

    this.form = {};

    this.commentRequired = false;
    this.confirmShown = false;

    Current.current
      .then((res) => {
        this.reviewer = `${res.fname} ${res.lname}`;
      });

    this.talkFileService = TalkFile;
    this.fileNameObj = this.talkFileService.getName(this.talk.id);
  }

  get statusEditable() {  // getter, convenient for template inline triggers
    if (this.talk.status === this.talksService.TALK_STATUS_NEW ||
        this.talk.status === this.talksService.TALK_STATUS_PROGRESS) {
      return true;
    }
    return false;
  }

  approve() {
    this.talksService.approve(this.talk.id, this.comment,
      () => {   // success callback
        this.talk.comment = this.comment;   // modify the obj itself, affect the view
        this.talk.status = this.talksService.TALK_STATUS_APPROVED;
        this.talk.assignee = this.reviewer;
        this.close();
      });
  }

  reject() {
    if (!this.comment) { // required
      this.commentRequired = true;
      return;
    }
    this.talksService.reject(this.talk.id, this.comment,
      () => {
        this.talk.comment = this.comment;
        this.talk.status = this.talksService.TALK_STATUS_REJECTED;
        this.talk.assignee = this.reviewer;
        this.close();
      });
  }

  progress() {
    this.talksService.progress(this.talk.id, this.comment,
      () => {
        this.talk.comment = this.comment;
        this.talk.status = this.talksService.TALK_STATUS_PROGRESS;
        this.talk.assignee = this.reviewer;
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
