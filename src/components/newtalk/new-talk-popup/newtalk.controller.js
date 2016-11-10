export default class NewtalkController {
  constructor(Menus, Current, $state) {
    this.currentUserService = Current;
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
    let answer = this.currentUserService.addTalk(this.talk);
    console.log(answer);
    if (!this.currentUserService.current.talks instanceof Array) {
      this.currentUserService.current.talks = [];
    }
    this.currentUserService.current.talks.push(this.talk);
    this.state.go('header.tabs.myTalks');


  }
}

