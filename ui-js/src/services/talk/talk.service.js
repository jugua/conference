export default class TalkService {

  constructor($resource) {
    'ngInject';

    this.talks = $resource('api/talk/:id/:action', {}, {
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
        methog: 'GET',
        params: { id: '@id' },
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
      progress: {
        method: 'POST',
        params: { id: '@id', action: 'progress' },
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
      approve: {
        method: 'POST',
        params: { id: '@id', action: 'approve' },
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
      reject: {
        method: 'POST',
        params: { id: '@id', action: 'reject' },
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      }
    });
  }

  clear() {
    this._talks = null;
  }

  getAll() {

    if (this._talks) {
      return this._talks;
    }

    this._talks = this.talks.getAll(
      () => {},
      () => {
        this._talks = null;
      });

    return this._talks;
  }

  add(talk) {   // talk object passed
    this.talks.add(talk,
      () => {
        if (this._talks instanceof Array) {
          this._talks.push(talk);
        }
      },
      (err) => {
        console.log(err);
      });
  }

  get(id) {
    return this.talks.get({ id },
      (res) => { console.dir(res); },
      (err) => { console.log(err); });
  }

  progress(id) {
    this.talks.progress({ id },
      (res) => { console.log(res); },
      (err) => { console.log(err); }
    );
  }

  approve(id) {
    this.talks.approve({ id },
      (res) => { console.log(res); },
      (err) => { console.log(err); }
    );
  }

  reject(id) {
    this.talks.reject({ id },
      (res) => { console.log(res); },
      (err) => { console.log(err); }
    );
  }

}

