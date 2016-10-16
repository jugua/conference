export default class HeaderController {

  constructor(user, Menus, $state, $scope) {
    this.role = user ? user.roles : '';
    this.name = (user) ? (user.fname + '`s') : 'Your';
    this.menu = Menus.getMenu(this.role);
    this.menuHidden = false;
    $scope.$on('signInEvent', () => {
      $state.reload();
    });
  }
}