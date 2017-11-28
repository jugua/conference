import { createStore, applyMiddleware, compose } from 'redux';
import thunk from 'redux-thunk';

import rootReducer from '../reducers';
/* eslint-disable no-underscore-dangle */
const configureStore = () => {
  const composeEnhancers = (
    window.__REDUX_DEVTOOLS_EXTENSION_COMPOSE__ || compose
  );

  return createStore(
    rootReducer,
    composeEnhancers(applyMiddleware(thunk)),
  );
};
/* eslint-enable */
export default configureStore;
