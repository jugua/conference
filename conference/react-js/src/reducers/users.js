import actions from '../constants/actions-types';

const { LOAD_USER_DATA } = actions;

const users = (allUser = [], action) => {
  const { type, payload } = action;
  if (type === LOAD_USER_DATA) {
    return [
      ...allUser,
      ...payload,
    ];
  }
  return allUser;
};

export default users;
