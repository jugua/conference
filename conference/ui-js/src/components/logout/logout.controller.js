export default class LogoutController {
  constructor(Logout, $state, Current, Talks) {
    'ngInject';

    this.service = Logout;
    this.state = $state;
    this.userService = Current;
    this.talksService = Talks;
    this.logout();
  }

  logout() {
    this.service.logout().then(() => {
      this.userService.clear();
      this.state.go('header.home', {}, { reload: true });
    });
  }
}

