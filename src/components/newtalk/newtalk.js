import newtalkComponent from './new-talk-popup/newtalk.component';
import controlPopupComponent from './control-popup/control-popup.component';
import controller from './newtalk.controller';
import template from './newtalk.html';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.tabs.myTalks.newtalk', {
        template,
        controller,
        controllerAs:'vm',
        resolve: {
          currentUser: Current => Current.current
        },
      });
  }).component('newtalk', newtalkComponent)
     .component('controlPopup', controlPopupComponent);
};
