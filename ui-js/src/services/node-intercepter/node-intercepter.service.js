
const tokenInjector = (LocalStorage) => {
  'ngInject';

  const sessionInjector = {
    request: (config) => {
      if (!document.cookie.includes('XSRF')
        && LocalStorage.getToken()
        && LocalStorage.getToken() !== 'auth') {
        config.headers.token = LocalStorage.getToken();
      }
      return config;
    }
  };
  return sessionInjector;
};

export default tokenInjector;