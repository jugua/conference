export default class ConfirmRegistrationController {
  constructor($state, confirmRegistrationService, $stateParams) {
    this.state = $state;
    this.token = $stateParams.token;
    this.confirmService = confirmRegistrationService;
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