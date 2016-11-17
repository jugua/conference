export default class MyTalksController {
  constructor(Current, Talks, Menus) {
    'ngInject';
    this.current = Current.current;
    this.talks = Talks.getAll();
    this.filter = {};
    this.menuService = Menus;
    this.menuStatus = Menus;
  }
}

