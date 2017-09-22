import React from 'react';
import {
  Link,
} from 'react-router-dom';


const BasicExample = ({ match }) => (
  <div>
    <ul>
      <li><Link to={`${match.url}/about`}>About</Link></li>
      <li><Link to={`${match.url}/topics`}>Topics</Link></li>
    </ul>
    <hr />
  </div>
);

export default BasicExample;
