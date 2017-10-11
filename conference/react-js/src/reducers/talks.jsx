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
