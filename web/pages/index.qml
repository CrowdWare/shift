import FlatSiteBuilder 2.0
import RevolutionSlider 1.0
import TextEditor 1.0
import ImageEditor 1.0

Content {
    title: "Shift"
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
	&lt;strong&gt;Willkommen&lt;/strong&gt; bei SHIFT
&lt;/h1&gt;
&lt;p&gt;SHIFT ist ein Projekt von &lt;a href=&quot;https://www.crowdware.at&quot;&gt;CrowdWare&lt;/a&gt;&lt;/p&gt;

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
		&lt;h4&gt;&lt;strong&gt;Grundeinkommen&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;Wir stehen gerade vor schweren Zeiten. Was ist, wenn wir unser eigenes Grundeinkommen schaffen?&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Grundeinkommen"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
	&lt;div class=&quot;box-content&quot;&gt;
		&lt;i class=&quot;fa fa-lightbulb-o&quot;&gt;&lt;/i&gt;
		&lt;h4&gt;&lt;strong&gt;Dankbarkeit&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;Wir erstellen eine virtuelle Flüssigkeit namens THX, mit der Dankbarkeit gezeigt werden kann.&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Dankbarkeit"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
	&lt;div class=&quot;box-content&quot;&gt;
		&lt;i class=&quot;fa fa-users&quot;&gt;&lt;/i&gt;
		&lt;h4&gt;&lt;strong&gt;Mikro-Blogging&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;Keine Zensur. Anonym. Keine Werbung. Keine Registration. Kein Datenmissbrauch.&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Mikro-Blogging"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
	&lt;div class=&quot;box-content&quot;&gt;
		&lt;i class=&quot;fa fa-flag-checkered&quot;&gt;&lt;/i&gt;
		&lt;h4&gt;&lt;strong&gt;Sicheres Chatten&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;Sicheres Chatten ist ebenfalls verfügbar. Wir benutzen keine Server! Wir sind dezentral.&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Sicheres Chatten"
                }
            }
        }

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;p class=&quot;lead&quot;&gt;
	Die Idee hinter dem Wandel ist die Möglichkeit, mit einer neuen virtuellen Flüssigkeit, 
	die von Menschen hergestellt wird, ein universelles Grundeinkommen zu schaffen. 
	Die Möglichkeit, eine eigene Flüssigkeit als Energieaustausch zu nutzen, macht es uns allen leicht, 
	Dinge zu teilen, Dienstleistungen zu erbringen und unseren Dank auszudrücken.
	Wir werden niemals gezwungen sein, Steuern darauf zu zahlen, weil es überhaupt kein Geld ist.
	Es ist nur eine virtuelle Flüssigkeit.
&lt;/p&gt;

&lt;h3&gt;Ziel&lt;/h3&gt;
&lt;p class=&quot;lead&quot;&gt;
	Wir werden eine App erstellen, mit der die Benutzer täglich neue Flüssigkeiten schöpfen können.
	Wir haben dieser neuen virtuellen Flüssigkeit den Namen THX gegeben.
	THX basiert auf dem Satz Thank You aus dem Englischen und repräsentiert die Dankbarkeit.
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
				&lt;h4&gt;&lt;strong&gt;Wir sind&lt;/strong&gt;&lt;/h4&gt;
				&lt;p class=&quot;lead&quot;&gt;
				 	der Wandel...
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
	Die erste App für Android ist fast fertig und wird in Kürze veröffentlicht.
&lt;/p&gt;
&lt;h4&gt;Phase I&lt;/h4&gt;
&lt;p&gt;
	Wir werden eine sehr einfache App erstellen, damit der Benutzer neue Flüssigkeit schöpfen kann.
	Die Benutzer erstellen jeden Tag, an dem sie den Prozess in der App starten, etwa 10 Liter THX.
	Die Benutzer können auch andere Benutzer empfehlen und für jeden empfohlenen Benutzer pro Tag 1 Liter THX schöpfen.
	Jeder Benutzer kann bis zu 10 andere Benutzer hinzufügen (andernfalls würde es zu einem Schneeballsystem werden, was unfair ist).
	weil derjenige an der Spitze am meisten verdient)
&lt;/p&gt;
&lt;h4&gt;Phase II&lt;/h4&gt;
&lt;p&gt;
	Wenn wir 1.000.000 Benutzer erreichen oder wenn wir genug Leute finden, die die Entwicklungskosten über Crowdfunding finanzieren, 
	werden wir eine Dankbarkeitsfunktion in der App entwickeln.
	Jeder Benutzer kann sich dann dafür bedanken, indem er THX von einer App zur anderen überträgt.
	So kann der Benutzer die erstellte Flüssigkeit an eine andere Person weitergeben.
	Die Marke 1.000.000 wird einige Entwickler motivieren, mitzumachen, weil sie sehen, dass die Leute THX akzeptieren
	und das motiviert die Entwickler und sie werden für THX statt für Geld mitmachen.
&lt;/p&gt;

&lt;h4&gt;Phase III&lt;/h4&gt;
&lt;p&gt;
	Es wird eventuell eine Chat-Funktion geben.
	Es wird eventuell auch eine Mikro-Blogging-Engine geben, mit der Sie Dinge und Dienste anbieten können.
