export default class MyInfoController {
  constructor() {
   // this.service = MyInfo;
    this.showLoad = false;
  }

  toggleSlide (){
    this.showLoad = true;
  }

  toggleSlideBack (){
    this.showLoad = false;
  }
}

