export default class {

  constructor($resource, $log) {
    'ngInject';

    this.log = $log;


    this.res = $resource('talk/:talkId/takeFile', {}, {
      get: {
        url: '/talk/:talkId/takeFile',
        method: 'GET',
      },
      save: {
        url: '/talk/:talkId/uploadFile',
        method: 'POST',
        params: { talkId: '@talkId' },
        transformRequest: angular.identity,   // multipart
        headers: {
          'Content-Type': undefined,          // multipart
        }
      },
      delete: {
        url: '/talk/:talkId/deleteFile',
        method: 'DELETE',
        params: { talkId: '@talkId' },
      }
    });

    this.resName = $resource('talk/:talkId/takeFileName');

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
