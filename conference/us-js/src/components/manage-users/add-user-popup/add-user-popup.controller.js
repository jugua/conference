export default class AddUserPopupContropller {
  constructor(AddUserService, Constants) {
    'ngInject';

    this.service = AddUserService;
    this.user = {};
    this.userForm = {};
    this.formSent = false;
    this.passwordPattern = Constants.password;
    this.emailPattern = Constants.email;
    this.passwordPattern = Constants.password;

    this.roles = ['ROLE_SPEAKER', 'ROLE_ORGANISER'];
    this.user.roleName = this.roles[0];   // pre-selected option
  }

  close() {
    this.onHideAddPopup();
  }

  addUser() {
    if (this.userForm.$valid) {
      if (this.user.password !== this.user.confirm) {
        this.setPasswordsMatch(false);
        return;
      }

      this.formSent = true;

      this.service.addUser(this.user,
        () => {  // success callback
          this.userForm.$setPristine();
          this.onUpdateUsers({ newUser: this.user });
          this.onHideAddPopup();
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
