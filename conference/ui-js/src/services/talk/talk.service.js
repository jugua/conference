export default class TalkService {

  constructor($resource, $log) {
    'ngInject';

    this.log = $log;

    this.talks = $resource('/talk/:id', {}, {
      add: {
        url: '/submitTalk',
        method: 'POST',
        transformRequest: angular.identity,
        headers: {
          'Content-Type': undefined,
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
      getAll: {
        url: '/talk',
        method: 'GET',
        isArray: true,
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
      get: {
        url: '/talk/:id',
        method: 'GET',
        params: { id: '@id' },
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
      update: {
        url: '/talk/:id',
        method: 'PATCH',
        params: { id: '@id' }
      }
    });

    // constant status strings
    this.TALK_STATUS_NEW = 'New';
    this.TALK_STATUS_APPROVED = 'Approved';
    this.TALK_STATUS_REJECTED = 'Rejected';
    this.TALK_STATUS_PROGRESS = 'In Progress';
  }

  getAll() {
    return this.talks.getAll();
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

  get statusStrings() {   // return an array of constant status strings
    return [
      this.TALK_STATUS_NEW,
      this.TALK_STATUS_PROGRESS,
      this.TALK_STATUS_APPROVED,
      this.TALK_STATUS_REJECTED,
    ];
  }
}
