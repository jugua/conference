import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import InputBlock from '../../components/InputBlock';
import editUser from '../../actions/edit-user';

class NameChangeForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      fname: '',
      lname: '',
    };
  }

  componentDidMount() {
    this.setDefaultValues(this.props);
  }

  // componentWillReceiveProps(nextProps) {
  //   this.setDefaultValues(nextProps);
  // }

  setDefaultValues = ({ fname, lname }) => {
    this.setState({
      fname,
      lname,
    });
  }

  change = ({ target: { name, value } }) => {
    this.setState({
      [name]: value,
    });
  }

  submit = (e) => {
    e.preventDefault();
    const { edit, cancel } = this.props;
    edit(this.state);
    cancel();
  }

  cancel = (e) => {
    e.preventDefault();
    this.props.cancel();
  }

  render() {
    const { fname, lname } = this.state;
    return (
      <form
        onSubmit={this.submit}
        onChange={this.change}
        className="settings__row-content"
      >
        <InputBlock
          value={fname}
          label="First name"
          name="fname"
        />
        <InputBlock
          value={lname}
          label="Last name"
          name="lname"
        />
        <input
          type="submit"
          className="btn btn__inline"
        />
        <input
          type="button"
          className="btn btn__inline"
          value="Cancel"
          onClick={this.cancel}
        />
      </form>
    );
  }
}

const mapStateToProps = ({ user: { fname, lname } }) => ({ fname, lname });

NameChangeForm.propTypes = {
  cancel: PropTypes.func.isRequired,
  edit: PropTypes.func.isRequired,
};

export default connect(
  mapStateToProps,
  { edit: editUser },
)(NameChangeForm);
