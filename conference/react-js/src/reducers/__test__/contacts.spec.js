import conference from '../conference';
import actions from '../../constants/actions-types';

const { SET_CONTACTS } = actions;

describe('REDUCER conference', () => {
  const initialState = [];
  it('SET_CONFERENCE', () => {
    const action = {
      type: SET_CONTACTS,
      payload: {
        id: 1,
        title: 'some',
      },
    };
    const state = conference(initialState, action);
    expect(state).toMatchSnapshot();
  });
  it('initialState', () => {
    const state = conference(
      initialState,
      { type: undefined, payload: undefined },
    );
    expect(state).toMatchSnapshot();
  });
});
