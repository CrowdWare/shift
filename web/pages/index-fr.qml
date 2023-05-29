import FlatSiteBuilder 2.0
import RevolutionSlider 1.0
import TextEditor 1.0
import ImageEditor 1.0

Content {
    title: "Shift"
    menu: "default-fr"
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
	&lt;strong&gt;Bienvenue&lt;/strong&gt; dans SHIFT
&lt;/h1&gt;
&lt;p&gt;SHIFT est un projet de &lt;a href=&quot;https://www.crowdware.at&quot;&gt;CrowdWare&lt;/a&gt;&lt;/p&gt;


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
		&lt;h4&gt;&lt;strong&gt;Échange de Valeurs&lt;/strong&gt;&lt;/h4&gt;
&lt;p&gt;Nous naviguons à travers des temps difficiles. Et si nous repensions la façon dont nous valorisons et récompensons les uns les autres?&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Revenu de base"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
	&lt;div class=&quot;box-content&quot;&gt;
		&lt;i class=&quot;fa fa-lightbulb-o&quot;&gt;&lt;/i&gt;
		&lt;h4&gt;&lt;strong&gt;Liquid&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;Nous créons un liquide virtuel appelé LMC qui peut être utilisé pour montrer de la gratitude.&lt;/p&gt;
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
		&lt;h4&gt;&lt;strong&gt;Micro-blogging&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;Pas de censure. Anonyme. Pas de publicité. Pas d'inscription. Pas d'utilisation abusive des données.&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Micro-blogging
"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
&lt;div class=&quot;box-content&quot;&gt;
	&lt;i class=&quot;fa fa-flag-checkered&quot;&gt;&lt;/i&gt;
	&lt;h4&gt;&lt;strong&gt;Chat sécurisé&lt;/strong&gt;&lt;/h4&gt;
	&lt;p&gt;La messagerie sécurisée sera également disponible. Nous n'utilisons pas de serveurs ! Nous sommes décentralisés.&lt;/p&gt;
&lt;/div&gt;
</div>"
                    adminlabel: "Chat sécurisé"
                }
            }
        }

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;p class=&quot;lead&quot;&gt;
L&#x27;idée principale derrière Shift est d&#x27;établir une nouvelle forme d&#x27;échange de valeur utilisant un liquide virtuel unique, puisé par les gens. Ce liquide virtuel, connu sous le nom de Liquid Micro Coins (LMC), révolutionne la façon dont nous interagissons, partageons et fournissons des services dans notre réseau. Avec LMC, nous pouvons exprimer notre gratitude, payer pour des services et contribuer à la communauté en toute simplicité.
&lt;/p&gt;
&lt;h3&gt;Objectif&lt;/h3&gt;
&lt;p class=&quot;lead&quot;&gt;
L&#x27;un des avantages significatifs du LMC est son immunité face aux contraintes financières traditionnelles. Ce n&#x27;est pas de l&#x27;argent en soi, mais une nouvelle forme de valeur qui est à l&#x27;abri de la fiscalité. C&#x27;est un liquide virtuel, un symbole de notre appréciation mutuelle, créé et utilisé par la communauté. Rejoignez-nous pour créer un réseau social plus équitable et libre qui valorise la contribution et la participation. Avec Shift, changeons ensemble la dynamique de l&#x27;interaction en ligne !
&lt;/p&gt;"
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
			&lt;h4&gt;&lt;strong&gt;Nous créons&lt;/strong&gt;&lt;/h4&gt;
			&lt;p class=&quot;lead&quot;&gt;
				le changement...
			&lt;/p&gt;
		&lt;/div&gt;
	&lt;/div&gt;
&lt;/div&gt;

