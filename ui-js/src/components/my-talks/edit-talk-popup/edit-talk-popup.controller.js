export default class {
  constructor(Menus, Talks) {
    'ngInject';

    this.talksService = Talks;
    this.selectService = Menus;

    this.obj = {};    // temp object to hold the original object's properties while editing
    Object.assign(this.obj, this.talk);  // shallow copy object, not to modify the original obj itself yet

    this.form = {};

    this.confirmShown = false;
    this.submitAttempt = false;
  }

  get editable() {  // getter, convenient for template inline triggers
    if (this.talk.status === this.talksService.TALK_STATUS_NEW ||
        this.talk.status === this.talksService.TALK_STATUS_PROGRESS) {
      return true;
    }
    return false;
  }

  save() {
    if (this.form.$invalid) {
      this.submitAttempt = true;
      return;
    }

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
}
