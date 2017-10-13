import actions from '../constants/actions-types';

const { EMAIL_IS_EMPTY,
  EMAIL_NOT_FOUND, HIDE_EMAIL_ERROR } = actions;

const resultsMap = { [EMAIL_IS_EMPTY]: EMAIL_IS_EMPTY,
  [EMAIL_NOT_FOUND]: EMAIL_NOT_FOUND,
  [HIDE_EMAIL_ERROR]: HIDE_EMAIL_ERROR,
};

const forgotPasswordErrorMessage = (
  isForgotPasswordSent = HIDE_EMAIL_ERROR, action) => (
  resultsMap[action.type] || isForgotPasswordSent);

export default forgotPasswordErrorMessage;
