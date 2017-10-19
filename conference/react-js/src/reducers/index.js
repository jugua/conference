import { combineReducers } from 'redux';
import user from './user';
import users from './users';
import forgotPassword from './forgot-password';
import forgotPasswordErrorMessage from './forgot-password-error-message';

const rootReducers = combineReducers({
  user,
  forgotPassword,
  forgotPasswordErrorMessage,
  users,
});

export default rootReducers;
