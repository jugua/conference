export default class {
  constructor() {
    this.defaultLanding = true;
  }

  landingType(type) {
    switch (type) {
      case 'ROLE_SPEAKER':
      case 'ROLE_ORGANISER':
      case 'ROLE_ADMIN':
        if (this.user && this.user.roles && this.user.roles.indexOf(type) > -1) {
          this.defaultLanding = false;
          return true;
        }
        return false;
      default:
        return false;   // default case - unknown landing type
    }
  }
}