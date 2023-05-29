import FlatSiteBuilder 2.0
import RevolutionSlider 1.0
import TextEditor 1.0
import ImageEditor 1.0

Content {
    title: "Shift"
    menu: "default-eo"
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
&lt;strong&gt;Bonvenon&lt;/strong&gt; al SHIFT
&lt;/h1&gt;
&lt;p&gt;SHIFT estas projekto de &lt;a href=&quot;https://www.crowdware.at&quot;&gt;CrowdWare&lt;/a&gt;&lt;/p&gt;"
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
	&lt;h4&gt;&lt;strong&gt;Valorinterŝanĝo&lt;/strong&gt;&lt;/h4&gt;
&lt;p&gt;Ni navigas tra defiitaj tempoj. Kio se ni repensus la manieron, laŭ kiu ni valoras kaj rekompencas unu la alian?&lt;/p&gt;
&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Baza Reddito"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
&lt;div class=&quot;box-content&quot;&gt;
	&lt;i class=&quot;fa fa-lightbulb-o&quot;&gt;&lt;/i&gt;
	&lt;h4&gt;&lt;strong&gt;Likva&lt;/strong&gt;&lt;/h4&gt;
	&lt;p&gt;Ni kreas virtualan likvon nomitan LMC, kiu povas esti uzata por montri dankemon.&lt;/p&gt;
&lt;/div&gt;
</div>"
                    adminlabel: "Likva"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
&lt;div class=&quot;box-content&quot;&gt;
	&lt;i class=&quot;fa fa-users&quot;&gt;&lt;/i&gt;
	&lt;h4&gt;&lt;strong&gt;Mikro-Blogo&lt;/strong&gt;&lt;/h4&gt;
	&lt;p&gt;Sen cenzuro. Anonima. Sen reklamoj. Sen registro. Sen misuzado de datumoj.&lt;/p&gt;
&lt;/div&gt;
</div>"
                    adminlabel: "M

ikro-Blogo"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
&lt;div class=&quot;box-content&quot;&gt;
	&lt;i class=&quot;fa fa-flag-checkered&quot;&gt;&lt;/i&gt;
	&lt;h4&gt;&lt;strong&gt;Sekura Babilejo&lt;/strong&gt;&lt;/h4&gt;
	&lt;p&gt;Sekura babilejo ankaŭ estos havebla. Ni ne uzas servilojn! Ni estas decentraligitaj.&lt;/p&gt;
&lt;/div&gt;
</div>"
                    adminlabel: "Sekura Babilejo"
                }
            }
        }

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;p class=&quot;lead&quot;&gt;
La ĉefa ideo malantaŭ Shift estas establi novan formon de valoro-interŝanĝo per unika virtuala likvaĵo, kiun homoj kolektas. 
Tiu ĉi virtuala likvaĵo, konata kiel Liquid Micro Coins (LMC), revolucias la manieron, laŭ kiu ni interagas, dividas kaj provizas 
servojn en nia reto. Per LMC, ni povas senpena esprimi dankon, pagi por servoj kaj kontribui al la komunumo.
&lt;/p&gt;
&lt;h3&gt;Celo&lt;/h3&gt;
&lt;p class=&quot;lead&quot;&gt;
Unu el la gravaj avantagoj de LMC estas ĝia imuneco al tradiciaj financaj limigoj. Ĝi ne estas mono en si mem, sed nova formo de 
valoro kiu estas imuna al impostado. Ĝi estas virtuala likvaĵo, simbolo de nia reciproka aprezado, kreita kaj uzata de la komunumo. 
Aliĝu al ni por krei pli egalan kaj liberan socian reton, kiu valoras kontribuon kaj partoprenon. Kun Shift, ni kune ŝanĝos la 
dinamikon de interreta interago!
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
			&lt;h4&gt;&lt;strong&gt;Ni estas&lt;/strong&gt;&lt;/h4&gt;
			&lt;p class=&quot;lead&quot;&gt;
				kreante la ŝanĝon...
			&lt;/p&gt;
		&lt;/div&gt;
	&lt;/div&gt;
