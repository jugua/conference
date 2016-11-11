export default class LogoutController {
  constructor(Logout, $state) {
    this.servis = Logout;
    this.state = $state
    this.logout()
  }

  logout() {
    this.servis.logout().then(() => {
      this.state
    })
  }
}

