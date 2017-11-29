import React, { Component } from 'react';
import axios from 'axios';
import Dialog from 'material-ui/Dialog';
import RaisedButton from 'material-ui/RaisedButton';
import MenuItem from 'material-ui/MenuItem';
import SelectField from 'material-ui/SelectField';
import TextField from 'material-ui/TextField';
import { conferencesNames, organizerInvite } from '../../constants/backend-url';
import SuccessMessage from './SuccessMessage';

class AddNewOrganizer extends Component {
  constructor(prop) {
    super(prop);
    this.state = {
      open: false,
      conferenceName: '',
      userEmail: '',
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

  handleConference = (event, index, conferenceName) => (
    this.setState({ conferenceName }));

  handleEmail = (event, userEmail) => this.setState({ userEmail });

  toggleInput = () => {
    this.setState({ open: !this.state.open });
    return <SuccessMessage />;
  }

  sendInvite = () => {
    const { userEmail, conferenceName } = this.state;
    axios.post(`${organizerInvite}?email=${userEmail}&name=${conferenceName}`,
      { email: userEmail,
        name: conferenceName })
      .then(() => {
        this.toggleInput();
      }).catch((
        { response: { data: { error } } }) => {
        console.log(error);
      });
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
        onClick={this.sendInvite}
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
            value={this.state.userEmail}
            onChange={this.handleEmail}
          /><br />
          <SelectField
            floatingLabelText="Choose Conference"
            value={this.state.conferenceName}
            onChange={this.handleConference}
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