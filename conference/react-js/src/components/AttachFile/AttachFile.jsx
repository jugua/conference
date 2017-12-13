import React, { PureComponent } from 'react';
import PropTypes from 'prop-types';
import axios from 'axios';

import RaisedButton from 'material-ui/RaisedButton';
import AttachImg from 'material-ui/svg-icons/editor/attach-file';
import Chip from 'material-ui/Chip';

class AttachFile extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      fileToSave: '',
      fileName: '',
    };
  }

  componentDidMount() {
    this.getTalkFileName(this.props.talk.id);
  }

  getTalkFileName = (id) => {
    axios.get(`/talk/${id}/takeFileName`)
      .then(({ data }) => {
        this.setState({
          fileName: data.fileName,
        });
      });
  };

  getName = (e) => {
    e.preventDefault();

    const file = e.target.files[0];
    this.setState({
      fileToSave: file.name,
    });
  };

  handleRequestDelete = () => {
    const file = document.querySelector('#attached-file');
    file.value = '';

    this.setState({
      fileToSave: '',
    });
  };

  uploadFile = (e) => {
    e.preventDefault();

    const { id } = this.props.talk;
    const attachedFileBtn = document.querySelector('#attached-file');
    const attachedFile = attachedFileBtn.files[0];

    const fileData = new FormData();
    fileData.append('file', attachedFile);

    if (attachedFileBtn.files.length > 0) {
      axios.post(`/talk/${id}/uploadFile`, fileData)
        .then(() => {
          this.handleRequestDelete();
          this.getTalkFileName(id);
        });
    }
  };

  render() {
    const { fileToSave, fileName } = this.state;

    return (
      <div className="attach-file_wrapper">
        <h1 className="attach-file__title">Attached file</h1>
        <Chip>
          {fileName}
        </Chip>
        <form
          encType="multipart/form-data"
          className="attach-file__form"
        >
          <RaisedButton
            containerElement="label"
            label="Attach"
            icon={<AttachImg />}
            primary
          >
            <input
              type="file"
              name="file"
              id="attached-file"
              onChange={this.getName}
            />
          </RaisedButton>
          { fileToSave !== '' ?
            <div>
              <Chip
                onRequestDelete={this.handleRequestDelete}
              >
                {fileToSave}
              </Chip>
              <RaisedButton
                label="Save"
                primary
                onClick={this.uploadFile}
              />
            </div>
            : null
          }
        </form>
      </div>
    );
  }
}

AttachFile.propTypes = {
  talk: PropTypes.shape({
    id: PropTypes.number.isRequired,
  }).isRequired,
};

export default AttachFile;
