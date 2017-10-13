import actions from '../constants/actions-types';

const { APPLY_FILTERS } = actions;

const talksInitial = [];

const talks = (state = talksInitial, action) => {
  if (action.type === 'load') {
    return [...state, ...action.payload];
  }
  if (action.type === APPLY_FILTERS) {
    const { payload: { filter: { topic, status }, listOfTalks } } = action;
    const filtered = (elementOfListTopic) => {
      const countExtendTopic = elementOfListTopic.topic.indexOf(topic);
      const countExtendStatus = elementOfListTopic.status.indexOf(status);
      return countExtendStatus > -1 && countExtendTopic > -1;
    };
    const res = listOfTalks.filter(filtered);
    return [...res];
  }

  return state;
};

export default talks;
