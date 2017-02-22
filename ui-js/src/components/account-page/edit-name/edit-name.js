import editNameComponent from './edit-name.component';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('editName', {
        url: '/edit-name',
        template: '<edit-name></edit-name>'
      });
  }).component('editName', editNameComponent);
};
