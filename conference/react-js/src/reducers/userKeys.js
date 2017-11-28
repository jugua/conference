import actionTypes from '../constants/actions-types';

const user = (state = [], { type, payload }) => (
  type === actionTypes.SET_USER_KEYS ? payload : state
);

export default user;
