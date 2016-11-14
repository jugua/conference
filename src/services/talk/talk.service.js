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
    if (this.talks) {
      return this.talks;
    }
    this.talks = this.talks.getAll((res) => {
        this.talks = res;

      },
      (err)=> {
        this.talks = [];
      });

    return this.talks;
  }

  add(talk) {
    this.talks.add(talk, (res) => {
        if (this.talks instanceof Array) {
          this.talks.push(res);
        }
      },
      (err)=> {
        console.log(err);
      });
  }

}

