import React, { Component } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';
import { TextField, RaisedButton } from 'material-ui';

import { uploadUserPhoto, defaultUserPhoto } from
  '../../../constants/backend-url';
import userShape from '../../../constants/user-shape';

class SpeakerInfo extends Component {
  constructor(props) {
    super(props);
    this.state = {
      speaker: props.speaker,
    };
  }

  componentDidMount() {
    this.setDefaultUserPhoto();
  }

  componentWillReceiveProps({ speaker }) {
    this.setState({ speaker });
    this.setDefaultUserPhoto();
  }

    setDefaultUserPhoto = () => {
      axios.get(defaultUserPhoto)
        .then(() => {
          this.setState(prevState => ({
            speaker: {
              ...prevState.speaker,
              photo: defaultUserPhoto,
            },
          }));
        });
    };

    getUserPhoto = (id) => {
      axios.get(`${uploadUserPhoto}/${id}`)
        .then(() => {
          this.setState(prevState => ({
            speaker: {
              ...prevState.speaker,
              photo: `${uploadUserPhoto}/${id}`,
            },
          }
          ));
        });
    };

    render() {
      const { speaker: {
        bio,
        job,
        company,
        past,
        info,
        photo,
      } } = this.state;

      const { close } = this.props;

      return (
        <div>
          <div className="my-info__photo-block">
            <img
              className="my-info__photo"
              src={photo || ''}
              alt=""
            />
          </div>
          <form className="my-info">
            <div className="input-required input-required__bio">
              <TextField
                id="my-info-bio"
                name="bio"
                floatingLabelText="Short Bio*"
                multiLine
                fullWidth
                rows={5}
                maxLength={2000}
                value={bio || ''}
                disabled
              />
            </div>
            <div className="input-wrapper">
              <div className="input-required">
                <TextField
                  id="my-job-title"
                  name="job"
                  floatingLabelText="Job Title*"
                  fullWidth
                  maxLength={256}
                  value={job || ''}
                  disabled
                />
              </div>
              <div className="input-required">
                <TextField
                  id="my-info-company"
                  name="company"
                  floatingLabelText="Company*"
                  fullWidth
                  maxLength={256}
                  value={company || ''}
                  disabled
                />
              </div>
            </div>
            <TextField
              id="my-past-conferences"
              name="past"
              floatingLabelText="Past Conferences"
              multiLine
              rows={5}
              maxLength={1000}
              value={past || ''}
              fullWidth
              disabled
            />
            <TextField
              id="my-additional-info"
              name="info"
              floatingLabelText="Additional Info"
              multiLine
              rows={5}
              maxLength={1000}
              value={info || ''}
              fullWidth
              disabled
            />
            <RaisedButton
              type="button"
              label="back"
              primary
              onClick={close}
            />
          </form>
        </div>
      );
    }
}

SpeakerInfo.propTypes = {
  speaker: PropTypes.shape(userShape).isRequired,
  close: PropTypes.func.isRequired,
};

export default SpeakerInfo;
