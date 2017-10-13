import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

class CustomForm extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {};
  }

  componentWillMount() {
    this.setDefaultValues();
  }

  onChange = ({ target }) => {
    this.setState({
      [target.name]: target.value,
    });
  };

  onCancel = () => {
    this.setDefaultValues();
    this.props.cancelAction();
  };

  onSubmit = (e) => {
    const { saveAction, cancelAction } = this.props;
    e.preventDefault();
    saveAction(this.state);
    cancelAction();
  };

  setDefaultValues = () => {
    // const { inputs } = this.props;
    // inputs.forEach(({ name, value }) => {
    //   this.setState({
    //     [name]: value,
    //   });
    // });
  };

  render() {
    return (
      <form
        onSubmit={this.onSubmit}
        onChange={this.onChange}
        className="settings__row-content"
      >
        { this.props.children }
        <input
          type="submit"
          className="btn btn__inline"
          value="Save"
        />

        <input
          type="button"
          className="btn btn__inline"
          value="Cancel"
          onClick={this.onCancel}
        />
      </form>
    );
  }
}

CustomForm.propTypes = {
  cancelAction: PropTypes.func.isRequired,
  saveAction: PropTypes.func.isRequired,
  children: PropTypes.oneOfType([
    PropTypes.arrayOf(PropTypes.node),
    PropTypes.node,
  ]).isRequired,
};

export default CustomForm;
