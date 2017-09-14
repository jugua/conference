import talksComponent from './talks.component';
import reviewTalkPopupComponent from './review-talk-popup/review-talk-popup.component';
import userInfoPopupComponent from './user-info-popup/user-info-popup.component';

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
          Permissions.permitted('ROLE_ORGANISER', currentUser);
          this.resolved = true;
        },
        controllerAs: 'ctrl'
      })
      .state('header.talksReviewTalk', {
        url: '/talks/:reviewTalkId',
        template: '<talks review-talk-id="ctrl.reviewTalkId" ng-if="ctrl.resolved"></talks>',
        resolve: {
          currentUser: Current => Current.current
        },
        controller: function Controller(Permissions, currentUser, $stateParams) {
          Permissions.permitted('ROLE_ORGANISER', currentUser);
          this.resolved = true;

          this.reviewTalkId = $stateParams.reviewTalkId;
        },
        controllerAs: 'ctrl',
      });
  }).component('talks', talksComponent)
    .component('reviewTalkPopup', reviewTalkPopupComponent)
    .component('userInfoPopup', userInfoPopupComponent);
};
