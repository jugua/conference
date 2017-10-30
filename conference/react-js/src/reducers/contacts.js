import actionTypes from '../constants/actions-types';

const contacts = (state = [], { type, payload }) => {
  const { SET_CONTACTS } = actionTypes;

  if (type === SET_CONTACTS) {
    return payload;
  }
  return state;
};

export default contacts;