&lt;/div&gt;
</div>"
                    adminlabel: "Paralakso"
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
                    src: "preview-en.jpg"
                    animation: "slideInLeft"
                    animation_type: "Eniroj Glitado"
                }
            }

            Column {
                span: 8

                Text {
                    text: "&lt;h3&gt;Unua Aplikaĵo por Android&lt;/h3&gt;
<p class=&quot;lead&quot;>
La unua aplikaĵo por Android preskaŭ estas preta kaj estos publikigita baldaŭ.
</p>
<h4>Fazo I</h4>
<p>
Ni kreis tre simplan aplikaĵon, kiu permesas al vi kolekti novan likvon.
Kun la aplikaĵo, vi kolektas 10.000 ml (10 litroj) da LMC ĉiutage, kiam vi startas la proceson en la aplikaĵo.
Vi ankaŭ povas inviti aliajn homojn kaj kolekti 1.500 ml (1,5 litroj) da LMC por ĉiu rekomendita uzanto ĉiutage.
Se ili ankaŭ invitas siajn amikojn, vi gajnas aldonan 300 ml da LMC por ĉiu tago kaj invitado.
Kaj se la amikoj de ili ankaŭ aliĝas, vi ricevas pluajn 60 ml por ĉiu el tiuj amikoj.
Tio estas nia maniero disvastigi bonan ideon rapide.
</p>
<h4>Fazo II</h4>
<p>
Kiam ni atingos 1.000.000 da uzantoj aŭ kiam ni trovos sufiĉe da homoj, kiuj financos la kostojn de la disvolvado, ni disvolvos funkcion de dankemo en la aplikaĵo.
Ĉiu uzanto tiam povos montri dankemon donante LMC de unu aplikaĵo al la alia.
Tial la uzanto povas doni la likvon, kiu estis kreita, al iu alia.
La marko de 1.000.000 incitos iujn disvolvontojn aliĝi, ĉar ili vidos, ke la homoj
volas tiun aplikaĵon kaj la disvolvontoj ricevos remuneron per LMC anstataŭ moneroj.
</p>

<h4>Fazo III</h4>
<p>
Estos funkcio de babilejo.
Ankaŭ estos maŝineto por mikro-blogado, por ke vi povu kunhavigi aferojn kaj servojn.
</p>
<h4>Fazo IV</h4>
<p>
Estos ebleco implementi kromprogramojn, por ke ni ĉiuj povu krei novan enhavon por nova tempopero.
</p>
<h4>Fazo V</h4>
<p>
Post atingi 10.000.000 da uzantoj, la kreado de likvo estos malkreskigita al 1 litro ĉi

utage,
tiel ke ĉiuj uzantoj ricevas egalan kvanton ĉiutage.
Tiam ni forigos la konekton al la servilo kaj la platformo tute funkcios descentraligite.
</p>

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
                    text: "&lt;!-- VOKO --&gt;
<div class=&quot;bs-callout text-center styleBackground&quot;>
<h3>Ĉu vi volas elŝuti la <strong>APLIKAĴON</strong> por Android kaj komenci kolekti likvon?<a href=&quot;&quot; target=&quot;_self&quot; class=&quot;btn btn-primary btn-lg&quot;>ELŜUTI - SEKVA SEMAJNO</a></h3>
</div>
<!-- /VOKO -->"
                    adminlabel: "Voko"
                }
            }
        }
    }

    Section {

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;h3&gt;Pri la likvo&lt;/h3&gt;
<p>
Kiam ni parolas pri salajroj kaj mono, ni ankaŭ parolas pri likvido. Do, kiam vi havas sufiĉe da mono, vi estas likvida.</br>
Fakte, ni ne plu volas uzi monon, ĉar en la malnova sistemo, kiel ni ĝin konis antaŭ 2020, iuj homoj misuzis monon por akumuli ĝin. Per tiu ago, ili interrompis la liberen fluantan energion. Ili kreis barilon por teni multe da mono por si mem. Ĉiuj aliaj homoj elsuferis kaj devis suferi.</br></br>

Kun LMC, ĉiuj homoj estos egalaj. Unue, ni devas pensi pri dankemo por ĉiu horo. Do, kiam ni faras ion dum unu horo por iu alia, tiu alia povas montri dankemon donante 60 litrojn da LMC.</br> Tio estas unu litro por minuto.
Do, programisto en Norvegio ricevos 60 litrojn da LMC ĉiuhore, same kiel frizisto en Barato.</br>
Ĉi tio estas ĝuste, ĉu ne?
</p>
<h3>Inflacio/Deflacio</h3>
<p>
Tiel ke ni ne superakvezi la planedon, la likvo disvaporas ĉiutage per malgranda procento ĝis ĝi tute malaperas post 7 jaroj. Tiel ĝi estas kreita, uzata, kaj detruata en plena cirklo.
</p>
<h3>Sekureco / Integreco</h3>
<p>
La loka datumbazo estas ĉifrita.
Tial estas neeble por iu ŝanĝi

ajnan daton en la loka datumbazo.
Tio ankaŭ validas pri la saldo.
Kiam ni transferas LMC inter du poŝtelefonoj, ankaŭ la datumoj estas ĉifritaj.
Tial la transferaj datumoj povas esti deĉifritaj nur per la originala aplikaĵo.</br></br>Se vi transferas sumon de LMC al falsa poŝtelefono, tio ne gravas. La persono kun la falsa poŝtelefono ne diros al vi, ke la transferto estis abortita.&lt;/br&gt;&lt;/br&gt;

Se iu volas transferi LMC al via konto, via aplikaĵo ne akceptos tiun transakcion pro 
maldekstre ĉifritaj datoj.
</p>

<h3>FAQ</h3>
<ul>
<li>
Sonas stranga kolekti likvon?</br>
Ni ne povas nomi ĝin mono. Kiam vi kreus monon, vi devus pagi impostojn ;-)
</li>
<li>
Ĉu estos versio por iPhone? </br>
Jes, kiam ni havos aliron al iPhono por testi ĝin. (kredu aŭ ne, ni havas tre malgrandan buĝeton)
</li>
<li>
Kiam vi iros en fazo II?</br>
Ni iros en fazon II post atingi 1.000.000 da homoj uzantaj la aplikaĵon. Tiam estos kritika maso
kiu akceptos LMC kiel energiinterŝanĝo. Tiam ni ankaŭ trovos disvolvontojn, kiujn ni povas remuneri per LMC.
Alia ebleco estos trovi sponsorojn por financi la kostojn de disvolvado.
</li>
</ul>"
                    adminlabel: "FAQ"
                }
            }
        }
    }
}
