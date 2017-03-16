export default class SignInController {
  constructor($scope, $state, $location, signInService, Constants, LocalStorage) {
    'ngInject';

    this.user = {};
    this.userForm = {};
    this.service = signInService;
    this.scope = $scope;
    this.emailPattern = Constants.email;

    this.state = $state;
    this.location = $location;
    this.localStorageService = LocalStorage;
  }

  login() {
    this.userForm.password.$setValidity('password_auth_err', true);
    this.userForm.mail.$setValidity('login_auth_err', true);
    this.userForm.mail.$setValidity('confirm_reg', true);

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
    if (error === 'confirm_reg') {
      this.userForm.mail.$setValidity(error, false);
    }
  }

  successSignIn() {
    this.user = {};
    this.userForm.$setPristine();

    const redirectStateName = this.localStorageService.getItem('redirectStateName');
    const redirectStateParams = JSON.parse(this.localStorageService.getItem('redirectStateParams'));

    if (redirectStateName) {
      this.localStorageService.removeItem('redirectStateName');
      this.localStorageService.removeItem('redirectStateParams');
      this.state.go(redirectStateName, redirectStateParams, { reload: true });
    } else {
      this.service.callTheEvent();
    }
  }

  emitCloseDropdown() {
    this.scope.$emit('closeDropdown');
  }

  get conditionalWrapperClass() {
    if (this.display === 'full') {
      return 'sign-in-wrapper sign-in-wrapper_full js-dropdown';         // fullscreen
    }
    return 'sign-in-wrapper js-dropdown';    // dropdown, default
  }
}
// no_info_auth_err