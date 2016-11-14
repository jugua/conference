export default class TalksController {
  constructor(Talks) {
    this.talks = Talks.getAll();
    this.nme=11;
    console.log(this.talks)
  }
}

