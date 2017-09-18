import React from 'react';
import { render } from 'react-dom';
import './index.sass';

import ComponentA from './ComponentA';
import ComponentB from './ComponentB'



class App extends React.Component {
	constructor(props) {
		super(props);
	}

	render() {
		return (
			<div>
				<h1>HI WORLD</h1>
				<ComponentA />
				<ComponentB />
			</div>
		);
	}
}

render(<App />, document.getElementById('app'));
