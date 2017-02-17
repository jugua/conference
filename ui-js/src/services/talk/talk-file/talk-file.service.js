export default class {

  constructor($resource, $log) {
    'ngInject';

    this.log = $log;

    this.res = $resource('api/talk/:id/file', {}, {
      get: {
        method: 'GET',
        params: { id: '@id' },    // talk id identifying the file
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

  get(talkId) {   // talk id
    return this.res.get({ talkId });
  }

  save(talkId, formData, successCallback) {   // talk id, multipart form data with file
    this.res.save(talkId, multipartFormData,
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }

  delete(talkId) {
    this.res.delete(talkId, successCallback,
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }

}
