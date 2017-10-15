import React from 'react';
import ReactDOM from 'react-dom';
import { Provider } from 'react-redux';
import App from './components/App';
import configureStore from './store/configureStore';

const store = configureStore();

store.dispatch({
  type: 'set_user',
  payload: {
    fname: 'Alex',
    lname: 'Black',
    mail: 'alex.black@alex.com',
    id: 1,
    roles: [],
  },
});

ReactDOM.render(
  <Provider store={store}>
    <App />
  </Provider>,
  document.getElementById('react-root'),
);
