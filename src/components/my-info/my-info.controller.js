export default class MyInfoController {
  constructor (Users) {
    this.users = Users;
    this.message = {};
    console.log(this.user)
    this.userInfoForm = {};


  }

  submit() {
    if (this.userInfoForm.$invalid) {
      this.toggleMessage();
    } else {
        this.users.updateInfo(this.user);
    }
  }

  toggleMessage(){
    this.showMessage = !this.showMessage;
  }

  toggleSlide (){
    this.showLoad = true;
  }

  toggleSlideBack (){
    this.showLoad = false;
  }
}

