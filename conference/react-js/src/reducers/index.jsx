import { combineReducers } from 'redux';
import user from './test-reducers';
import filter from './filter';
import talks from './talks';

const rootReducers = combineReducers({
  user, filter, talks,
});

export default rootReducers;
