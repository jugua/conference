import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

class SlideBlock extends PureComponent {
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

  onSubmit = (event) => {
    event.preventDefault();
    this.props.saveAction(this.state);
  };

  onCancel = () => {
    this.setDefaultValues();
    this.props.cancelAction();
  };

  setDefaultValues = () => {
    this.props.inputs.forEach(({ name, value }) => {
      this.setState({
        [name]: value,
      });
    });
  };

  toggle = () => {
    this.props.show(this.props.index);
  };

  render() {
    console.log('slide rendered');
    const {
      header,
      inputs,
      brief: briefString,
      isOpened } = this.props;

    const details = (
      <div className="settings__details settings__row">
        <div className="settings__title">{header}</div>
        <form
          className="settings__row-content"
          onChange={this.onChange}
          onSubmit={this.onSubmit}
        >
          <div>
            {
              inputs.map(({ name, id, label, type, readonly }) => (
                <div key={id}>
                  <label
                    className="form-label"
                    htmlFor={id}
                  >{label}
                  </label>
                  <input
                    className="field"
                    name={name}
                    id={id}
                    type={type}
                    readOnly={readonly}
                    value={this.state[name]}
                  />
                </div>
              ))
            }
            <input
              type="submit"
              className="btn btn__inline"
              value="Save"
              onClick={this.toggle}
            />
            <input
              type="button"
              className="btn btn__inline"
              value="Cancel"
              onClick={this.onCancel}
            />
          </div>
        </form>
      </div>
    );

    const brief = (
      <div
        className="settings__brief settings__row"
        role="button"
        onClick={this.toggle}
        tabIndex={0}
      >
        <div className="settings__title">
          {header}
        </div>
        <div className="settings__row-content">
          {briefString}
        </div>
      </div>
    );

    return isOpened ? details : brief;
  }
}

SlideBlock.propTypes = {
  index: PropTypes.number.isRequired,
  isOpened: PropTypes.bool.isRequired,
  header: PropTypes.string.isRequired,
  brief: PropTypes.string.isRequired,
  saveAction: PropTypes.func.isRequired,
  cancelAction: PropTypes.func.isRequired,
  show: PropTypes.func.isRequired,
  inputs: PropTypes.arrayOf(PropTypes.shape({
    name: PropTypes.string,
    id: PropTypes.string,
    label: PropTypes.string,
    type: PropTypes.string,
    value: PropTypes.string,
    readonly: PropTypes.bool,
  })).isRequired,
};

export default SlideBlock;
