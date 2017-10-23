import actions from '../constants/actions-types';

const { APPLY_FILTERS, LOAD } = actions;

const talksInitial = [];

const talks = (state = talksInitial, { type, payload }) => {
  if (type === LOAD) {
    return payload;
  }
  if (type === APPLY_FILTERS) {
    const { filter: { topic, status }, listOfTalks } = payload;
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
