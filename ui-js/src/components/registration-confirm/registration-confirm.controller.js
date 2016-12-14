export default class registrationConfirmController {
  constructor($state, $stateParams, RegistrationConfirmService, Current) {
    'ngInject';

    this.state = $state;
    this.currentService = Current;
    this.token = $stateParams.token;
    this.confirmService = RegistrationConfirmService;

    this.confirm();
  }

  confirm() {
    this.confirmService.registrationConfirm(this.token)
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