export default class {
  constructor(AddUserService, Constants) {
    'ngInject';
    this.userData = {
      mail: Date.now().toString() + 'sodddme@mddddsasadsdil.com',
      password: '123123123',
      confirm: '123123123',
      fname: 'ololo',
      lname: 'trololo',
      roleName: 'ROLE_SPEAKER'
    };
    this.AddUserService = AddUserService;
    this.user = {};
    this.userForm = {};
    this.formSent = false;
    this.passwordPattern = Constants.password;
  }


  addUser() {
    this.AddUserService.addUser().add({}, this.userData, res => {
      debugger;
      this.res = res;
      res.$save();
      let a = 1;
    });
  }

  close() {
    this.onHideAddPopup();
  }
  // addUser() {
  //   if (this.userForm.$valid) {
  //     if (this.user.password !== this.user.confirm) {
  //       this.setPasswordsMatch(false);
  //       return;
  //     }
  //
  //     this.formSent = true;
  //
  //     this.service.AddUser(this.user,
  //       () => {  // success callback
  //         this.userForm.$setPristine();
  //       },
  //       (error) => {  // error callback
  //         if (error.data.error === 'email_already_exists') {
  //           this.userForm.mail.$setValidity('email_already_exists', false);
  //           this.formSent = false;
  //         }
  //       });
  //   }
  // }
}
