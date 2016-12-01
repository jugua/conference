export default class EditEmailController {
  constructor(Current, Constants, $timeout) {
    'ngInject';

    this.currentUserService = Current;
    this.emailPattern = Constants.email;

    this.timeout = $timeout;
    this.timeoutHandler = null;

    this.editEmailForm = {};
    this.newEmail = null;

    this.error = false;
    this.errorMessage = null;

    this.changed = false;

    this.tooltipVisible = false;
  }
  changeEmail() {
    if (this.editEmailForm.newEmail.$error.pattern ||
        this.editEmailForm.newEmail.$error.required) {
      this.showError();
    } else if (this.editEmailForm.$valid) {

      this.currentUserService.updateInfo({ mail: this.newEmail });
      this.user.mail = this.newEmail;

      this.showConfirm();
    }
  }
  showError() {
    this.error = true;
    this.errorMessage = 'Please enter a valid email address';
  }
  showConfirm() {
    this.error = false;
    this.errorMessage = null;

    this.confirm = true;
    this.confirmMessage = `We've sent a confirmation link to your new email address,
                           please check your inbox and confirm your changes`;

    this.changed = true;

    this.timeout(() => {
      this.confirm = false;
      this.confirmMessage = null;
      this.changed = false;
      this.closeEditEmail();
    }, 2000);
  }
  closeEditEmail() {
    this.error = false;
    this.errorMessage = null;
    this.newEmail = null;
    this.editEmailForm.$setPristine();
    this.editing = false;
  }
  showTooltip() {
    this.tooltipVisible = true;
    this.timeoutHandler = this.timeout(() => {
      this.tooltipVisible = false;
    }, 3000);
  }
  hideTooltip() {
    this.tooltipVisible = false;
    if (this.timeoutHandler) {  // cancel a pending timeout promise if any
      this.timeout.cancel(this.timeoutHandler);
    }
  }
}
