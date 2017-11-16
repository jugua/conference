import React, { Component } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';

class MyContacts extends Component {
  componentDidMount() {
    axios.get('/user/1/contacts')
      .then((data) => {
        console.log(data);
      });
  }

  render() {
    const { id } = this.props.user;
    return (
      <div>
        <h1>Contacts id: {id}</h1>
      </div>
    );
  }
}

MyContacts.propTypes = {
  user: PropTypes.objectOf(PropTypes.string).isRequired,
  id: PropTypes.number.isRequired,
};

export default MyContacts;
