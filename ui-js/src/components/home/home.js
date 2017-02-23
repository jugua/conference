import homeComponent from './home.component';
import homeSpeakerComponent from './home-speaker/home-speaker.component';
import homeAdminComponent from './home-admin/home-admin.component';
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
    .component('homeSpeaker', homeSpeakerComponent)
    .component('homeAdmin', homeAdminComponent)
    .component('conferenceCard', conferenceCardComponent);


};
