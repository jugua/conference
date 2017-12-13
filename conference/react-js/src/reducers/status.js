import actions from '../constants/actions-types';

const { LOAD_STATUS_LIST } = actions;

const status = (state = [], action) => {
  const { type, payload } = action;
  if (type === LOAD_STATUS_LIST) {
    return payload;
  }
  return state;
};

export default status;
