import React, { Component } from 'react';
import axios from 'axios';
import PropTypes from 'prop-types';
import InputBlock from '../InputBlock/InputBlock';

class MyContactsContainer extends Component {
  constructor(props) {
    super(props);
    this.state = {
      contacts: [],
    };
  }
  componentDidMount() {
    this.getUserContacts();
  }
  getUserContacts = () => {
    const contactsURL = `/user/${this.props.user.id}/contacts`;
    axios.get(contactsURL)
      .then((data) => {
        this.setState({ contacts: data.data });
      });
  };

  // handleInput = ({ target: { value } }, i) => {
  //   const z = this.state.arr[i];
  // const items = this.state.arr;
  // items[item].value = e.target.value;
  //   console.log(z);
  //   console.log({ arr: [...this.state.arr, ...this.state.arr[i], { id: i, value }] });
  // // const items = this.state.userContact;
  // // items[item].value = e.target.value;
  // // update state
  // // this.setState({ userContact: items });
  // // this.state(prevState => ({...prevState.userContact[i], value:value }))
  //
  // // this.setState((prevState) => {
  // //   const current = prevState.userContact[i].value;
  // //   return { [current]: value };
  // // });
  //
  // // onChange={e => (this.handleInput(e, i))}
  // };

  handleInput = (e, i) => {
    const items = this.state.contacts;
    items[i - 1].value = e.target.value;
    this.setState({
      contacts: [...this.state.contacts, ...this.state.contacts[i - 1], { value: e.target.value }],
    });
  };

  renderElement = contacts => contacts.map(({ id, value, contactType: { name } }) => (
    <InputBlock
      key={id}
      id={name}
      htmlFor={name}
      label={name}
      labelClass="form-label"
      inputClass="sign-up__field"
      value={value}
      name="value"
      onChange={(e) => { this.handleInput(e, id); }}
    />
  ));

  render() {
    return (
      <div>
        <form
          className="sign-up"
        >
          {this.renderElement(this.state.contacts)}
        </form>
      </div>
    );
  }
}

MyContactsContainer.propTypes = {
  user: PropTypes.objectOf(PropTypes.string).isRequired,
};

export default MyContactsContainer;
