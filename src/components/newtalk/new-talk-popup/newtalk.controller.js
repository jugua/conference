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
  }

  close() {
    function isEmptyForm(form) {
      let isEmpty = true;
      for (const field in form) {
        if (form[field] && form[field].length) {
          isEmpty = false;
          break;
        }
      }

      return isEmpty;
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

    this.talk.status = 'New';
    this.talk.date = Date.now();

    this.talksService.add(this.talk);
    this.state.go('header.tabs.myTalks');
  }
}