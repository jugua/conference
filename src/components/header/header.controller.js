export default class HeaderController {

  constructor(user, Menus, Current, $state, $scope) {
    this.role = user ? user.roles : '';
    this.name = (user) ? `${user.fname}'s` : 'Your';
    this.menu = Menus.getMenu(this.role);
    this.menuHidden = false;
    $scope.$on('signInEvent', () => {
      $state.go('header.home', {}, { reload: true });
    });
    $scope.$on('closeDropdown', () => {
      this.menuHidden = false;
    });
    this.Current = Current;
    this.$state = $state;
  }

  logout() {
    this.Current.logout()
      .then(
        () => {  // success callback
          this.$state.go('header.home', {}, { reload: true });
        }
      );
  }
}