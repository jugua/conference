export default class ManageUsersService {

  constructor($resource, $log) {
    'ngInject';

    this.log = $log;

    this.users = $resource('api/user/admin', {}, {
      getAll: {
        method: 'GET',
        isArray: true,
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
  }

  getAll() {
    return this.users.getAll();
  }

  add(talk, successCallback) {   // talk object passed
    this.talks.add(talk,
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }

  get(id) {
    return this.talks.get({ id });
  }

  update(id, talk, successCallback) {   // talk object passed
    this.talks.update({ id }, talk,
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }

  approve(id, comment, successCallback) {
    this.talks.update({ id }, { status: this.TALK_STATUS_APPROVED, comment },
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }

  reject(id, comment, successCallback) {
    this.talks.update({ id }, { status: this.TALK_STATUS_REJECTED, comment },
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }

  progress(id, comment, successCallback) {
    this.talks.update({ id }, { status: this.TALK_STATUS_PROGRESS, comment },
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }
}
