export default class SignInController {
  constructor(SignIn) {
    this.user = {};
    this.userForm = {};
    this.service = SignIn;
  }

  login() {
    this.userForm.password.$setValidity('password_auth_err', true);
    this.userForm.mail.$setValidity('login_auth_err', true);

    if (this.userForm.$valid) {
      this.service.login(this.user).then(() => {
        this.successSignIn();
      }, (error) => {
        this.showError(error.data.error);
      });
    }
  }

  showError(error) {
    if (error === 'password_auth_err') {
      this.userForm.password.$setValidity(error, false);
    }

    if (error === 'login_auth_err') {
      this.userForm.mail.$setValidity(error, false);
    }
  }

  successSignIn() {
    this.user = {};
    this.userForm.$setPristine();
    this.service.callTheEvent();
  }
}
// no_info_auth_err