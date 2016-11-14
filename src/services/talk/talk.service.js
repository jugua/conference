export default class TalkService {

  constructor($resource, $window) {
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

    this.talks = $resource('api/talk', {}, {
      add: {
        method: 'POST',
        headers: {
          token: getToken,
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      },
      getAll: {
        method: 'GET',
        isArray: true,
        headers: {
          token: getToken,
          'Cache-Control': 'no-cache, no-store',
          Pragma: 'no-cache'
        }
      }
    });

  }

  getAll() {
    if (this._talks) {
      return this._talks;
    }

    this._talks = this.talks.getAll((res) => {
       // this._talks = res;

      },
      (err)=> {
        this._talks = [];
      });

    return this._talks;
  }

  add(talk) {
    this.talks.add(talk, (res) => {
        if (this._talks instanceof Array) {
          this._talks.push(res);
        }
      },
      (err)=> {
        console.log(err);
      });
  }

}

