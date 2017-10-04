import React, { PureComponent } from 'react';
import DatePicker from 'react-datepicker';

import 'react-datepicker/dist/react-datepicker.css';

class Calendar extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      value: '',
    };
  }

  handleChange = (value) => {
    this.setState({ value });
  }

  render() {
    return (<DatePicker
      selected={this.state.value}
      onChange={this.handleChange}
      placeholderText="Select Date"
    />);
  }
}

export default Calendar;
