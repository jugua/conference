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
      const countName = elementOfListTopic.name.indexOf(name);
      const countTitle = elementOfListTopic.title.indexOf(title);
      const countTopic = elementOfListTopic.topic.indexOf(topic);
      const countStatus = elementOfListTopic.status.indexOf(status);
      return countName > -1 && countTitle > -1 && countStatus > -1 &&
      countTopic > -1;
    };
    return listOfTalks.filter(filtered).slice(prevValue, lastValue);
  } else if (type === SORT_ALL_TALKS) {
    const { direction, talks: talksList, field } = payload;
    return talksList.sort(propComparator(field, direction));
  }
  return state;
};

export default talks;
