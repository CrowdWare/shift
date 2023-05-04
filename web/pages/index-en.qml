import FlatSiteBuilder 2.0
import RevolutionSlider 1.0
import TextEditor 1.0
import ImageEditor 1.0

Content {
    title: "Shift"
    menu: "default-en"
    author: "Adam"
    layout: "default"
    date: "2021-02-04"

    Section {
        fullwidth: true

        RevolutionSlider {
            fullwidth: true

            Slide {
                src: "/Users/user/SourceCode/Shift/assets/images/happypeople.png"
                adminlabel: "HappyPeople"
            }

            Slide {
                src: "/Users/user/SourceCode/Shift/assets/images/gull.png"
                adminlabel: "Gull"
            }

            Slide {
                src: "/Users/user/SourceCode/Shift/assets/images/butterfly.png"
                adminlabel: "Butterfly"
            }
        }
    }

    Section {

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;h1&gt;
	&lt;strong&gt;Welcome&lt;/strong&gt; to SHIFT
&lt;/h1&gt;
&lt;p&gt;SHIFT is a project from &lt;a href=&quot;https://www.crowdware.at&quot;&gt;CrowdWare&lt;/a&gt;&lt;/p&gt;


"
                }
            }
        }
    }

    Section {

        Row {

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
	&lt;div class=&quot;box-content&quot;&gt;
		&lt;i class=&quot;fa fa-bolt&quot;&gt;&lt;/i&gt;
		&lt;h4&gt;&lt;strong&gt;Basic Income&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;We are facing real hard times right now. What if we create our own basic income.&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Basic Income"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
	&lt;div class=&quot;box-content&quot;&gt;
		&lt;i class=&quot;fa fa-lightbulb-o&quot;&gt;&lt;/i&gt;
		&lt;h4&gt;&lt;strong&gt;Liquid&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;We are creating a virtual liquid called LMC which can be used to show gratitude.&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Liquid"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
	&lt;div class=&quot;box-content&quot;&gt;
		&lt;i class=&quot;fa fa-users&quot;&gt;&lt;/i&gt;
		&lt;h4&gt;&lt;strong&gt;Micro-Blogging&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;No censorship. Anonymous. No ads. No registration. No data mis-usage.&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Micro-Blogging"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
	&lt;div class=&quot;box-content&quot;&gt;
		&lt;i class=&quot;fa fa-flag-checkered&quot;&gt;&lt;/i&gt;
		&lt;h4&gt;&lt;strong&gt;Secure Chat&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;Secure chatting will also be available. We don&#x27;t use servers! We are decentral.&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Secure Chat"
                }
            }
        }

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;p class=&quot;lead&quot;&gt;
	The idea behind shift is the possibily to create a universal basic income with a new virtual liquid made by men. The possibilities to have an own liquid to use as energy exchange, makes it easy for us all to share things, give services and to express our gratitude.
	We will never be forced to pay taxes on it, because it&#x27;s not money at all.
	It&#x27;s just a virtual liquid.
&lt;/p&gt;

