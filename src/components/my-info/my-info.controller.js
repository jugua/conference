export default class MyInfoController {
  constructor(Users, $scope, $state) {
    this.state = $state;
    this.users = Users;
    this.errorMessage = {
      title: 'Error',
      p: 'Please, fill in all mandatory fields'
    };
    this.sucessMessage = {
      title: 'Ok',
      p: 'Changes saved successfully'
    };
    this.goAwayMessage = {
      title: 'Info',
      p: 'Would you like to save changes?',
      showBtns: true
    };
    this.userInfoForm = {};

    this.event = $scope.$on('$stateChangeStart', (e, toState) => {
      if (this.userInfoForm.$pristine ||
         (this.userInfoForm.$submitted && this.userInfoForm.$valid)) {
        return;
      }
      e.preventDefault();
      this.nextState = toState;
      this.showMessage('goAwayMessage');
    });
  }

  submit() {
    if (this.userInfoForm.$invalid) {
      this.showMessage('errorMessage');
    } else {
      this.users.updateInfo(this.user);
      this.showMessage('sucessMessage');
    }
  }

  showMessage(messageType) {
    this.message = this[messageType];
    this.isShowMessage = true;
  }

  hideMessage() {
    this.isShowMessage = false;
  }

  toggleSlide() {
    this.showLoad = true;
  }

  toggleSlideBack() {
    this.showLoad = false;
  }

  saveChangesBeforeOut() {
    this.submit();
    if (this.userInfoForm.$valid) {
      this.event();
      this.state.go(this.nextState.name);
    }
  }

  cancelChanges() {
    this.event();
    this.state.reload();
    this.state.go(this.nextState.name);
  }
}

