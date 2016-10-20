export default class MyInfoController {
  constructor() {

    this.showLoad = false;
  }

  toggleSlide (){
    this.showLoad = true;
  }

  toggleSlideBack (){
    this.showLoad = false;
  }
}