&lt;/p&gt;
&lt;h4&gt;Phase IV&lt;/h4&gt;
&lt;p&gt;
	Es wird die höchstwahrscheinlich eine Möglichkeit geben, Plugins zu implementieren, damit wir alle neue Inhalte für eine neue Zeit im Wandel erstellen können.
&lt;/p&gt;
&lt;h4&gt;Phase V&lt;/h4&gt;
&lt;p&gt;
	Nach Erreichen der Menge von 10.000.000 Benutzern wird die Erzeugung von THX auf 10 Liter pro Tag reduziert.
	Damit erhalten alle Benutzer den gleichen Betrag pro Tag.
	An diesem Punkt werden wir die Verbindung zum Server trennen und die Plattform wird vollständig dezentral ausgeführt.
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
	&lt;h3&gt;Willst Du die &lt;strong&gt;APP&lt;/strong&gt; für Android haben and anfangen Flüsigkeit zu schöpfen?&lt;a href=&quot;javascript:alert(&#x27;We are still working on that.&#x27;)&quot; target=&quot;_self&quot; class=&quot;btn btn-primary btn-lg&quot;&gt;DOWNLOAD&lt;/a&gt;&lt;/h3&gt;
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
                    text: "&lt;h3&gt; Über die Flüssigkeit &lt;/h3&gt;
&lt;p&gt;
	Wenn wir über Gehälter und Geld sprechen, dann sprechen wir auch über Liquidität. Wenn Sie also genug 
	Geld haben, sind Sie liquide.&lt;/br&gt;
	Tatsächlich wollen wir kein Geld mehr verwenden, weil in dem alten System, wie wir es vor 2020 kannten, 
	einige Leute Geld missbraucht haben, um es zu horten.&lt;/br&gt;
	Mit dieser Aktion stoppten sie den freien Energiefluss. Also haben sie eine Blockade geschaffen, 
	um viel Geld für sich zu behalten. Alle anderen Menschen waren ausgetrocknet und mussten leiden.&lt;/br&gt;&lt;/br&gt;

	Mit THX werden alle Menschen gleich sein. Zunächst sollten wir jede Stunde über Dankbarkeit nachdenken. 
	Wenn wir also eine Stunde lang etwas für jemand anderen tun, kann jemand Dankbarkeit zeigen und 60 Liter 
	THX geben. Also ein Liter pro Minute.&lt;/br&gt;
	Ein Softwareentwickler in Norwegen erhält also stündlich 60 Liter THX, ebenso ein Friseur in Indien.&lt;/br&gt;
	Einfach nur fair.
&lt;/p&gt;
&lt;h3&gt; Inflation / Deflation &lt;/h3&gt;
&lt;p&gt;
	Damit wir den Planeten nicht überfluten, verdampft die Flüssigkeit jeden Tag einen kleinen Prozentsatz, 
	bis sie nach 7 Jahren komplett verschwunden ist. So wird es in einem vollen Zyklus erstellt, 
	verwendet und wieder zerstört.
&lt;/p&gt;
&lt;h3&gt;Sicherheit / Integrität&lt;/h3&gt;
&lt;p&gt;
	Die lokale Datenbank wurde mit einem 1024 Bits Schlüssel verschlüsselt. 
	Dies macht es für jemanden unmöglich, Daten in der lokalen Datenbank zu ändern. 
	Was natürlich auch für die Balance gilt.
	Wenn wir THX zwischen zwei Mobiltelefonen übertragen, werden auch die Daten mit diesem 1024 Bits Schlüssel 
	verschlüsselt. Diese Transer-Daten können also nur mit der Original-App entschlüsselt werden.&lt;/br&gt;&lt;/br&gt;

	Wenn Sie eine Menge THX auf eine gefälschte mobile App übertragen. Es spielt keine Rolle. 
	Der Typ mit der gefälschten mobilen App wird Ihnen nicht mitteilen, dass die Übertragung abgebrochen wurde.&lt;/br&gt;&lt;/br&gt;

	Wenn jemand THX auf Ihr Konto übertragen möchte, akzeptiert Ihre App diese Übertragung aufgrund falsch entschlüsselter Daten nicht.
&lt;/p&gt;

&lt;h3&gt;FAQ&lt;/h3&gt;
&lt;ul&gt;
	&lt;li&gt;
		Klingt komisch, dass Sie Flüssigkeit schöpfen?&lt;/br&gt;
		Wir können das Wort Geld nicht benutzen. Wenn du Geld erschaffst, müsstest du Steuern zahlen ;-)
	&lt;/li&gt;
	&lt;li&gt;
		Wird es eine Version für das iPhone geben? &lt;/br&gt;
		Ja, wenn wir ein iPhone in die Hände bekommen, um es zu testen. (ob Sie es glauben oder nicht, wir haben ein sehr geringes Budget)
	&lt;/li&gt;
	&lt;li&gt;
		Wann gehen Sie in Phase II? &lt;/br&gt;
		Wir werden in die Phase II gehen, nachdem wir 1.000.000 Menschen mit der App erreicht haben. 
		Dann wird es eine kritische Masse an Menschen geben, die THX als Energieaustausch akzeptieren. 
		Dann finden wir auch Entwickler, die wir mit THX auszahlen können.
		Eine andere Möglichkeit wird sein, dass wir Sponsoren finden, um die Entwicklungskosten zu finanzieren.
	&lt;/li&gt;
&lt;/ul&gt;"
                    adminlabel: "FAQ"
                }
            }
        }
    }
}
