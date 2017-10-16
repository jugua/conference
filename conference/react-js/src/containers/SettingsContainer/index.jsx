import React, { PureComponent } from 'react';
import { connect } from 'react-redux';
import PropTypes from 'prop-types';

import editUser from '../../actions/edit-user';
import SlideBlock from '../../components/SlideBlock';
import NameBrief from '../../components/NameBrief';
import EmailBrief from '../../components/EmailBrief';
import NameChangeForm from '../../components/NameChangeForm';
import EmailChangeForm from '../../components/EmailChangeForm';

class SettingsContainer extends PureComponent {
  constructor(props) {
    super(props);
    this.state = {
      fname: '',
      lname: '',
      oldMail: '',
      mail: '',
      currentPassword: '',
      newPassword: '',
      confirmNewPassword: '',
      current: null,
    };
  }

  componentDidMount() {
    this.setDefaultValues(this.props);
  }

  componentWillReceiveProps(nextProps) {
    this.setDefaultValues(nextProps);
  }

  setDefaultValues = ({ fname, lname, mail }) => {
    this.setState({
      fname,
      lname,
      oldMail: mail,
      mail: '',
    });
  }

  show = (id) => {
    this.setState({ current: id });
  }

  change = ({ target: { name, value } }) => {
    this.setState({
      [name]: value,
    });
  }

  submit = (e) => {
    e.preventDefault();
    const { fname, lname, mail, oldMail } = this.state;

    this.props.edit({
      fname,
      lname,
      mail: mail || oldMail,
    });

    this.cancel();
  }

  cancel = () => {
    this.setDefaultValues(this.props);
    this.setState({ current: -1 });
  }

  render() {
    const {
      fname,
      lname,
      mail,
      oldMail,
      // currentPassword,
      // newPassword,
      // confirmNewPassword,
      current,
    } = this.state;

    return (
      <div>
        <SlideBlock isOpened={current === 0}>
          <NameBrief
            show={() => this.show(0)}
            fname={fname}
            lname={lname}
          />
          <NameChangeForm
            cancel={this.cancel}
            submit={this.submit}
            change={this.change}
            fname={fname}
            lname={lname}
          />
        </SlideBlock>
        <SlideBlock isOpened={current === 1}>
          <EmailBrief
            show={() => this.show(1)}
            mail={oldMail}
          />
          <EmailChangeForm
            cancel={this.cancel}
            submit={this.submit}
            change={this.change}
            mail={mail}
            oldMail={oldMail}
          />
        </SlideBlock>
        {/* <SlideBlock isOpened={current === 2}>
          <EmailBrief show={() => this.show(2)} />
          <PasswordChangeForm cancel={this.cancel} />
        </SlideBlock> */}
      </div>
    );
  }
}

const mapStateToProps = ({
  user: { fname, lname, mail },
}) => ({
  fname, lname, mail,
});

SettingsContainer.propTypes = {
  edit: PropTypes.func.isRequired,
};

export default connect(
  mapStateToProps,
  { edit: editUser },
)(SettingsContainer);
