export default class TalksController {
  constructor(Talks) {
    this.talks = Talks.getAll();
  }
}

