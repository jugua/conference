export default class LogoutController {
  constructor(Logout, $state) {
    this.service = Logout;
    this.state = $state;
    this.logout()
  }

  logout() {
    this.service.logout().then(() => {
      this.state.go('header.home', {}, {reload: true});
    })
  }
}

