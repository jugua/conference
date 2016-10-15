const Users = function Users($resource) {
  const token = 12345;

  return $resource('/api/users/current', {}, {

    getCurrentUser: {
      method: 'GET',
      headers: { token }
    }
  });
};

export default Users;
