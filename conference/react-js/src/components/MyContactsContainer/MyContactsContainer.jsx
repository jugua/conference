import React, { Component } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';
// import InputBlock from '../InputBlock/InputBlock';

class MyContactsContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      arr: [],
    };
  }
  componentDidMount() {
    this.getUserContacts();
  }
  getUserContacts = () => {
    const contactsURL = `/user/${this.props.user.id}/contacts`;
    axios.get(contactsURL)
      .then((data) => {
        this.setState({ arr: data.data });
        console.log(data.data);
      });
  };

  handleInput = ({ target: { value } }, i) => {
    const z = this.state.arr[i];
    console.log(z);
    console.log({ arr: [...this.state.arr, ...this.state.arr[i], { id: i, value }] });
  // const items = this.state.userContact;
  // items[item].value = e.target.value;
  // update state
  // this.setState({ userContact: items });
  // this.state(prevState => ({...prevState.userContact[i], value:value }))

  // this.setState((prevState) => {
  //   const current = prevState.userContact[i].value;
  //   return { [current]: value };
  // });

  // onChange={e => (this.handleInput(e, i))}
  };

  renderElement = arr => arr.map(({ id, value }) => (
    <div key={id} >
      <span>{id}</span>
      <input
        value={value}
        onChange={e => (this.handleInput(e, id))}
      />
    </div>))

  render() {
    return (
      <div>
        <h1>test</h1>
        {this.renderElement(this.state.userContacts)}
      </div>
    );
  }
}

MyContactsContainer.propTypes = {
  user: PropTypes.objectOf(PropTypes.number).isRequired,
};

export default MyContactsContainer;
