class AddUserService {
  constructor($resource) {
    'ngInject';

    this.resource = $resource('/user/registerByAdmin', {}, {
      add: {
        method: 'POST',
        headers: {
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      }
    });
  }

  addUser(user, successCallback, errorCallback) {
    // POST
    this.resource.save(user,
      (result) => {
        successCallback(result);
      },
      (error) => {
        errorCallback(error);
      }
    );
  }
}

export default AddUserService;