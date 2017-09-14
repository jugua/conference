export default class {
  constructor(Conference, $state) {
    'ngInject';

    this.conferenceService = Conference;
    this.state = $state;

    this.view = 'upcoming';    // default view
    this.conferences = [];
    this.getConferences();     // get initial conferences collection

    this.popupOpen = false;
    this.fillInfoPopupOpen = false;

    this.fillInfoPopupFwdState = 'header.home';
  }

  conditionalClass(condition) {
    return (this.view === condition) ? 'tabs-list__anchor_active' : '';
  }

  showView(viewName) {
    this.view = viewName;
    this.getConferences();
  }

  getConferences() {
    switch (this.view) {
      case 'upcoming':
        this.conferences = this.conferenceService.getUpcoming();
        break;
      case 'past':
        this.conferences = this.conferenceService.getPast();
        break;
      default:
        this.conferences = [];
    }
  }

  signIn() {
    this.state.go('header.sign-in');
  }
}
