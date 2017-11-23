import axios from 'axios';

import actionTypes from '../constants/actions-types';
import { myInfo } from '../constants/backend-url';

const setUserAction = ({ data: user }) => ({
  type: actionTypes.SET_USER,
  payload: user,
});

const getUserInfo = dispatch => (
  axios.get(myInfo)
    .then(res => dispatch(setUserAction(res)))
    .catch(err => console.log(err))
);

export default getUserInfo;
