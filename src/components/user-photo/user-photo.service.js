/* global FormData */
/* global angular */

class UserPhoto {
  constructor($window, $http) {
    'ngInject';

    this.window = $window;
    this.http = $http;
  }

  getToken() {
    let info = this.window.localStorage.userInfo;
    let token;

    if (info) {
      info = JSON.parse(info);
      token = info.token;
    } else {
      token = '';
    }

    return token;
  }

  uploadPhoto(file) {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post('api/users/current/photo', formData, {
      transformRequest: angular.identity,
      headers: {
        token: this.getToken.bind(this),
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
        token: this.getToken.bind(this),
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache',
      }
    });
  }
}

export default UserPhoto;

