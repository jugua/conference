import actionTypes from '../constants/actions-types';

const { SET_CONTACTS } = actionTypes;
const contacts = (state = [], { type, payload }) => {
  if (type === SET_CONTACTS) {
    return payload;
  }
  return state;
};

export default contacts;
