import actions from '../constants/actions-types';

const { SHOW_SUCCESS_RESET_PASSWORD_MESSAGE,
  HIDE_SUCCESS_RESET_PASSWORD_MESSAGE } = actions;

const resultsMap = {
  [SHOW_SUCCESS_RESET_PASSWORD_MESSAGE]: true,
  [HIDE_SUCCESS_RESET_PASSWORD_MESSAGE]: false,
};

const forgotPassword = (isForgotPasswordSent = false, action) => (
  resultsMap[action.type] || isForgotPasswordSent);

export default forgotPassword;
