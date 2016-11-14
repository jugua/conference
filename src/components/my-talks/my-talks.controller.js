export default class MyTalksController {
  constructor(Current, Talks) {
    this.current = Current.current;
    this.talks = Talks.getAll();
  }
}

