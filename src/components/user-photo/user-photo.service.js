/* global FormData */
/* global angular */

class UserPhoto {
  constructor( $http, LocalStorage) {
    'ngInject';
    this.localStorage = LocalStorage;
    this.http = $http;
  }

  uploadPhoto(file) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post('api/users/current/photo', formData, {
      transformRequest: angular.identity,
      headers: {
        token: this.localStorage.getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache',
        'Content-Type': undefined
      }
    });
  }

  // deleting photo
  deleteUserPhoto() {
    return this.http.delete('api/users/current/photo', {
      headers: {
        token: this.localStorage.getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache',
      }
    });
  }
}

export default UserPhoto;

