import React from 'react';
import PropTypes from 'prop-types';
import action from '../../constants/actions-types';
import Card from './Card';

const SetCards = ({ data, id, load }) => (<div>{data.map((element) => {
  const { SET_CONFERENCE } = action;
  load(SET_CONFERENCE, data);
  return (<Card
    data={element}
    key={element.id}
    id={id}
    setConference={this.setConference}
  />);
})}</div>);

SetCards.propTypes = {
  id: PropTypes.number.isRequired,
  load: PropTypes.func.isRequired,
  data: PropTypes.arrayOf(PropTypes.object).isRequired,
};
export default SetCards;
