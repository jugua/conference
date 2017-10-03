import React from 'react';
import baseUrl from '../../constants/backend-url';
import CardsList from '../CardsList';

const Upcoming = () => <CardsList url={`${baseUrl}/api/conference/upcoming`} />;

export default Upcoming;
