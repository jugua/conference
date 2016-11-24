const Menus = function Menus() {
  const menu = {
    s: [
      {
        link: 'account',
        name: 'My Account'
      },
      {
        link: 'tabs.myInfo',
        name: 'My Info'
      },
      {
        link: 'tabs.myTalks',
        name: 'My Talks'
      }
    ],
    o: [
      {
        link: 'oaccount',
        name: 'My Account'
      },
      {
        link: 'otalks',
        name: 'Talks'
      }
    ]
  };

  return {
    getMenu: function getMenu(role) {
      let menuRole;


      if (!role || role.length === 0) {
        return false;
      }

      if (role.indexOf('s') !== -1) {
        menuRole = 's';
      }

      if (role.indexOf('o') !== -1) {
        menuRole = 'o';
      }

      return menu[menuRole];
    }
  };
};

export default Menus;
