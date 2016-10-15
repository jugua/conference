const Users = function Users($resource) {

  let token = 12345;

  return  $resource('/api/users/current',{} , {

    getCurrentUser: {
      method: 'GET',
      headers:{token}
    }
  });
};

export default Users;
