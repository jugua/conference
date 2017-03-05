
const tokenInjector = (LocalStorage, $document) => {
  'ngInject';

  const sessionInjector = {
    request: (config) => {
      if ($document[0].cookie.indexOf('XSRF') === -1
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