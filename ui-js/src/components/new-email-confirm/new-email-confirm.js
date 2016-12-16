import service from './new-email-confirm.service';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.emailConfirm', {
        url: '/newEmailConfirm/:token',
        onEnter($state, $stateParams, Current, NewEmailConfirmService) {
          'ngInject';


          console.log('onEnter:');
          console.log($stateParams.token);
          console.log(NewEmailConfirmService);
          console.log(NewEmailConfirmService.wat);
          console.log(NewEmailConfirmService.emailConfirm);
          console.log(Current);

          NewEmailConfirmService.emailConfirm($stateParams.token)
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
  }).service('NewEmailConfirmService', service);
};
