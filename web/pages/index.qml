import FlatSiteBuilder 2.0
import RevolutionSlider 1.0
import TextEditor 1.0
import ImageEditor 1.0

Content {
    title: "Index"
    menu: "default"
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
&lt;p class=&quot;lead&quot;&gt;
	Our goal is to create universal basic income for everybody.	
&lt;/p&gt;

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
		&lt;p&gt;We are facing real hard times right now. What if we craete our own basic income.&lt;/p&gt;
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
		&lt;p&gt;We are creating a virtual liquid called THX which can be used to show gratitude.&lt;/p&gt;
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
        cssclass: "margin-top80"

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
&lt;p&gt;
	In the first phase the app doesn&#x27;t have that much functionality.
	You can start to scoop liquid, which we call THX and you can invite your friends to 
	join the circle of trusted users.

&lt;/p&gt;"
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
	&lt;h3&gt;Do you want to get the &lt;strong&gt;APP&lt;/strong&gt; for Android and start to scoop liquid?&lt;a href=&quot;javascript:alert(&#x27;We are still working on that.&#x27;)&quot; target=&quot;_self&quot; class=&quot;btn btn-primary btn-lg&quot;&gt;DOWNLOAD&lt;/a&gt;&lt;/h3&gt;
&lt;/div&gt;
&lt;!-- /CALLOUT --&gt;"
                    adminlabel: "callout"
                }
            }
        }
    }
}
