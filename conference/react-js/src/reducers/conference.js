import actions from '../constants/actions-types';

const { SET_CONFERENCE } = actions;

const conference = (dataConference = {}, action) => {
  const { type, payload } = action;
  if (type === SET_CONFERENCE) {
    return payload;
  }
  return dataConference;
};

export default conference;
