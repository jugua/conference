import actions from '../constants/actions-types';
import propComparator from '../utils/sortData';

const { APPLY_FILTERS, LOAD, SORT_ALL_TALKS } = actions;

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
  } else if (type === SORT_ALL_TALKS) {
    const { direction, talks: talksList, field } = payload;
    return talksList.sort(propComparator(field, direction));
  }

  return state;
};

export default talks;
