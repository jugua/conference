import actions from '../constants/actions-types';

const { SHOW_SUCCESS_RESET_PASSWORD_MESSAGE,
  HIDE_SUCCESS_RESET_PASSWORD_MESSAGE } = actions;

function forgotPassword(isForgotPasswordSent, action) {
  const { type } = action;
  let res = false;
  if (type === SHOW_SUCCESS_RESET_PASSWORD_MESSAGE) {
    res = true;
  } else if (type === HIDE_SUCCESS_RESET_PASSWORD_MESSAGE) {
    res = false;
  }
  return res;
}

export default forgotPassword;
