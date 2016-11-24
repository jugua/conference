import editEmailComponent from './edit-email.component';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('editEmail', {
        url: '/edit-email',
        template: '<edit-email></edit-email>'
      });
  }).component('editEmail', editEmailComponent);
};
