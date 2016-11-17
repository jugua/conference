export default class SignUpController {
  constructor(SignUp, Constants) {
    'ngInject';

    this.user = {};
    this.userForm = {};
    this.service = SignUp;
    this.showPopup = false;
    this.emailPattern = Constants.email;
    this.passwordPattern = Constants.password;
  }

  signUp() {
    if (this.userForm.$valid) {
      if (this.user.password !== this.user.confirm) {
        this.setPasswordsMatch(false);
        return;
      }

      this.service.signUp(this.user,
        () => {  // success callback
          this.userForm.$setPristine();
          this.showPopup = true;
        },
        (error) => {  // error callback
          if (error.data.error === 'email_already_exists') {
            this.userForm.mail.$setValidity('email_already_exists', false);
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
