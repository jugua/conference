import React, { Component } from 'react';
import axios from 'axios';
import Dialog from 'material-ui/Dialog';
import RaisedButton from 'material-ui/RaisedButton';
import MenuItem from 'material-ui/MenuItem';
import SelectField from 'material-ui/SelectField';
import TextField from 'material-ui/TextField';
import { conferencesNames } from '../../constants/backend-url';

class AddNewOrganizer extends Component {
  constructor(prop) {
    super(prop);
    this.state = {
      open: false,
      value: false,
      conferenceList: [],
    };
  }

  componentDidMount() {
    axios.get(conferencesNames)
      .then(({ data }) => {
        this.setState({ conferenceList: data });
      });
  }

  setMenuItems = () => (
    this.state.conferenceList.map(name => (
      <MenuItem
        value={name}
        primaryText={name}
        key={name}
      />),
    )
  )

  handleChange = (event, index, value) => this.setState({ value });

  toggleInput = () => {
    this.setState({ open: !this.state.open });
  }

  render() {
    const actions = [
      <RaisedButton
        className="update-talk__button"
        label="Cancel"
        keyboardFocused
        primary
        onClick={this.toggleInput}
      />,
      <RaisedButton
        className="update-talk__button"
        label="Submit"
        keyboardFocused
        primary
        onClick={this.toggleInput}
      />,
    ];

    return (
      <div>
        <RaisedButton
          label="Invite Organizer"
          primary
          onClick={this.toggleInput}
        />
        <Dialog
          title="Invite Organizer"
          actions={actions}
          modal={false}
          open={this.state.open}
          onRequestClose={this.handleClose}
        >
          <TextField
            id="text-field-default"
            hintText="Enter email adress"
            type="email"
          /><br />
          <SelectField
            floatingLabelText="Choose Conference"
            value={this.state.value}
            onChange={this.handleChange}
          >
            <MenuItem value={null} primaryText="" />
            {this.setMenuItems()}
          </SelectField>
        </Dialog>
      </div>
    );
  }
}

export default AddNewOrganizer;
