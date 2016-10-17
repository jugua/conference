const Users = function Users($resource, $window) {
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

  return $resource('/api/users/current', {}, {
    getCurrentUser: {
      method: 'GET',
      headers: { token: getToken }
    }
  });
};
export default Users;