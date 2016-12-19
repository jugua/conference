import service from './new-email-confirm.service';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.emailConfirm', {
        url: '/newEmailConfirm/:token',
        onEnter($state, $stateParams, Current, NewEmailConfirmService) {
          'ngInject';

          NewEmailConfirmService.emailConfirm($stateParams.token)
            .then(
              (res) => {
                Current.getInfo();
                $state.go('header.home', {}, { reload: true });
              },
              (err) => {
                $state.go('header.invalidLink', {}, { reload: true });
              }
            );
        }
      });
  }).service('NewEmailConfirmService', service);
};
