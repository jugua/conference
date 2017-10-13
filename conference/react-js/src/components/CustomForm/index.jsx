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
    const { inputs } = this.props;
    inputs.forEach(({ name, value }) => {
      this.setState({
        [name]: value,
      });
    });
  };

  render() {
    return (
      <form
        onSubmit={this.onSubmit}
        className="settings__row-content"
      >

        { this.props.inputs.map(({
          name, id, label, type, readonly, pattern, required,
        }) => (
          <div key={id}>
            <label
              className="form-label"
              htmlFor={id}
            >
              {label}
            </label>
            <input
              className="field"
              onChange={this.onChange}
              name={name}
              id={id}
              type={type}
              readOnly={readonly}
              pattern={pattern}
              value={this.state[name]}
              required={required}
            />
          </div>))
        }

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
  inputs: PropTypes.arrayOf(PropTypes.shape({
    name: PropTypes.string,
    id: PropTypes.string,
    pattern: PropTypes.string,
    label: PropTypes.string,
    type: PropTypes.string,
    value: PropTypes.string,
    readonly: PropTypes.bool,
  })).isRequired,
};

export default CustomForm;
