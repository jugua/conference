export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/api/conference');

    this.resUpcoming = $resource('/api/conference/upcoming', {}, {
      getAll: {
        method: 'GET',
        isArray: true,
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      }
    });

    this.resPast = $resource('/api/conference/past', {}, {
      getAll: {
        method: 'GET',
        isArray: true,
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      }
    });
  }

  getUpcoming() {
    return this.resUpcoming.getAll();
  }

  getPast() {
    return this.resPast.getAll();
  }

  save(confObj, successCallback) {
    this.res.save(confObj,
      (result) => { successCallback(result); });
  }
}