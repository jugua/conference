export default class NewtalkController {
  constructor(Menus, Talks, $state, $scope) {
    'ngInject';

    this.talksService = Talks;
    this.scope = $scope;
    this.selectService = Menus;
    this.state = $state;
    this.talkForm = {};
    this.talk = {};
    this.isShownPopup = false;
    this.buttonsBlocked = false;
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
      this.state.go('header.tabs.myTalks');
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

    const formData = new FormData();

    for (const [key, value] of Object.entries(this.talk)) {
      formData.append(key, value);
    }

    this.talksService.add(formData,
      () => { this.state.go('header.tabs.myTalks', {}, { reload: true }); }
    );
  }


  get fileLabelClass() {
    if (this.talkForm.$error.pattern || this.talkForm.$error.maxSize) {
      return 'file-upload file-upload__label_named file-upload__label_error';
    }
    return 'file-upload file-upload__label_named';
  }
}