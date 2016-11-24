/* global window */

function LocalStorage() {
  function getToken() {
    let info = window.localStorage.userInfo;
    let token;

    if (info) {
      info = JSON.parse(info);
      token = info.token;
    } else {
      token = '';
    }

    return token;
  }
  return {
    getToken
  };
}

export default LocalStorage;