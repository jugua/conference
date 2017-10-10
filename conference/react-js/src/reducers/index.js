import { combineReducers } from 'redux';
import user from './user';
import forgotPassword from './forgot-password';
import forgotPasswordErrorMessage from './forgot-password-error-message';

const rootReducers = combineReducers({
  user,
  forgotPassword,
  forgotPasswordErrorMessage,
});

export default rootReducers;
