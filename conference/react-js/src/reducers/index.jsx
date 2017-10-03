import { combineReducers } from 'redux';
import user from './test-reducers';

const rootReducers = combineReducers({
  user,
});

export default rootReducers;
