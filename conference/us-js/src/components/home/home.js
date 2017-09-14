import homeComponent from './home.component';
import homeGuestComponent from './home-guest/home-guest.component';
import homeSpeakerComponent from './home-speaker/home-speaker.component';
import homeOrganiserComponent from './home-organiser/home-organiser.component';
import homeAdminComponent from './home-admin/home-admin.component';
import newConferencePopupComponent from './home-admin/new-conference-popup/new-conference-popup.component';
import conferenceCardComponent from './conference-card/conference-card.component';

export default (app) => {
  app.config(($stateProvider) => {
    'ngInject';

    $stateProvider
      .state('header.home', {
        url: '/',
        template: '<home user="ctrl.currentUser"></home>',
        resolve: {
          currentUser: Current => Current.current
        },
        controller(currentUser) {
          this.currentUser = currentUser;
        },
        controllerAs: 'ctrl',
      });
  }).component('home', homeComponent)
    .component('homeGuest', homeGuestComponent)
    .component('homeSpeaker', homeSpeakerComponent)
    .component('homeOrganiser', homeOrganiserComponent)
    .component('homeAdmin', homeAdminComponent)
    .component('newConferencePopup', newConferencePopupComponent)
    .component('conferenceCard', conferenceCardComponent);
};
