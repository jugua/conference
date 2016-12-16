export default class EditEmailController {
  constructor(Constants, EditEmailService, $timeout) {
    'ngInject';

    this.timeout = $timeout;
    this.editEmailService = EditEmailService;

    this.emailPattern = Constants.email;
    this.messages = this.editEmailService.getMessages();

    this.timeoutHandler = null;

    this.editEmailForm = {};
    this.newEmail = null;

    this.error = false;
    this.errorMessage = null;

    this.changed = false;

    this.tooltipVisible = false;

    this.pendingChange = false;
    this.confirmTimeout = 3600000;  // default 1 hour

    this.checkPendingUpdate();
  }
  checkPendingUpdate() {
    this.editEmailService.checkPendingUpdate()
      .then((res) => {
        console.log('successful result:');
        console.log(res);
        if (res.data.answer === 'pending_email_change_found') {
          console.log('pending change found');
          this.pendingChange = true;
          this.confirmTimeout = res.data.secondsToExpiry;
          this.showConfirm();
        }
      })
      .catch((err) => {
        console.log('error:');
        console.log(err);
      });
  }
  changeEmail() {
    if (this.editEmailForm.newEmail.$error.pattern ||
        this.editEmailForm.newEmail.$error.required) {
      this.showError(this.messages.errInvalidEmail);
    } else if (this.user.mail === this.newEmail) {
      this.showError(this.messages.errEmailIsTheSame);
      this.editEmailForm.newEmail.$setValidity('email_is_the_same', false);
    } else if (this.editEmailForm.$valid) {
      this.editEmailService.updateEmail(this.newEmail)
        .then(() => {
          // this.user.mail = this.newEmail;
          this.pendingChange = true;
          this.showConfirm(this.messages.confirmationSent);
        })
        .catch((err) => {
          if (err.data.error === 'email_already_exists') {
            this.editEmailForm.newEmail.$setValidity('email_already_exists', false);

            const errMessage = this.messages.errEmailAlreadyExists.replace('{{email}}', this.newEmail);
            this.showError(errMessage);
          } else {  // unknown error, default handler
            this.showError(err.data.error);
          }
        });
    }
  }
  showError(message) {
    this.error = true;
    this.errorMessage = message;
  }
  showConfirm(message) {
    this.error = false;
    this.errorMessage = null;

    this.confirm = true;
    this.confirmMessage = message;

    this.changed = true;

    this.timeout(() => {
      this.confirm = false;
      this.confirmMessage = null;
      this.changed = false;
      this.closeEditEmail();
    }, this.confirmTimeout);
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
  resetEmailValidity() {
    this.editEmailForm.newEmail.$setValidity('email_is_the_same', true);
    this.editEmailForm.newEmail.$setValidity('email_already_exists', true);
  }
}
