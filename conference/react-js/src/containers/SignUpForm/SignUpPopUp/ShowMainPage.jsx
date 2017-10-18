import React from 'react';
import { withRouter } from 'react-router-dom';

const ShowMainPage = withRouter(({ history }) => (
  <button
    onClick={() => { history.push('/react'); }}
    className="btn pop-up__button"
  >Ok
  </button>
));

export default ShowMainPage;
