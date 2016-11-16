export default class MyTalksController {
  constructor(Current, Talks) {
    "ngInject";
    this.current = Current.current;
    this.talks = Talks.getAll();
  }
}

