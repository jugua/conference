export default class SignUpController {
  constructor(SignUpService, Constants) {
    'ngInject';

    this.service = SignUpService;

    this.emailPattern = Constants.email;
    this.passwordPattern = Constants.password;

    this.user = {};
    this.userForm = {};

    this.showPopup = false;
    this.formSent = false;
  }

  signUp() {
    if (this.userForm.$valid) {
      if (this.user.password !== this.user.confirm) {
        this.setPasswordsMatch(false);
        return;
      }

      this.formSent = true;

      this.service.signUp(this.user,
        () => {  // success callback
          this.userForm.$setPristine();
          this.showPopup = true;
        },
        (error) => {  // error callback
          if (error.data.error === 'email_already_exists') {
            this.userForm.mail.$setValidity('email_already_exists', false);
            this.formSent = false;
          }
        });
    }
  }

  setPasswordsMatch(bool) {
    this.userForm.password.$setValidity('passwords_match', bool);
    this.userForm.confirm.$setValidity('passwords_match', bool);
  }

  resetEmailAlreadyExists() {
    this.userForm.mail.$setValidity('email_already_exists', true);
  }
}
