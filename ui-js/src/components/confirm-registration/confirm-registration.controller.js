export default class ConfirmRegistrationController {
  constructor($state, $stateParams, confirmRegistrationService, Current) {
    'ngInject';

    this.state = $state;
    this.currentService = Current;
    this.token = $stateParams.token;
    this.confirmService = confirmRegistrationService;

    this.confirm();
  }

  confirm() {
    this.confirmService.confirmRegistration(this.token)
      .then(
        () => {
          this.currentService.getInfo();
          this.state.go('header.home', {}, { reload: true });
        },
        () => {
          this.state.go('header.invalidLink', {}, { reload: true });
        }
      );
  }
}