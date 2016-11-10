export default class NewtalkController {
  constructor(Menus, Current, $state) {
    this.currentUserService = Current;
    this.selectService = Menus;
    this.state = $state;
    this.talkForm = {};
    this.talk = {};
    this.isShownPopup = false;
  }

  close() {
    if (this.talkForm.$pristine || this.talkForm.$submitted || isEmptyForm(this.talk)) {
      this.state.go('header.tabs.myTalks');
      return;
    }

    if (this.talkForm.$dirty && !this.talkForm.$submitted) {
      this.isShownPopup = true;
    }

    function isEmptyForm(form) {
      let isEmpty = true;
      for (let field in form) {
        if (form[field] && form[field].length) {
          isEmpty = false;
          break;
        }
      }

      return isEmpty;
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

    let answer = this.currentUserService.addTalk(this.talk);
    console.log(answer);

    if ( !(this.currentUserService.current.talks instanceof Array) ) {
      this.currentUserService.current.talks = [];
    }

    this.currentUserService.current.talks.push(this.talk);
    this.state.go('header.tabs.myTalks');
  }
}

