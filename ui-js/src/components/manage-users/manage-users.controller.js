export default class ManageUsersController {
  constructor() {
    'ngInject';

    this.addUserPopup = false;
  }

  hideAddUserPopup() {
    this.addUserPopup = !this.addUserPopup;
  }

  updateUsers(newUser) { // obj
    this.newUser = newUser;
    this.newUser.roles = this.newUser.roleName;
    this.users.push(newUser);
    this.users.sort((userA, userB) => userA.lname.localeCompare(userB.lname));
  }
}