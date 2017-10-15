import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import InputBlock from '../../components/InputBlock';
import editUser from '../../actions/edit-user';

class EmailChangeForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = { mail: '' };
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
    const { mail } = this.state;
    const { mail: oldEmail } = this.props;
    return (
      <form
        onSubmit={this.submit}
        onChange={this.change}
      >
        <InputBlock
          label="Old email"
          name="oldEmail"
          value={oldEmail}
          readonly
          onChange={() => false}
        />
        <InputBlock
          label="New email"
          name="mail"
          onChange={this.change}
          value={mail}
          required
        />
        <input
          type="submit"
          className="btn btn__inline"
          disabled={mail === oldEmail}
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

const mapStateToProps = ({ user: { mail } }) => ({ mail });

EmailChangeForm.propTypes = {
  mail: PropTypes.string.isRequired,
  edit: PropTypes.func.isRequired,
  cancel: PropTypes.func.isRequired,
};

export default connect(
  mapStateToProps,
  { edit: editUser },
)(EmailChangeForm);
