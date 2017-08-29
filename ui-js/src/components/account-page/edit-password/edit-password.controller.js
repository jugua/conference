export default class EditPasswordController {
  constructor(EditPasswordService, Constants, $timeout) {
    'ngInject';

    this.timeout = $timeout;
    this.editPasswordService = EditPasswordService;
    this.passwordPattern = Constants.password;

    this.editPasswordForm = {};
    this.passwords = {};
    this.changed = false;
    this.error = false;
    this.errorMessage = null;

    this.errorMessages = this.editPasswordService.getErrors();
  }

  closeEditPassword() {
    this.passwords = {};
    this.error = false;
    this.errorMessage = null;
    this.editPasswordForm.$setPristine();
    this.editing = false;
  }

  changePassword() {
    this.setToDefault();
    if (this.editPasswordForm.$valid && this.checkMatchPasswords()) {
      this.editPasswordService.changePassword(this.passwords)
        .then(() => {
          this.successChanged();
        })
        .catch((error) => {
          this.errorChanged(error.data);
        });
      return;
    }

    this.showError();
  }

  successChanged() {
    this.passwords = {};
    this.editPasswordForm.$setPristine();
    this.changed = true;
    this.timeout(() => {
      this.changed = false;
      this.editing = false;
    }, 1000);
  }

  errorChanged(error) {
    if (error.fields) {
      error.fields.forEach((field) => {
        this.editPasswordForm[field].$setValidity(error.error, false);
      });
      this.showError();
    }
  }

  checkMatchPasswords() {
    if (this.passwords.newPassword === this.passwords.confirmNewPassword) {
      return true;
    }

    this.editPasswordForm.newPassword.$setValidity('passwords_match', false);
    this.editPasswordForm.confirmNewPassword.$setValidity('passwords_match', false);
    return false;
  }

  setToDefault() {
    this.error = false;
    this.errorMessage = null;

    this.editPasswordForm.currentPassword.$setValidity('wrong_password', true);
    this.editPasswordForm.newPassword.$setValidity('passwords_match', true);
    this.editPasswordForm.confirmNewPassword.$setValidity('passwords_match', true);
  }

  showError() {
    this.error = true;

    if (this.editPasswordForm.currentPassword.$error.required ||
      this.editPasswordForm.newPassword.$error.required ||
      this.editPasswordForm.confirmNewPassword.$error.required) {
      this.errorMessage = this.errorMessages.require;
      return;
    }

    if (this.editPasswordForm.newPassword.$error.minlength ||
      this.editPasswordForm.confirmNewPassword.$error.minlength) {
      this.errorMessage = this.errorMessages.minLength;
      return;
    }

    if (this.editPasswordForm.newPassword.$error.maxlength ||
      this.editPasswordForm.confirmNewPassword.$error.maxlength) {
      this.errorMessage = this.errorMessages.maxLength;
      return;
    }

    if (this.editPasswordForm.newPassword.$error.pattern ||
      this.editPasswordForm.confirmNewPassword.$error.pattern) {
      this.errorMessage = this.errorMessages.nonSpace;
      return;
    }

    if (this.editPasswordForm.newPassword.$error.passwords_match ||
      this.editPasswordForm.confirmNewPassword.$error.passwords_match) {
      this.errorMessage = this.errorMessages.passwordsMatch;
      return;
    }

    if (this.editPasswordForm.currentPassword.$error.wrong_password) {
      this.errorMessage = this.errorMessages.wrongPassword;

    }
  }
}

