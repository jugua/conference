export default class NewtalkController {
  constructor(currentUser) {
    const mandatory = ['bio', 'job', 'company']
    this.current = currentUser;

    this.isEmptyBio = mandatory.every((el) => {
      if (this.current[el]) {
        return false;
      } else {
        return true;
      }
    })
  }
}

