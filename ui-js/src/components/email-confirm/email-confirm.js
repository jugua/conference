// import EmailConfirmController from './email-confirm.controller';
import service from './email-confirm.service';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.emailConfirm', {
        url: '/emailConfirm/:token',
        onEnter($state, $stateParams, Current, EmailConfirmService) {
          'ngInject';


          console.log('onEnter:');
          console.log($stateParams.token);
          console.log(EmailConfirmService);
          console.log(EmailConfirmService.wat);
          console.log(EmailConfirmService.emailConfirm);
          console.log(Current);

          EmailConfirmService.emailConfirm($stateParams.token)
            .then(
              (res) => {
                console.log('resolve success');
                console.log(res);
                console.log(res.data);
                Current.getInfo();
                $state.go('header.home', {}, { reload: true });
              },
              (err) => {
                console.log('resolve error:');
                console.log(err);
                $state.go('header.invalidLink', {}, { reload: true });
              }
            );
        }
      });
  }).service('EmailConfirmService', service);
};