</div>"
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
                    src: "preview-en.jpg"
                    animation: "slideInLeft"
                    animation_type: "Sliding Entrances"
                }
            }

            Column {
                span: 8

                Text {
                    text: "&lt;h3&gt;Première application pour Android&lt;/h3&gt;

&lt;p class=&quot;lead&quot;&gt;
La première application pour Android est presque prête et sera bientôt disponible.
&lt;/p&gt;
&lt;h4&gt;Phase I&lt;/h4&gt;
&lt;p&gt;
Nous avons créé une application très simple qui vous permet de collecter un nouveau fluide.
Avec l&#x27;application, vous collectez 10 000 ml (10 l) de LMC chaque jour où vous lancez le processus dans l&#x27;application.
Vous pouvez également inviter d&#x27;autres personnes et collecter 1 500 ml (1,5 l) de LMC par utilisateur recommandé par jour.
S&#x27;ils invitent également leurs amis, vous gagnez 300 ml supplémentaires de LMC par jour et par invitation.
Et si leurs amis se joignent également, vous obtenez 60 ml supplémentaires pour chacun de ces amis.
C&#x27;est notre façon de propager rapidement une bonne idée.
&lt;/p&gt;
&lt;h4&gt;Phase II&lt;/h4&gt;
&lt;p&gt;
Lorsque nous atteindrons 1 000 000 d&#x27;utilisateurs ou lorsque nous trouverons suffisamment de personnes pour financer les coûts de développement, nous développerons une fonction de gratitude dans l&#x27;application.
Chaque utilisateur pourra alors montrer sa gratitude en donnant du LMC d&#x27;une application à l&#x27;autre.
Ainsi, l&#x27;utilisateur pourra donner le liquide qui a été créé à quelqu&#x27;un d&#x27;autre.
Le seuil de 1 000 000 encouragera certains développeurs à se joindre, car ils verront que les utilisateurs veulent cette application et les développeurs seront rémunérés en LMC plutôt qu&#x27;en argent.
&lt;/p&gt;

&lt;h4&gt;Phase III&lt;/h4&gt;
&lt;p&gt;
Il y aura une fonction de chat.
Il y aura également un moteur de micro-blogging, vous permettant de partager des choses et des services.
&lt;/p&gt;
&lt;h4&gt;Phase IV&lt;/h4&gt;
&lt;p&gt;
Il sera possible d&#x27;implémenter des plugins, afin que nous puissions tous créer du nouveau contenu pour une nouvelle ère.
&lt;/p&gt;
&lt;h4&gt;Phase V&lt;/h4&gt;
&lt;p&gt;
Après avoir atteint 10 000 000 d&#x27;utilisateurs, la création de liquide sera réduite à 1 litre par jour, de sorte que tous les utilisateurs reçoivent la même quantité par jour.
À ce stade, nous couperons la connexion au serveur et la plateforme fonctionnera totalement de manière décentralisée.
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
<div class=&quot;bs-callout text-center styleBackground&quot;>
<h3>Voulez-vous obtenir l'application pour Android et commencer à collecter du liquide ? <a href=&quot;&quot; target=&quot;_self&quot; class=&quot;btn btn-primary btn-lg&quot;>TÉLÉCHARGER - LA SEMAINE PROCHAINE</a></h3>
</div>
<!-- /CALLOUT -->"
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
                    text: "&lt;h3&gt;À propos du liquide&lt;/h3&gt;
<p>
Quand nous parlons de salaires et d'argent, nous parlons également de liquidité. Donc lorsque vous avez assez d'argent, vous êtes liquide.</br>
En fait, nous ne voulons plus utiliser d'argent, car dans l'ancien système tel que nous le connaissions avant 2020, certaines personnes ont abusé de l'argent pour l'accumuler. Par cette action, elles ont bloqué le flux libre d'énergie. Ainsi, elles ont créé un blocage pour garder beaucoup d'argent pour elles-mêmes. Toutes les autres personnes se sont retrouvées sans ressources et ont dû souffrir.</br></br>

Avec LMC, tous les êtres humains seront égaux. Tout d'abord, nous devons penser à la gratitude pour chaque heure. Ainsi, lorsque nous faisons quelque chose pendant une heure pour quelqu'un d'autre, cette personne peut montrer sa gratitude en donnant 60 litres de LMC.</br> Soit un litre par minute.
Ainsi, un développeur de logiciels en Norvège recevra 60 litres de LMC chaque heure, tout comme un coiffeur en Inde.</br>
Juste équitable, n'est-ce pas ?
</p>
<h3>Inflation/Déflation</h3>
<p>
Afin de ne pas inonder la planète, le liquide s'évapore chaque jour d'un petit pourcentage jusqu'à ce qu'il disparaisse complètement après 7 ans. Ainsi, il est créé, utilisé et détruit dans un cycle complet.
</p>
<h3>Sécurité / Intégrité</h3>
<p>
La base de données locale a été cryptée.
Il est donc impossible pour quelqu'un de modifier des données dans la base de données locale.
Cela vaut bien sûr aussi pour le solde.
Lorsque nous transférons du LMC entre deux téléphones mobiles, les données sont également cryptées.
Ainsi, ces données de transfert ne peuvent être déchiffrées qu'avec l'application d'origine.</br></br>
Si vous transférez une quantité de LMC vers une application mobile falsifiée, cela n'a pas d'importance. Le propriétaire de l'application falsifiée ne vous dira pas que le transfert a été interrompu.&lt;/br&gt;&lt;/br&gt;

Si quelqu'un souhaite transférer du LMC sur votre compte, votre application n'acceptera pas ce transfert en raison de données déchiffrées incorrectes.
</p>

<h3>FAQ</h3>
<ul>
<li>
Cela semble étrange de collecter du liquide ?</br>
Nous ne pouvons pas appeler ça de l'argent. Lorsque vous créez de l'argent, vous seriez obligé de payer des impôts ;-)
</li>
<li>
Y aura

-t-il une version pour iPhone ? </br>
Oui, lorsque nous aurons un iPhone pour le tester. (croyez-le ou non, nous avons un budget très limité)
</li>
<li>
Quand passerez-vous à la phase II ?</br>
Nous passerons à la phase II après avoir atteint 1 000 000 de personnes utilisant l'application. À ce moment-là, il y aura une masse critique
qui acceptera le LMC comme moyen d'échange d'énergie. Nous trouverons également des développeurs que nous pourrons rémunérer en LMC.
Une autre possibilité serait de trouver des sponsors pour financer les coûts de développement.
</li>
</ul>"
                    adminlabel: "FAQ"
                }
            }
        }
    }
}
