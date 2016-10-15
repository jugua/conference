export default class HeaderController {
  constructor (user, Menus, $state ) {
    this.role = user.role;
    this.name = user.name || 'Your Account';
    this.menu = Menus.getMenu();
    this.menuHidden = false;
    }
  }


// this.role