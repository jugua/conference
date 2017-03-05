const Menus = function Menus() {
  const menu = {
    speaker: [
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
    },
    getTopics: () => ['JVM Languages and new programming paradigms',
      'Web development and Java Enterprise technologies',
      'Software engineering practices',
      'Architecture & Cloud',
      'BigData & NoSQL'
    ],
    getTypes: () => ['Regular Talk',
      'Lighting Talk',
      'Online Talk',
      'Hands-On-Lab'
    ],
    getLang: () => ['English',
      'Ukrainian',
      'Russian'
    ],
    getTalksLevels: () => ['Beginner',
      'Intermediate',
      'Advanced',
      'Expert'
    ],
    getStatus: () => ['New',
      'In Progress',
      'Approved',
      'Rejected'
    ]
  };
};

export default Menus;
