export default class ManageUsersController {
  constructor() {
    'ngInject';

    this.addUserPopup = false;
  }

  hideAddUserPopup() {
    this.addUserPopup = !this.addUserPopup;
  }

  updateUsers() {
    console.log('upd');
  }
}