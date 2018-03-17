import ACTION_TYPES from '../constants/actions-types';

export default (upcomingConferences = [], { type, payload }) => (
  type === ACTION_TYPES.SET_UPCOMING_CONFERENCES
    ? payload
    : upcomingConferences
);
