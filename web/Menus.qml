import FlatSiteBuilder 2.0

Menus {
    Menu {
        name: 'default'
        Menuitem {
            title: 'Home'
            url: 'index.html'
            icon: ''
        }

        Menuitem {
            title: 'Blog'
            url: 'blog.html'
            icon: ''
        }
        Menuitem
        {
            title: 'Deutsch'
            url: '#'
            icon: 'assets/images/flags/de.png'

            Menuitem
            {
                title: '[US] English'
                url: 'index-en.html'
                icon: 'assets/images/flags/us.png'
            }

            Menuitem
            {
                title: '[DE] Deutsch'
                url: '#'
                icon: 'assets/images/flags/de.png'
            }
        }
    }
    Menu {
        name: 'default-en'
        Menuitem {
            title: 'Home'
            url: 'index.html'
            icon: ''
        }

        Menuitem {
            title: 'Blog'
            url: 'blog.html'
            icon: ''
        }
        Menuitem
        {
            title: 'English'
            url: '#'
            icon: 'assets/images/flags/us.png'

            Menuitem
            {
                title: '[US] English'
                url: '#'
                icon: 'assets/images/flags/us.png'
            }

            Menuitem
            {
                title: '[DE] Deutsch'
                url: 'index.html'
                icon: 'assets/images/flags/de.png'
            }
        }
    }
}
