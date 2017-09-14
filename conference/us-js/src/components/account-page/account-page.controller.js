export default class AccountPageController {
  constructor() {
    'ngInject';

    this.editName = false;
    this.editEmail = false;
    this.editPass = false;
  }
  showEditname() {
    this.editName = true;
    this.editEmail = false;
    this.editPass = false;
  }
  showEditemail() {
    this.editEmail = true;
    this.editPass = false;
    this.editName = false;
  }
  showEditpass() {
    this.editPass = true;
    this.editEmail = false;
    this.editName = false;
  }
}

