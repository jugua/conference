import React from 'react';
import baseUrl from '../../constants/backend-url';
import CardsList from '../CardsList';

const Past = () => <CardsList url={`${baseUrl}/api/conference/past`} />;

export default Past;

