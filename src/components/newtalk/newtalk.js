import newtalkComponent from './new-talk-popup/newtalk.component';
import controlPopupComponent from './control-popup/control-popup.component';
import controller from './newtalk.controller';

export default (app) => {
  app.config(($stateProvider) => {
    $stateProvider
      .state('header.tabs.myTalks.newtalk', {
        template: '<newtalk></newtalk> <br><control-popup></control-popup>',
        controller
      });
  }).component('newtalk', newtalkComponent)
     .component('controlPopup', controlPopupComponent);
};
