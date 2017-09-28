import React from 'react';
import { render } from 'react-dom';
import ComponentA from './components/ComponentA';
import ComponentB from './components/ComponentB';

function App() {
  return (
    <div>
      <h1>HI WORLD</h1>
      <ComponentA />
      <ComponentB />
      <p>ssdsd</p>
    </div>
  );
}

render(<App />, document.getElementById('react-root'));
