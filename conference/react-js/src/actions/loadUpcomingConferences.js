import axios from 'axios';
import ACTION_TYPES from '../constants/actions-types';

const setUpcomingConferences = conferences => ({
  type: ACTION_TYPES.SET_UPCOMING_CONFERENCES,
  payload: conferences,
});

export const loadUpcomingConferences =
  () => dispatch => axios.get('/conference/upcoming')
    .then(({ data }) => dispatch(setUpcomingConferences(data)));

export const clearUpcomingConferences =
  () => setUpcomingConferences([]);
