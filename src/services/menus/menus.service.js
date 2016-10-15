const Menus = function Menus() {
  let menu = {
    's': [
      {
        link:"account",
        name:"My Account"
      },
      {
        link:"info",
        name:"My Info"
      },
      {
        link:"talks",
        name:"My Talks"
      }
    ],
    'o': [
      {
        link:"oaccount",
        name:"My Account"
      },
      {
        link:"otalks",
        name:"My Talks"
      }
    ]
  }


  return {
    getMenu: function (role){
      let menuRole;

      if (!role || role.length == 0) {
        return;
      }

      if ( ~role.indexOf('s')) {
        menuRole = 's';
      }

      if ( ~role.indexOf('o')) {
        menuRole = 'o';
      }
       return  menu[menuRole];
    }
  }

};

export default Menus;
