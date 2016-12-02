export default class AccountPageController {
  constructor() {
    'ngInject';

    this.controler = 'controller';
    this.editName = false;
    this.editEmail = false;
    this.editPass = false;
  }
  showEditname() {
    this.editName = !true;
    this.editPass = false;
  }
  showEditemail() {
    this.editEmail = true;
  }
  showEditpass() {
    this.editPass = true;
  }
}

