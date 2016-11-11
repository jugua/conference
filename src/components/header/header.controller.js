/* global angular */

export default class HeaderController {

  constructor(user, Menus, Current, SignIn, $state, $scope, $document) {
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

    const off = $scope.$on('$viewContentLoaded', () => {
      $document.on('click', (e) => {
        const target = angular.element(e.target);

        let parents = target;
        let dropDownArea = false;

        if (!this.menuHidden) {
          return;
        }
        // to not use jQuery for one method parents() not supported in JQLight
        // so this loop finds all parents
        while (parents.parent().length) {
          if (parents.hasClass('js-dropdown')) {
            dropDownArea = true;
            break;
          }
          parents = parents.parent();
        }

        if (!dropDownArea) {
          this.menuHidden = false;
          $scope.$apply();
        }
      });

      off();
    });

    this.Current = Current;
    this.$state = $state;

    this.signIn = SignIn;
  }

  logout() {
    this.$state.go('header.home', {logout:true}).then(() => {
      this.Current.logout()
        .then(
          () => {  // success callback
            console.log('dfddf');
            window.localStorage.removeItem('userInfo');
            this.signIn.callTheEvent();
          }
        );
    }, (err)=>{
    });

  }
}