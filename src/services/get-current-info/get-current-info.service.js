function CurrentUserInfo(Users, $q) {
  const current = $q.defer();

  Users.getCurrentUser({},  (data) => {
      current.resolve(data);
    },
    () => {
      current.resolve(null);
    });

  return current;
}
export default CurrentUserInfo;