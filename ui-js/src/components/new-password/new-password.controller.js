export default class NewPasswordController {
  constructor($state, $stateParams, newPasswordService, Current, Constants) {
    'ngInject';

    this.newPasswordService = newPasswordService;
    this.state = $state;
    this.currentService = Current;
    this.token = $stateParams.tokenId;
    this.newPasswordForm = {};
    this.passwords = {};
    this.passwordPattern = Constants.password;
    this.confirm();
  }

  confirm() {
    this.newPasswordService.passConfirm(this.token)
      .then(null,
        () => {
          this.state.go('header.invalidLink', {}, { reload: true });
        }
      );
  }

  newPassword() {
    this.setPasswordsMatch(true);

    if (this.newPasswordForm.$valid && this.checkMatchPassword()) {
      this.newPasswordService.changePassword(this.passwords, this.token)
        .then(() => {
          this.currentService.getInfo();
          this.state.go('header.home', {}, { reload: true });
        },
          () => {
            this.state.go('header.home', {}, { reload: true });
          });
    }
  }

  setPasswordsMatch(bool) {
    this.newPasswordForm.newPassword.$setValidity('passwords_match', bool);
    this.newPasswordForm.confirmPassword.$setValidity('passwords_match', bool);
  }

  checkMatchPassword() {
    if (this.passwords.newPassword === this.passwords.confirmPassword) {
      return true;
    }

    this.setPasswordsMatch(false);
    return false;
  }
}