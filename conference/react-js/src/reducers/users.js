import actions from '../constants/actions-types';

const { LOAD_USER_DATA, SORT_USER_DATA, ASC } = actions;

const users = (allUser = [], action) => {
  const { type, payload } = action;
  if (type === LOAD_USER_DATA) {
    return [
      ...payload,
    ];
  } else if (type === SORT_USER_DATA) {
    const { direction, users: userList, field } = payload;
    const sortDirection = (a, b) => {
      if (direction === ASC) {
        return a[field] > b[field];
      }
      return b[field] < a[field];
    };

    return [...userList.sort(sortDirection)];
  }
  return allUser;
};

export default users;
