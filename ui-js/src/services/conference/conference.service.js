export default class {
  constructor($resource) {
    'ngInject';

    this.res = $resource('/conference');

    this.resUpcoming = $resource('/conference/upcoming', {}, {
      getAll: {
        method: 'GET',
        isArray: true,
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      }
    });

    this.resPast = $resource('/conference/past', {}, {
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