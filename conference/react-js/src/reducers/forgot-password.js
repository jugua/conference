import actions from '../constants/actions-types';

const { SHOW_SUCCESS_RESET_PASSWORD_MESSAGE,
  HIDE_SUCCESS_RESET_PASSWORD_MESSAGE,
} = actions;

const forgotPassword = (isForgotPasswordSent = false, action) => {
  if (action.type === SHOW_SUCCESS_RESET_PASSWORD_MESSAGE) {
    return true;
  } if (action.type === HIDE_SUCCESS_RESET_PASSWORD_MESSAGE) {
    return false;
  }
  return isForgotPasswordSent;
};
export default forgotPassword;
