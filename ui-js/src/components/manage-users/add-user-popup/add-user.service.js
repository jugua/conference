class AddUserService {
  constructor($resource) {
    'ngInject';

    this.resource = $resource('/api/user/create', {}, {
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