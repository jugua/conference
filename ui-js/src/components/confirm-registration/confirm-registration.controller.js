export default class ConfirmRegistrationController {
  constructor($state, confirmRegistrationService, $stateParams) {
    'ngInject';

    this.state = $state;
    this.token = $stateParams.token;
    this.confirmService = confirmRegistrationService;

    this.confirm();
  }

  confirm() {
    this.confirmService.confirmRegistration(this.token)
      .then(
        () => {
          this.state.go('header', {}, { reload: true });
        },
        () => {
          this.state.go('header.invalidLink', {}, { reload: true });
        }
      );
  }
}