export default class SignUpController {
  constructor(SignUp) {
    this.user = {};
    this.userForm = {};
    this.service = SignUp;
    //console.log(this.service.signUp);
  }

  signUp() {
    console.log(this.service);
    console.log(this.user);
    this.userForm.mail.$setValidity('email_already_exists_err', true);

    if (this.userForm.$valid) {
      this.service.signUp(this.user)
        .then((result) => {
          console.log('success');
          console.log(result);
          alert('user successfully created');
        }, (error) => {
          console.log(error.data.error);
          if (error.data.error === 'email_already_exists') {
            console.log('setting already exits');
            this.userForm.mail.$setValidity('email_already_exists_err', false);
          }
        });

      //this.service.signUp(this.user);

    }
  }

}

