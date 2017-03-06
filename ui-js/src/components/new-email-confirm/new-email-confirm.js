import service from './new-email-confirm.service';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.emailConfirm', {
        url: '/newEmailConfirm/:token',
        onEnter($state, $stateParams, Current, NewEmailConfirmService) {
          'ngInject';

          NewEmailConfirmService.emailConfirm($stateParams.token)
            .then(() => {
              Current.getInfo();
              $state.go('header.home', {}, { reload: true });
            })
            .catch(() => {
              $state.go('header.invalidLink', {}, { reload: true });
            });
        }
      });
  }).service('NewEmailConfirmService', service);
};
