export default class MyTalksController {
  constructor(Current, Talks, $scope) {
    this.current = Current.current;
    this.talks = Talks.getAll();
    console.log(this.talks)
  }
}

