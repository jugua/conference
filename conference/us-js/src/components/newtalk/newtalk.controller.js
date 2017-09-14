export default class NewtalkController {
  constructor(currentUser, $state, $stateParams) {
    'ngInject';

    const mandatory = ['bio', 'job', 'company'];
    this.current = currentUser;
    this.state = $state;
    this.stateParams = $stateParams;

    this.isEmptyBio = mandatory.every((el) => {
      if (this.current[el]) {
        return false;
      }

      return true;
    });
  }

  popupCloseCallback() {
    this.state.go('header.tabs.myTalks');
  }

  popupSubmitCallback() {
    this.state.go('header.tabs.myTalks', {}, { reload: true });
  }

  fillInfoPopupCloseCallback() {
    this.state.go('header.tabs.myTalks');
  }

  userInfoFilled() {
    const mandatory = ['bio', 'job', 'company'];
    return mandatory.every(el => this.current[el] !== '');
  }
}

