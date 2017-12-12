import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

import TextField from 'material-ui/TextField';
import RaisedButton from 'material-ui/RaisedButton';
import ImageSave from 'material-ui/svg-icons/content/save';
import { cyan500 } from 'material-ui/styles/colors';
import SelectField from 'material-ui/SelectField';
import MenuItem from 'material-ui/MenuItem';

import AttachFile from '../AttachFile/AttachFile';
import getTalkOptions from '../../actions/getTalkOptions';
import updateTalk from '../../actions/updateTalk';

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
    getTalkOptions().then(([
      listOfTopics,
      listOfTypes,
      listOfLanguages,
      listOfLangLevels,
    ]) => {
      this.setState({
        listOfTopics,
        listOfTypes,
        listOfLanguages,
        listOfLangLevels,
      });
      this.setDefaultValues();
    });

    this.getTalkFile(this.props.talk.id);
  }

  setDefaultValues = () => {
    this.setState(this.props.talk);
  }

  getTalkFile = (id) => {
    axios.get(`/talk/${id}/takeFileName`)
      .then((file) => {
        this.setState(prevState => ({
          ...prevState,
          file,
        }));
      });
  };

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
        <div>
          <RaisedButton
            className="update-talk__button"
            label="Save"
            icon={<ImageSave />}
            primary
            onClick={this.updateHandler}
          />

          <RaisedButton
            className="update-talk__button"
            label="Back"
            onClick={close}
            primary
          />
        </div>

        <TextField
          className="update-talk__title"
          floatingLabelText="Title"
          value={title}
          onChange={this.titleChange}
        />

        <TextField
          floatingLabelText="Description"
          multiLine
          rows={3}
          fullWidth
          value={description}
          onChange={this.descrChange}
        />

        <div className="select-field_wrapper">
          <SelectField
            className="select-field__item select-field__topic"
            floatingLabelText="Topic"
            value={topic}
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
            className="select-field__item select-field__type"
            floatingLabelText="Type"
            value={type}
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
            className="select-field__item select-field__lang"
            floatingLabelText="Language"
            value={lang}
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
            className="select-field__item select-field__level"
            floatingLabelText="Level"
            value={level}
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

        <AttachFile talk={this.props.talk} />

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
    id: PropTypes.number,
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
