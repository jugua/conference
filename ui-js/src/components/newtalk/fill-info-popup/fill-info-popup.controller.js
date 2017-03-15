export default class {
  constructor($state) {
    this.state = $state;
  }

  submit() {
    this.state.go('header.tabs.myInfo', { fwdState: this.fwdState });
  }

}