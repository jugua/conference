export default class MyInfoController {
  constructor(Current, $scope, $state, $stateParams) {
    'ngInject';

    this.state = $state;
    this.stateParams = $stateParams;
    this.currentUserService = Current;

    this.alertVisible = false;
    this.confirmVisible = false;
    this.message = {};

    this.messages = {
      error: {
        title: 'Error',
        content: 'Please fill in all mandatory fields',
      },
      success: {
        title: 'Saved',
        content: 'Changes saved successfully',
      },
      leave: {
        title: 'Attention',
        content: 'Would you like to save changes?',
      }
    };

    this.userInfoForm = {};

    this.event = $scope.$on('$stateChangeStart', (e, toState) => {
      if (this.userInfoForm.$pristine ||
         (this.userInfoForm.$submitted
         && this.userInfoForm.$valid
         && this.userInfoForm.$pristine)) {
        return;
      }
      e.preventDefault();
      this.nextState = toState;
      this.showConfirm('leave');
    });
  }

  submit() {
    if (this.userInfoForm.$invalid) {
      this.showAlert('error');
    } else {
      this.currentUserService.updateInfo(this.user);
      this.showAlert('success');
      this.userInfoForm.$setPristine();
      this.state.go(this.stateParams.fwdState);
    }
  }

  showAlert(message) {
    this.message = this.messages[message];
    this.alertVisible = true;
  }
  showConfirm(message) {
    this.message = this.messages[message];
    this.confirmVisible = true;
  }

  hideMessage() {
    this.alertVisible = false;
    this.confirmVisible = false;
    this.message = {};
  }

  saveChangesBeforeOut() {
    this.hideMessage();
    this.submit();

    this.userInfoForm.$setSubmitted();
    if (this.userInfoForm.$valid) {
      this.event();
      this.state.go(this.nextState.name);
    }
  }

  cancelChanges() {
    this.hideMessage();
    this.event();
    this.state.reload();
    this.state.go(this.nextState.name);
  }
}

