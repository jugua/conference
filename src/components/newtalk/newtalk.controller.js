export default class NewtalkController {
  constructor(currentUser) {
    let mandatory = ['bio', 'job', 'past']
    this.current = currentUser;
    this.talks = [];

    this.isEmptyBio = mandatory.every((el) => {
      if (this.current[el]) {
        return false;
      } else {
        return true;
      }
    })
  }
}

