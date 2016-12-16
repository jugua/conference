export default class NewPasswordController {
  constructor($state, $stateParams, newPasswordService, Current, Constants) {
    'ngInject';

    this.newPasswordService = newPasswordService;
    this.state = $state;
    this.currentService = Current;
    this.token = $stateParams.token;
    this.newPasswordForm = {};
    this.passwords = {};
    this.passwordPattern = Constants.password;
    this.confirm();

  }

  confirm (){
    this.newPasswordService.passConfirm(this.token)
      .then(null,
        () => {
          this.state.go('header.invalidLink', {}, { reload: true });
        }
      );
  }

  newPassword (){
    this.setToDefault();
    if (this.newPasswordForm.$valid && this.checkMatchPassword()){
      this.newPasswordService.newPassword(this.passwords)
    }
  }
}

