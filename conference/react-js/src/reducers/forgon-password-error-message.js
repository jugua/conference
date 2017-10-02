import actions from '../constants/actions-types';

function forgotPasswordErrorMessage(isForgotPasswordSent = '', action) {
  switch (action.type) {
  case actions.EMAIL_IS_EMPTY:
    return actions.EMAIL_IS_EMPTY;
  case actions.EMAIL_NOT_FOUND:
    return actions.EMAIL_NOT_FOUND;
  case actions.HIDE_EMAIL_ERROR:
    return actions.HIDE_EMAIL_ERROR;
  default:
    return isForgotPasswordSent;
  }
}

export default forgotPasswordErrorMessage;
