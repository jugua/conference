import actionTypes from '../constants/actions-types';

const defaultUser = {
  id: -1,
  roles: [],
  fname: '',
};

const user = (state = defaultUser, { type, payload }) => (
  type === actionTypes.SET_USER ? payload : state
);

export default user;
