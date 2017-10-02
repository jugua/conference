import { combineReducers } from 'redux';
import user from './test-reducers';
import forgotPassword from './forgot-password';
import forgotPasswordErrorMessage from './forgon-password-error-message';

const rootReducers = combineReducers({
  user,
  forgotPassword,
  forgotPasswordErrorMessage,
});

export default rootReducers;
