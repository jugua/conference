export default class ForgotPasswordController {
  constructor(ForgotPasswordService, Constants) {
    'ngInject';

    this.user = {};
    this.userForm = {};
    this.service = ForgotPasswordService;
    this.forgotten = false;
    this.emailPattern = Constants.email;
  }

  restore() {
    if (this.userForm.$valid) {
      this.service.restore(this.user,
        () => {  // success callback
          this.forgotten = true;
        },
        (error) => {  // error callback
          if (error.data.error === 'email_not_found') {
            this.userForm.mail.$setValidity('email_not_found', false);
          }
        });
    }
  }

  resetEmailNotFound() {
    this.userForm.mail.$setValidity('email_not_found', true);
  }
}

