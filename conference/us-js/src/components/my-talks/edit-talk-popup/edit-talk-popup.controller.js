export default class {
  constructor(Talks, TalkFile, Topic, Type, Language, Level) {
    'ngInject';

    this.talksService = Talks;
    this.talkFileService = TalkFile;

    this.obj = {};    // temp object to hold the original object's properties while editing
    Object.assign(this.obj, this.talk);  // shallow copy object, not to modify the original obj itself yet

    this.form = {};

    this.confirmShown = false;
    this.confirmDeleteFileShown = false;
    this.submitAttempt = false;

    this.fileNameObj = this.talkFileService.getName(this.talk.id);

    this.topics = Topic.queryForTalkPage();
    this.types = Type.queryForTalkPage();
    this.langs = Language.queryForTalkPage();
    this.levels = Level.queryForTalkPage();
  }

  get editable() {  // getter, convenient for template inline triggers
    if (this.talk.status === this.talksService.TALK_STATUS_NEW ||
        this.talk.status === this.talksService.TALK_STATUS_PROGRESS) {
      return true;
    }
    return false;
  }

  get new() {   // getter to check if talk is new
    if (this.talk.status === this.talksService.TALK_STATUS_NEW) {
      return true;
    }
    return false;
  }

  save() {
    if (this.form.$invalid) {
      this.submitAttempt = true;
      return;
    }

    // upload file first
    const formData = new FormData();
    formData.append('file', this.file);

    this.talkFileService.save(this.talk.id, formData, () => {
      this.fileNameObj = this.talkFileService.getName(this.talk.id);
    });

    // use separate send object, to filter out prohibited properties
    const sendObj = {};
    Object.assign(sendObj, this.obj);

    delete sendObj.status;                // filter out
    delete sendObj.comment;
    delete sendObj.id;

    this.talksService.update(this.talk.id, sendObj,
      () => {   // success callback
        Object.assign(this.talk, this.obj);   // modify the obj itself, affect the view
        this.close();
      });
  }

  closeCheck() {
    if (this.form.$dirty) {  // form modified, but attempting to leave
      this.showConfirm();
    } else {
      this.close();
    }
  }

  close() {
    this.onHideEditPopup();
  }

  showConfirm() {
    this.confirmShown = true;
  }

  hideConfirm() {
    this.confirmShown = false;
  }

  deleteFile() {
    this.talkFileService.delete(this.talk.id, () => {
      this.fileNameObj = {};
      this.hideConfirmDeleteFile();
    });
  }

  confirmDeleteFile() {
    this.confirmDeleteFileShown = true;
  }

  hideConfirmDeleteFile() {
    this.confirmDeleteFileShown = false;
  }

  get fileLabelClass() {
    if (this.form.$error.pattern || this.form.$error.maxSize) {
      return 'file-upload file-upload__label_named file-upload__label_error';
    }
    return 'file-upload file-upload__label_named';
  }
}
