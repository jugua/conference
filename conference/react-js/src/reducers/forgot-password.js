import actions from '../constants/actions-types';

function forgotPassword(isForgotPasswordSent = false, action) {
  switch (action.type) {
  case actions.SHOW_FORGOT_MESSAGE:
    return true;
  case actions.HIDE_FORGOT_MESSAGE:
    return false;
  default:
    return isForgotPasswordSent;
  }
}

export default forgotPassword;
