export default class {

  constructor($resource, $log) {
    'ngInject';

    this.log = $log;

    this.res = $resource('api/talk/:id/file', {}, {
      get: {
        method: 'GET',
        params: { id: '@id' },    // talk id
      },
      save: {
        method: 'POST',
        params: { id: '@id' },
        transformRequest: angular.identity,   // multipart
        headers: {
          'Content-Type': undefined,          // multipart
        }
      },
      delete: {
        method: 'DELETE',
      }
    });
  }

  get(talk_id) {   // talk id passed
    return this.res.get({ id });
  }

  save(talk, successCallback) {   // talk object passed
      this.res.add(talk,
        (res) => { successCallback(res); },
        (err) => { this.log.error(err); }
      );
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
