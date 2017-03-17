export default class {
  constructor(Conference, Topic, Type, Language, Level, ManageUsers) {
    'ngInject';

    this.conferenceService = Conference;
    this.topicService = Topic;
    this.typeService = Type;
    this.langService = Language;
    this.levelService = Level;
    this.manageUsersService = ManageUsers;

    this.topics = Topic.query();
    this.types = Type.query();
    this.langs = Language.query();
    this.levels = Level.query();

    this.users = ManageUsers.getAll();

    this.conf = {};
  }

  submit() {
    if (this.form.$invalid) {
      this.submitAttempt = true;
      return;
    }

    function dd(num) {     // Double Digit - add leading zero if needed
      const str = num.toString();
      if (str.length === 1) {
        return `0${str}`;
      }
      return str;
    }

    function formatDate(date) {
      return `${date.getFullYear()}-${dd(date.getMonth() + 1)}-${dd(date.getDate())}`;
    }

    const sendObj = {
      title: this.conf.title,
      description: this.conf.description,
      location: this.conf.location,
      no_dates: this.conf.noDates,
      cfp_no_dates: this.conf.cfpNoDates,
      topics: this.conf.topics,
      types: this.conf.types,
      levels: this.conf.levels,
      languages: this.conf.langs,
      organisers: this.conf.orgs,
    };

    if (this.conf.startDate) { // if is set - format
      sendObj.start_date = formatDate(this.conf.startDate);
    }
    if (this.conf.endDate) {
      sendObj.end_date = formatDate(this.conf.endDate);
    }
    if (this.conf.cfpStartDate) {
      sendObj.cfp_start_date = formatDate(this.conf.cfpStartDate);
    }
    if (this.conf.cfpEndDate) {
      sendObj.cfp_end_date = formatDate(this.conf.cfpEndDate);
    }

    if (!this.conf.noDates) {
      sendObj.no_dates = false;
    }
    if (!this.conf.cfpNoDates) {
      sendObj.cfp_no_dates = false;
    }
    this.conferenceService.save(sendObj, this.onSubmit);
  }

  close() {
    this.onClose();
  }

  addTopic() {
    if (this.newTopic) {
      this.topicService.save(this.newTopic,
      () => {  // upon success - reload topics
        this.newTopic = '';
        this.topics = this.topicService.query();
      });
    }
  }

  addType() {
    if (this.newType) {
      this.typeService.save(this.newType,
      () => {  // upon success - reload topics
        this.newType = '';
        this.types = this.typeService.query();
      });
    }
  }

  multiSelectSize(arrName) {
    return (this[arrName].length > 7) ? 7 : this[arrName].length;
  }

  get orgs() {
    const orgs = this.users.filter(el => (el.roles.indexOf('ROLE_ORGANISER') > -1) &&
                                         (el.roles.indexOf('ROLE_ADMIN') === -1));
    orgs.map((el) => {
      el.name = `${el.fname} ${el.lname}`;
      return el;
    });

    return orgs;
  }
}
