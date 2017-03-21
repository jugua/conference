export default class {
  constructor($state) {
    'ngInject';

    this.state = $state;
  }

  submit() {
    this.state.go('header.tabs.myInfo', { fwdState: this.fwdState });
  }

}