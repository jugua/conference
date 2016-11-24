export default class EditPasswordController {
  constructor(Constants) {
    'ngInject';

    this.editPasswordForm = {};
    this.passwords = {};
    this.passwordConstants = Constants.passwordConstants;
    this.changed = false;
    this.error = false;
    this.errorMessage = '';

    this.errorMessages = {
      nonSpace: 'Please use at least one non-space character in your password.',
      minLength: 'Your password must be at least 6 characters long. Please try another.',
      maxLength: 'Your password should not be longer than 30 characters. Please try another.',
      require: 'All fields are mandatory. Please make sure all required fields are filled out.',
      passwordsMatch: 'Passwords do not match.',
      wrongPassword: 'Your password is wrong.'
    };
  }

  changePassword() {
    this.error = false;
    this.errorMessage = '';

    this.checkMatchPasswords();
  }

  checkMatchPasswords() {
    this.editPasswordForm.newPassword.$setValidity('passwords_match', true);
    this.editPasswordForm.confirmNewPassword.$setValidity('passwords_match', true);

    if (this.passwords.newPassword === this.passwords.confirmNewPassword) {
      return true;
    }

    this.error = true;
    this.errorMessage = this.errorMessages.passwordsMatch;
    this.editPasswordForm.newPassword.$setValidity('passwords_match', false);
    this.editPasswordForm.confirmNewPassword.$setValidity('passwords_match', false);
    return false;
  }
}

