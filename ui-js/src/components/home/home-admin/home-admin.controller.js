export default class {
  constructor(Conference) {
    'ngInject';

    this.conferenceService = Conference;

    this.view = 'upcoming';    // default view
    this.conferences = [];
    this.getConferences();     // get initial conferences collection

    this.showNewConferencePopup = false;
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

  openNewConferencePopup() {
    this.showNewConferencePopup = true;
  }

  closeNewConferencePopup() {
    this.showNewConferencePopup = false;
  }
}