&lt;h3&gt;Goal&lt;/h3&gt;
&lt;p class=&quot;lead&quot;&gt;
We are going to create an app which the users can use to create new liquid on a daily base.
We gave the name LMC (&lt;b&gt;L&lt;/b&gt;iquid&lt;b&gt;M&lt;/b&gt;icro&lt;b&gt;C&lt;/b&gt;oins)to this new virtual liquid.
LMC is based on the phrase thank you and represents the gratitude.
&lt;/p&gt;
"
                }
            }
        }
    }

    Section {
        cssclass: "parallax margin-top80"
        style: "background-image: url('assets/images/natur2.jpg');"
        attributes: "data-stellar-background-ratio='0.7'"

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;div class=&quot;container&quot;&gt;
	&lt;div class=&quot;row animation_fade_in&quot;&gt;
		&lt;div class=&quot;col-md-6&quot;&gt;&lt;/div&gt;
		&lt;div class=&quot;col-md-6&quot;&gt;
			&lt;div class=&quot;white-row&quot;&gt;
				&lt;h4&gt;&lt;strong&gt;We are&lt;/strong&gt;&lt;/h4&gt;
				&lt;p class=&quot;lead&quot;&gt;
					creating the change...
				&lt;/p&gt;
			&lt;/div&gt;
		&lt;/div&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Parallax"
                }
            }
        }
    }

    Section {
        cssclass: "container margin-top80"

        Row {

            Column {
                span: 4

                Image {
                    src: "preview.png"
                    animation: "slideInLeft"
                    animation_type: "Sliding Entrances"
                }
            }

            Column {
                span: 8

                Text {
                    text: "&lt;h3&gt;First App for Android&lt;/h3&gt;
&lt;p class=&quot;lead&quot;&gt;
	The first app for Android is almost ready and will be released shortly.
&lt;/p&gt;
&lt;h4&gt;Phase I&lt;/h4&gt;
&lt;p&gt;
	We are going to create a very simple app just to enable the user to scoop new liquid. 
	The users are creating like 10 liters of LMC every day they start the process in the app.
	The users can also refer other users and can earn 1 liter of LMC for every referred user per day. 
	Every user can add up to 10 other users (otherwise it would become a snowball system, which is unfair, 
	because the one on te top will earn the most)
&lt;/p&gt;
&lt;h4&gt;Phase II&lt;/h4&gt;
&lt;p&gt;
	When we reach 1.000.000 users or when we will find enough people crowdfunding the development costs, we are going to develop a gratitude function in the app. 
	Every user will then be able to show gratitude giving LMC from one app to the other. 
	So the user is able to give the liquid which has been created to someone else.
	The mark 1.000.000 will motivate some developers to join in, because they see that the people 
	want this app and the devlopers will be kind of payed with LMC instead of money.
&lt;/p&gt;

&lt;h4&gt;Phase III&lt;/h4&gt;
&lt;p&gt;
	There will be a chat function.
	There will also be a micro blogging engine, so that you are able to share things and services.
&lt;/p&gt;
&lt;h4&gt;Phase IV&lt;/h4&gt;
&lt;p&gt;
	There will be the possibility to implement plugins, so that we all are able to create new content for a new time shift.
&lt;/p&gt;
&lt;h4&gt;Phase V&lt;/h4&gt;
&lt;p&gt;
	After reaching the amount of 10.000.000 users the creation of liquid will be dropped down to 10 liters a day, 
	so that all users are getting an equal amount per day. 
	At that point we will cut the connection to the server and the platform will running totally decentral.
&lt;/p&gt;

"
                }
            }
        }
    }

    Section {

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;!-- CALLOUT --&gt;
&lt;div class=&quot;bs-callout text-center styleBackground&quot;&gt;
	&lt;h3&gt;Do you want to get the &lt;strong&gt;APP&lt;/strong&gt; for Android and start to scoop liquid?&lt;a href=&quot;https://github.com/CrowdWare/Shift/releases/download/v1.1.0/shift-1.1.0-signed.apk&quot; target=&quot;_self&quot; class=&quot;btn btn-primary btn-lg&quot;&gt;DOWNLOAD&lt;/a&gt;&lt;/h3&gt;
&lt;/div&gt;
&lt;!-- /CALLOUT --&gt;"
                    adminlabel: "callout"
                }
            }
        }
    }

    Section {

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;h3&gt;About the liquid&lt;/h3&gt;
&lt;p&gt;
When we are talking about salaries and money, then we also talk about liquidity. So when you have got enough money you are liquid.&lt;/br&gt;
In fact we don&#x27;t wanne use money anymore, because in the old system as we knew it from before 2020, some people abused money to hoard it. With this action they stopped the free flow of energy. So they created a blockade to keep much money for themselves. All other people where dryed out and had to suffer.&lt;/br&gt;&lt;/br&gt;

With LMC all human beings will be equal. First of all we should think about gratitude for every hour. So when we do something for an hour for someone else, than this someone can show gratitude giving 60 liters of LMC.&lt;/br&gt; So one liter per minute.
So a softwaredeveloper in Norway will get 60 liters of LMC every hour and so does a hairdresser in India.&lt;/br&gt;
Just fair ins&#x27;nt it.
&lt;/p&gt;
&lt;h3&gt;Inflation/Deflation&lt;/h3&gt;
&lt;p&gt;
	So that we will not flood the planet, the liquid will vaporate every day a small percentage until it&#x27;s gone 
	fully after 7 years. So it will be created, used and destroy in a full circle.
&lt;/p&gt;
&lt;h3&gt;Security / Integrity&lt;/h3&gt;
&lt;p&gt;	
	The local database has been encrypted.
	So this makes it impossible for someone to change any data in the local database.
	Which of course is also true for the balance.
	When we are transfering LMC between two mobile phones, also the data will be encrypted. 
So this tranfser data can only be decrypted with the original app.&lt;/br&gt;&lt;/br&gt;

	If you transfer an amount of LMC to a faked mobile app, it doesn&#x27;t matter. The guy with the faked mobile 
	app will not tell you that the transfer has been aborted.&lt;/br&gt;&lt;/br&gt;

	If someone wants to transfer LMC to your account, then your app will not accept this transfer due 
	to incorrect decrypted data.
&lt;/p&gt;

&lt;h3&gt;FAQ&lt;/h3&gt;
&lt;ul&gt;
	&lt;li&gt;
		Sounds weird that you scoop liquid?&lt;/br&gt; 
		We cannot call money. When you create money, then you would have to pay taxes ;-)
	&lt;/li&gt;
	&lt;li&gt;
		Will there be a version for iPhone? &lt;/br&gt;
		Yes, when we get the hands on an iPhone to test it. (believe it or not, we are on a very low budget)
	&lt;/li&gt;
	&lt;li&gt;
		When will you go into phase II?&lt;/br&gt;
		We will go into phase II after reaching 1.000.000 people using the app. Then there will be a critical mass
		who will accept LMC as an energy exchange. Then we also will find developers who we can pay out with LMC.
		Another possibilty will be that we find sponsors to finance the development costs.
	&lt;/li&gt;
&lt;/ul&gt;"
                    adminlabel: "FAQ"
                }
            }
        }
    }
}
