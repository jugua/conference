import newtalkComponent from './newtalk.component';
import newtalkPopupComponent from './newtalk-popup/newtalk-popup.component';
import fillInfoPopupComponent from './fill-info-popup/fill-info-popup.component';

import controller from './newtalk.controller';
import template from './newtalk.html';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.tabs.myTalks.newtalk', {
        template,
        controller,
        controllerAs: '$ctrl',
        resolve: {
          currentUser: Current => Current.current
        },
        params: { fwdState: null },
      });
  }).component('newtalk', newtalkComponent)
    .component('newtalkPopup', newtalkPopupComponent)
    .component('fillInfoPopup', fillInfoPopupComponent);
};
