export default class SignInController {
  constructor(signInService, $scope) {
    this.user = {};
    this.userForm = {};
    this.service = signInService;
    this.scope = $scope;
    this.emailPattern = new RegExp('^[a-z0-9._%+-]+@[a-z0-9.-]+.[a-z]{2,6}$', 'i');
  }

  login() {
    this.userForm.password.$setValidity('password_auth_err', true);
    this.userForm.mail.$setValidity('login_auth_err', true);

    if (this.userForm.mail.$error.required || this.userForm.mail.$error.pattern) {
      this.userForm.mail.$setValidity('login_auth_err', false);
    }

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

  emitCloseDropdown() {
    this.scope.$emit('closeDropdown');
  }
}
// no_info_auth_err