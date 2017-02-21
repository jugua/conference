export default class {

  constructor($resource, $log) {
    'ngInject';

    this.log = $log;

    this.res = $resource('api/talk/:talkId/file', {}, {
      get: {
        method: 'GET',
      },
      save: {
        method: 'POST',
        params: { talkId: '@talkId' },
        transformRequest: angular.identity,   // multipart
        headers: {
          'Content-Type': undefined,          // multipart
        }
      },
      delete: {
        method: 'DELETE',
        params: { talkId: '@talkId' },
      }
    });

    this.resName = $resource('api/talk/:talkId/filename');
  }

  get(talkId) {   // talk id
    return this.res.get({ talkId });
  }

  save(talkId, formData, successCallback) {   // talk id, multipart form data with file
    this.res.save({ talkId }, formData,
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }

  delete(talkId, successCallback) {
    this.res.delete({ talkId }, successCallback,
      (res) => { successCallback(res); },
      (err) => { this.log.error(err); }
    );
  }

  getName(talkId) {   // talk id
    return this.resName.get({ talkId });
  }
}
