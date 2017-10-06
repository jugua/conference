import actions from '../constants/actions-types';

const { SHOW_SUCCESS_RESET_PASSWORD_MESSAGE,
  HIDE_SUCCESS_RESET_PASSWORD_MESSAGE,
} = actions;
const results={
  [SHOW_SUCCESS_RESET_PASSWORD_MESSAGE]: true,
  [HIDE_SUCCESS_RESET_PASSWORD_MESSAGE]: false,
};

const forgotPassword = (isPasswordForgottenSent = false, action) => (
    results.hasOwnProperty(action.type) ?
      results[action.type] : isPasswordForgottenSent
);
export default forgotPassword;
