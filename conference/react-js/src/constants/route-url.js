import path from 'path';

const { resolve } = path;

export const root = '/react/';

export const upcoming = root;
export const history = resolve(root, 'history');
export const forgotPassword = resolve(root, 'forgot-password');
export const myTalks = resolve(root, 'my-talks');
export const myEvents = resolve(root, 'my-events');
export const talks = resolve(root, 'talks');
export const signUp = resolve(root, 'sign-up');
export const account = resolve(root, 'account');
export const manageUser = resolve(root, 'manage-user');
export const conference = resolve(root, 'conference');

export const myInfo = resolve(account, 'my-info');
export const settings = resolve(account, 'settings');
