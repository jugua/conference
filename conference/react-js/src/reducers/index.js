import { combineReducers } from 'redux';
import user from './user';
import users from './users';
import userTalks from './userTalks';
import forgotPassword from './forgot-password';
import forgotPasswordErrorMessage from './forgot-password-error-message';
import talks from './talks';
import contacts from './contacts';

const rootReducers = combineReducers({
  user,
  forgotPassword,
  forgotPasswordErrorMessage,
  users,
  userTalks,
  talks,
  contacts,
});

export default rootReducers;
