export default class ManageUsersController {
  constructor(Current, ManageUsers, User) {
    'ngInject';

    this.current = Current.current;

    this.manageUsersService = ManageUsers;
    this.userService = User;

    this.users = ManageUsers.getAll();
  }
}