import actions from '../constants/actions-types';
import propComparator from '../utils/sortData';

const { APPLY_FILTERS, LOAD, SORT_ALL_TALKS } = actions;

const talks = (state = [], action) => {
  const { payload, type } = action;
  if (type === LOAD) {
    return payload;
  } else if (type === APPLY_FILTERS) {
    const {
      filter: { name, title, topic, status },
      talks: listOfTalks,
      quantity,
      page,
    } = payload;
    const prevValue = (page - 1) * quantity;
    const lastValue = page * quantity;
    const filtered = (elementOfListTopic) => {
      const countName = elementOfListTopic.name.toLowerCase()
        .includes(name.toLowerCase());
      const countTitle = elementOfListTopic.title.toLowerCase()
        .includes(title.toLowerCase());
      const countTopic = elementOfListTopic.topic.toLowerCase()
        .includes(topic.toLowerCase());
      const countStatus = elementOfListTopic.status.toLowerCase()
        .includes(status.toLowerCase());
      return countName && countTitle && countStatus && countTopic;
    };
    return listOfTalks.filter(filtered).slice(prevValue, lastValue);
  } else if (type === SORT_ALL_TALKS) {
    const { direction, talks: talksList, field } = payload;
    return talksList.sort(propComparator(field, direction));
  }
  return state;
};

export default talks;
