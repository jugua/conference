import React from 'react';
import CardsList from '../../containers/CardsList';
import { upcoming } from '../../constants/backend-url';

const Upcoming = () => <CardsList url={upcoming} />;

export default Upcoming;
