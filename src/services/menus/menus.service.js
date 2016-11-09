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
    },
    getTopics: () => ['JVM Languages and new programming paradigms',
      'Web development and Java Enterprise technologies',
      'Software engineering practices',
      'Architecture & Cloud',
      'BigData & NoSQL'
    ]
    ,
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
    ]
  };
};

export default Menus;
