import ACTIONS_TYPES from '../constants/actions-types';

export default (forgotPasswordState = {}, { type, payload }) => {
  switch (type) {
  case ACTIONS_TYPES.SET_FORGOT_PASSWORD_VISIBILITY:
    return { ...forgotPasswordState, ...{ visibility: payload } };
  case ACTIONS_TYPES.SET_FORGOT_PASSWORD_MESSAGE:
    return { ...forgotPasswordState, ...{ message: payload } };
  case ACTIONS_TYPES.SET_FORGOT_PASSWORD_ERROR:
    return { ...forgotPasswordState, ...{ error: payload } };
  default:
    return forgotPasswordState;
  }
};
