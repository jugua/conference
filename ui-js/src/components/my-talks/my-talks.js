import myTalksComponent from './my-talks.component';
import editTalkPopup from './edit-talk-popup/edit-talk-popup.component';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.tabs.myTalks', {
        url: '/my-talks',
        template: '<my-talks ng-if="ctrl.resolved"></my-talks>',
        resolve: {
          currentUser: Current => Current.current
        },
        controller: function myTalkPreController(currentUser, Permissions) {
          Permissions.permitted('ROLE_SPEAKER', currentUser);
          this.resolved = true;
          this.currentUser = currentUser;
        },
        controllerAs: 'ctrl'
      });
  }).component('myTalks', myTalksComponent)
    .component('editTalkPopup', editTalkPopup);
};
