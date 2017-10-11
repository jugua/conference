// import Talks from '../components/Talks/talksListData';
// import TalksObj from '../containers/Talks/GetTalks';
// const talks = (state = TalksObj) => state;

const talksInitial = [];
//   filter: {
//     status: '',
//     topic: '',
//   },
//   data: [],
// };

const talks = (state = talksInitial, action) => {
  if (action.type === 'load') {
    return [...state, ...action.payload];
  }
  if (action.type === 'filter') {
    console.log(action, 'reducer');
    const { payload: { filter, list } } = action;
    const filtered = (data) => {
      console.log(data);
      return data.status === filter;
    };
    console.log(typeof list, 'type of');
    const res = list.filter(filtered);
    console.log(res);
    return [...res];
  }

  return state;
};

export default talks;

// switch (action.type) {
//  case 'load':
//  const { payload: {filter , data }} = action;
//  const filtered = (data, 'topic') => (
//
//  )
//    return [...state, ...action.payload]
//  case 'APPLY_FILTERS':
//    return Object.assign({}, action.filter);
//  default:
//    return state
