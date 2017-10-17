import actionTypes from '../constants/actions-types';

const { SET_TALKS } = actionTypes;

const userTalks = (state = [], { type, payload }) => (
  type === SET_TALKS ? payload : state
);

export default userTalks;
