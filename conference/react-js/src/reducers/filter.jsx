const initialState = {};

function filter(state = initialState, action) {
  switch (action.type) {
  case 'APPLY_FILTERS': {
    return Object.assign({}, action.filter);
  }
  default: {
    return state;
  }
  }
}

export default filter;
