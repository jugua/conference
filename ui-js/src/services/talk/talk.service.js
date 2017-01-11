export default class TalkService {

  constructor($resource) {
    'ngInject';

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
        methog: 'GET',
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
    return this.talks.get({ id });
  }

  approve(id) {
    this.talks.update({ id }, { status: 'approved' });
  }

  reject(id, comment) {
    this.talks.update({ id }, { status: 'rejected', comment });
  }

  progress(id, comment) {
    this.talks.update({ id }, { status: 'progress', comment });
  }
}

