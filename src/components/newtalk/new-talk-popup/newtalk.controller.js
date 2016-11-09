export default class NewtalkController {
  constructor(Menus, $state) {
    this.selectService = Menus;
    this.talk = {};
    this.state = $state;
    this.talkForm = {};
    this.isShownPopup = false;
  }

  close() {
    if (this.talkForm.$pristine || this.talkForm.$submitted) {
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

    console.log(this.talk);
    this.state.go('header.tabs.myTalks');


  }
}

