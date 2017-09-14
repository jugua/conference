/* global window */

function LocalStorage($window) {
  'ngInject';

  function getToken() {
    let info = $window.localStorage.userInfo;
    let token;

    if (info) {
      info = JSON.parse(info);
      token = info.token;
    } else {
      token = '';
    }

    return token;
  }

  function getItem(key) {
    return $window.localStorage.getItem(key);
  }

  function setItem(key, value) {
    $window.localStorage.setItem(key, value);
  }

  function removeItem(key) {
    $window.localStorage.removeItem(key);
  }

  return {
    getToken,
    getItem,
    setItem,
    removeItem,
  };
}

export default LocalStorage;