export default class SignUpController {
  constructor(SignUp) {
    this.user = {};
    this.userForm = {};
    this.service = SignUp;
    this.showPopup = false;
  }

  signUp() {
    // this.resetEmailAlreadyExists();     // reset
    // this.setPasswordsMatch(true);

    if (this.userForm.$valid) {

      // check if passwords match
      if (this.user.password !== this.user.confirm) {
        this.setPasswordsMatch(false);
        return;
      }

      this.service.signUp(this.user,
        (result) => {  // success callback
          this.userForm.$setPristine();
          console.log('result:');
          console.log(result);
          this.showPopup = true;
          console.log(this.showPopup);
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
