import actions from '../constants/actions-types';

const { APPLY_FILTERS, LOAD } = actions;

const talksInitial = [];

const talks = (state = talksInitial, action) => {
  if (action.type === LOAD) {
    return [...state, ...action.payload];
  }
  if (action.type === APPLY_FILTERS) {
    const { payload: { filter: { topic, status }, listOfTalks } } = action;
    const filtered = (elementOfListTopic) => {
      const countExtendTopic = elementOfListTopic.topic.indexOf(topic);
      const countExtendStatus = elementOfListTopic.status.indexOf(status);
      return countExtendStatus > -1 && countExtendTopic > -1;
    };
    return listOfTalks.filter(filtered);
  }

  return state;
};

export default talks;
