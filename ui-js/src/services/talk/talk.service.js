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

  add(talk) {   // talk object passed

    this.talks.add(talk);
  }

  get(id) {
    return this.talks.get({ id });
  }

  approve(id, comment) {
    this.talks.update({ id }, { status: 'Approved', comment });
  }

  reject(id, comment) {
    this.talks.update({ id }, { status: 'Rejected', comment });
  }

  progress(id, comment) {
    this.talks.update({ id }, { status: 'In Progress', comment });
  }
}

