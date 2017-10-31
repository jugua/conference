import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';

import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import ImageEdit from 'material-ui/svg-icons/image/edit';
import AttachFile from 'material-ui/svg-icons/editor/attach-file';
import { cyan500 } from 'material-ui/styles/colors';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';

import getTopics from '../../actions/getTalksTopics';
import getTypes from '../../actions/getTalksTypes';
import getLanguages from '../../actions/getTalksLanguages';
import getTalksLangLevels from '../../actions/getTalksLangLevels';
import updateTalk from '../../actions/updateTalk';

const buttonStyle = {
  background: '#B22746',
  color: '#fff',
};

class UpdateTalk extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      listOfTopics: [],
      listOfTypes: [],
      listOfLanguages: [],
      listOfLangLevels: [],
      title: '',
      description: '',
      topic: '',
      type: '',
      lang: '',
      level: '',
      addon: '',
    };
  }

  componentDidMount() {
    getTopics().then(({ data }) => {
      this.setState({
        listOfTopics: data,
      });
    });

    getTypes().then(({ data }) => {
      this.setState({
        listOfTypes: data,
      });
    });

    getLanguages().then(({ data }) => {
      this.setState({
        listOfLanguages: data,
      });
    });

    getTalksLangLevels().then(({ data }) => {
      this.setState({
        listOfLangLevels: data,
      });
    });

    this.setDefaultValues();
  }

  setDefaultValues = () => {
    this.setState(this.props.talk);
  }

  topicChange = (event, index, value) => this.setState({ topic: value });
  typeChange = (event, index, value) => this.setState({ type: value });
  langChange = (event, index, value) => this.setState({ lang: value });
  levelChange = (event, index, value) => this.setState({ level: value });
  titleChange = (event, value) => this.setState({ title: value });
  descrChange = (event, value) => this.setState({ description: value });
  addonChange = (event, value) => this.setState({ addon: value });

  updateHandler = () => {
    const updatedTalk = { ...this.props.talk };
    Object.keys(updatedTalk).forEach(
      (key) => { updatedTalk[key] = this.state[key]; },
    );
    updateTalk(updatedTalk);
  }

  render() {
    const {
      topic,
      type,
      lang,
      title,
      level,
      description,
      addon,
      listOfTopics,
      listOfTypes,
      listOfLangLevels,
      listOfLanguages } = this.state;

    const { close } = this.props;

    return (
      <div className="update-talk_wrapper">

        <div className="update-talk__title">
          <TextField
            floatingLabelText="Title"
            style={{ flexGrow: 1 }}
            value={title}
            onChange={this.titleChange}
          />

          <RaisedButton
            className="update-talk__button"
            label="File"
            icon={<AttachFile />}
            primary
          />

          <RaisedButton
            className="update-talk__button"
            label="Update"
            icon={<ImageEdit />}
            primary
            onClick={this.updateHandler}
          />

          <RaisedButton
            className="update-talk__button"
            label="Close"
            buttonStyle={buttonStyle}
            onClick={close}
            primary
          />
        </div>
        <TextField
          floatingLabelText="Description"
          multiLine
          rows={2}
          fullWidth
          value={description}
          onChange={this.descrChange}
        />

        <div className="select-field_wrapper">
          <SelectField
            floatingLabelText="Topic"
            value={topic}
            autoWidth
            selectedMenuItemStyle={{ color: cyan500 }}
            onChange={this.topicChange}
          >
            {
              listOfTopics.map(({ id, name }) => (
                <MenuItem
                  value={name}
                  primaryText={name}
                  key={id}
                />),
              )
            }
          </SelectField>

          <SelectField
            floatingLabelText="Type"
            value={type}
            autoWidth
            selectedMenuItemStyle={{ color: cyan500 }}
            onChange={this.typeChange}
          >
            {
              listOfTypes.map(({ id, name }) => (
                <MenuItem
                  value={name}
                  primaryText={name}
                  key={id}
                />),
              )
            }
          </SelectField>

          <SelectField
            floatingLabelText="Language"
            value={lang}
            autoWidth
            selectedMenuItemStyle={{ color: cyan500 }}
            onChange={this.langChange}
          >
            {
              listOfLanguages.map(({ id, name }) => (
                <MenuItem
                  value={name}
                  primaryText={name}
                  key={id}
                />),
              )
            }
          </SelectField>

          <SelectField
            floatingLabelText="Level"
            value={level}
            autoWidth
            selectedMenuItemStyle={{ color: cyan500 }}
            onChange={this.levelChange}
          >
            {
              listOfLangLevels.map(({ id, name }) => (
                <MenuItem
                  value={name}
                  primaryText={name}
                  key={id}
                />),
              )
            }
          </SelectField>
        </div>

        <TextField
          floatingLabelText="Additional info"
          multiLine
          rows={2}
          fullWidth
          value={addon}
          onChange={this.addonChange}
        />

      </div>
    );
  }
}

UpdateTalk.defaultProps = {
  talk: {},
};

UpdateTalk.propTypes = {
  close: PropTypes.func.isRequired,
  talk: PropTypes.shape({
    title: PropTypes.string,
    description: PropTypes.string,
    topic: PropTypes.string,
    type: PropTypes.string,
    lang: PropTypes.string,
    level: PropTypes.string,
    addon: PropTypes.string,
  }),
};

export default UpdateTalk;
