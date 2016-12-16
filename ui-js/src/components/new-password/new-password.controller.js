export default class NewPasswordController {
  constructor(NewPasswordService) {
    'ngInject';

    this.newPasswordService = NewPasswordService;

    this.newPasswordForm = {};
    this.passwords = {};
    this.passwordPattern = Constants.password;

  }

  newPassword () {
    if (this.newPasswordForm.$valid && this.checkMatchPassword()){
      this.newPasswordService.newPassword(this.passwords)
    }
  }
}

