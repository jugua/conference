export default class MyTalksController {
  constructor(Current, Talks, Menus) {
    'ngInject';

    this.current = Current.current;
    this.talks = Talks.getAll();
    this.filter = {};
    this.menuService = Menus;
    this.showFilters = true;

    this.editTalkObj = {};
    this.showEditPopup = false;
  }
  showSettings() {
    this.showFilters = !this.showFilters;
  }
  edit(talk) {  // talk object passed
    this.editTalkObj = talk;
    this.showEditPopup = true;
  }
  hideEditPopup() {
    this.editTalkObj = {};
    this.showEditPopup = false;
  }
}