export default class {
  constructor(Constants) {
    'ngInject';

    this.defaultAva = Constants.ava;   // default ava path constant
  }
  get fullName() {
    if (this.user.fname) {
      return `${this.user.fname} ${this.user.lname}`;
    }
    return '';  // empty string instead of direct temporary visible 'undefined undefined'
  }
  get ava() {
    return this.user.photo ? this.user.photo : this.defaultAva;   // show default ava until link is available
  }
  close() {
    this.onHideUserInfoPopup();
  }
}