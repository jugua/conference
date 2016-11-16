export default class TalkService {

  constructor($resource, LocalStorage) {
    'ngInject';

    this.talks = $resource('api/talk', {}, {
      add: {
        method: 'POST',
        headers: {
          token: LocalStorage.getToken,
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
      getAll: {
        method: 'GET',
        isArray: true,
        headers: {
          token: LocalStorage.getToken,
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

    this._talks = this.talks.getAll(() => {
    },
      () => {
        this._talks = [];
      });

    return this._talks;
  }

  add(talk) {
    this.talks.add(talk, (res) => {
      if (this._talks instanceof Array) {
        this._talks.push(res);
      }
    },
      (err) => {
        console.log(err);
      });
  }

}

