import actions from '../constants/actions-types';

const { EMAIL_IS_EMPTY, EMAIL_NOT_FOUND, HIDE_EMAIL_ERROR } = actions;

function forgotPasswordErrorMessage(initialstate, action) {
  const { type } = action;
  let statusEmail = HIDE_EMAIL_ERROR;

  if (type === EMAIL_IS_EMPTY) {
    statusEmail = EMAIL_IS_EMPTY;
  } else if (type === EMAIL_NOT_FOUND) {
    statusEmail = EMAIL_NOT_FOUND;
  }
  return statusEmail;
}

export default forgotPasswordErrorMessage;
