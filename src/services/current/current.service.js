function Current($resource, $window, $q, $rootScope, $http) {
  function getToken() {
    let info = $window.localStorage.userInfo;
    let token;

    if (info) {
      info = JSON.parse(info);
      token = info.token;
    } else {
      token = '';
    }

    return token;
  }

  const users = $resource('/api/users/current', {}, {
    getCurrentUser: {
      method: 'GET',
      headers: {
        token: getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    },
    updateCurrentUser: {
      method: 'POST',
      headers: {
        token: getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache'
      }
    }
  });

  function getInfo() {
    const current = $q.defer();
    users.getCurrentUser({}, (data) => {
      current.resolve(data);
    },
    () => {
      current.resolve(null);
    });

    this.current = current.promise;
  }

  function updateInfo(userInfo) {
    users.updateCurrentUser(userInfo, () => {
      //this.getInfo();
    },
    () => {
      $rootScope.$broadcast('signInEvent');
    });
  }

  function getPhotoStatus() {
    return this.current.then((result)=>{
      if (result.photo) {
        return { button: 'Update Photo', title: 'Update Your Photo' };
      }

      return { button: 'Upload Photo', title: 'Upload new photo' };
    });
  }

  function uploadPhoto(file) {
    let formData = new FormData();
    formData.append('file', file);
    return $http.post('api/upload-image', formData, {
      transformRequest: angular.identity,
      headers: {
        token: getToken,
        'Cache-Control': 'no-cache, no-store',
        Pragma: 'no-cache',
        'Content-Type': undefined
      }
    })
  }

  return {
    getInfo,
    updateInfo,
    uploadPhoto,
    getPhotoStatus
  };
}

export default Current;