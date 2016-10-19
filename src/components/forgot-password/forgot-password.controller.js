export default class ForgotPasswordController {
  constructor() {
    this.forgotten = false;
    this.mail = '';
  }

  forgot() {
    this.successfulForgot();
  }

  successfulForgot() {
    this.forgotten = true;
  }
}

