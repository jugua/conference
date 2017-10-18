import actionTypes from '../constants/actions-types';

const editUser = (update) => {
  const { EDIT_USER } = actionTypes;

  return {
    type: EDIT_USER,
    payload: update,
  };
};

export default editUser;
