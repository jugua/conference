export default class TalkService {

  constructor($resource, $log) {
    'ngInject';

    this.log = $log;

    this.talks = $resource('api/talk/:id', {}, {
      add: {
        method: 'POST',
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
      getAll: {
        method: 'GET',
        isArray: true,
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
      get: {
        method: 'GET',
        params: { id: '@id' },
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
      update: {
        method: 'PATCH',
        params: { id: '@id' }
      }
    });
  }

  getAll() {
    return this.talks.getAll();
  }

  add(talk, successCallback) {   // talk object passeds
    this.talks.add(talk,
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }

  get(id) {
    return this.talks.get({ id });
  }

  approve(id, comment, successCallback) {
    this.talks.update({ id }, { status: 'Approved', comment },
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }

  reject(id, comment, successCallback) {
    this.talks.update({ id }, { status: 'Rejected', comment },
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }

  progress(id, comment, successCallback) {
    this.talks.update({ id }, { status: 'In Progress', comment },
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }
}

