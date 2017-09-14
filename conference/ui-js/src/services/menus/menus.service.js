const Menus = function Menus() {
  const menu = {
    speaker: [
      {
        link: 'home',
        name: 'Conferences'
      },
      {
        link: 'tabs.myInfo',
        name: 'My Info'
      },
      {
        link: 'tabs.myTalks',
        name: 'My Talks'
      },
      {
        link: 'account',
        name: 'Settings'
      }
    ],
    organiser: [
      {
        link: 'home',
        name: 'Conferences'
      },
      {
        link: 'talks',
        name: 'Talks'
      },
      {
        link: 'account',
        name: 'Settings'
      }
    ],
    admin: [
      {
        link: 'manageUsers',
        name: 'Manage Users'
      }
    ]
  };

  return {
    getMenu: function getMenu(role) {
      let menuArr = [];

      if (!role || role.length === 0) {
        return false;
      }

      if (role.indexOf('ROLE_SPEAKER') !== -1) {
        menuArr = menuArr.concat(menu.speaker);
      }

      if (role.indexOf('ROLE_ORGANISER') !== -1) {
        menuArr = menuArr.concat(menu.organiser);
      }

      if (role.indexOf('ROLE_ADMIN') !== -1) {
        menuArr = menuArr.concat(menu.admin);
      }

      return menuArr;
    }
  };
};

export default Menus;
