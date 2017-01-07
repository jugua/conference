import talksComponent from './talks.component';
import reviewTalkPopupComponent from './review-talk-popup/review-talk-popup.component';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.talks', {
        url: '/talks',
        template: '<talks ng-if="ctrl.resolved"></talks>',
        resolve: {
          currentUser: Current => Current.current
        },
        controller: function Controller(Permissions, currentUser) {
          Permissions.permitted('o', currentUser);
          this.resolved = true;
        },
        controllerAs: 'ctrl'
      });
  }).component('talks', talksComponent)
    .component('reviewTalkPopup', reviewTalkPopupComponent);
};
