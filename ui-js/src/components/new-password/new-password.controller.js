export default class NewPasswordController {
  constructor(NewPasswordService) {
    'ngInject';

    this.newPasswordService = NewPasswordService;

    this.newPasswordForm = {};
    this.passwords = {};

  }
}

