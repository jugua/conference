export default class TalksController {
  constructor(Talks) {
    "ngInject";
    this.talks = Talks.getAll();
  }
}

