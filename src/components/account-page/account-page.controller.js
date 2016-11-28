export default class AccountPageController {
  constructor() {
    'ngInject';

    this.controler = 'controller';
    this.editName = false;
    this.editEmail = false;
    this.editPass = false;
  }
  showEditname() {
    this.editName = true;
  }
  showEditemail() {
    this.editEmail = true;
  }
  showEditpass() {
    this.editPass = true;
  }
}

