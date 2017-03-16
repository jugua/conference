export default class NewtalkController {
  constructor(Talks, Topic, Type, Language, Level) {
    'ngInject';

    this.talksService = Talks;

    this.talkForm = {};
    this.talk = {};
    this.isShownPopup = false;
    this.buttonsBlocked = false;

    this.topics = Topic.query();
    this.types = Type.query();
    this.langs = Language.query();
    this.levels = Level.query();
  }

  close() {
    function isEmptyForm(form) {
      for (const value of Object.values(form)) {
        if (value && value.length) {
          return false;
        }
      }
      return true;
    }

    if (this.talkForm.$pristine || this.talkForm.$submitted || isEmptyForm(this.talk)) {
      this.onClose();
      return;
    }

    if (this.talkForm.$dirty && !this.talkForm.$submitted) {
      this.isShownPopup = true;
    }
  }

  submit() {
    if (this.talkForm.$invalid) {
      this.isShownMandatoryAlert = true;
      this.talkForm.$submitted = false;
      return;
    }

    this.buttonsBlocked = true;   // block the buttons to avoid multiple submit

    this.talk.status = 'New';
    this.talk.date = Date.now();

    if (this.conferenceId) {    // if defined
      this.talk.conferenceId = this.conferenceId;
    }

    const formData = new FormData();

    for (const [key, value] of Object.entries(this.talk)) {
      formData.append(key, value);
    }

    this.talksService.add(formData,
      () => {
        this.onSubmit();
      }
    );
  }


  get fileLabelClass() {
    if (this.talkForm.$error.pattern || this.talkForm.$error.maxSize) {
      return 'file-upload file-upload__label_named file-upload__label_error';
    }
    return 'file-upload file-upload__label_named';
  }
}