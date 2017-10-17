import React from 'react';
import { connect } from 'react-redux';
import Talks from '../../containers/Talks';

const MyTalks = () => (
  <Talks />
);

const mapStateToProps = state => ({
  userTalks: state.userTalks,
});

export default connect(mapStateToProps)(MyTalks);
