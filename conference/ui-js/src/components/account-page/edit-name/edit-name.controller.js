export default class EditNameController {
  constructor(Current, $timeout, $state) {
    'ngInject';

    this.currentUserService = Current;
    this.timeout = $timeout;
    this.state = $state;
    this.editNameForm = {};
    this.userInfo = {
      fname: this.user.fname,
      lname: this.user.lname,
    };
    this.changed = false;
    this.error = false;
  }

  close() {
    this.changename = false;
  }

  submit() {
    if (this.editNameForm.$invalid) {
      this.showAlert('error');
    } else {
      this.user.fname = this.userInfo.fname;
      this.user.lname = this.userInfo.lname;
      this.currentUserService.updateInfo(this.user);
      this.showAlert('success');
      this.editNameForm.$setPristine();
    }
  }

  showAlert(message) {
    if (message === 'error') {
      this.error = true;
      return;
    }

    if (message === 'success') {
      this.changed = true;
      this.error = false;
      this.timeout(() => {
        this.changed = false;
        this.changename = false;
        this.state.go('header.account', {}, { reload: true });
      }, 1000);
    }
  }
}