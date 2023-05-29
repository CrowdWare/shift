import FlatSiteBuilder 2.0
import RevolutionSlider 1.0
import TextEditor 1.0
import ImageEditor 1.0

Content {
    title: "Shift"
    menu: "default-es"
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
	&lt;strong&gt;Bienvenido&lt;/strong&gt; a SHIFT 
&lt;/h1&gt;
&lt;p&gt;SHIFT es un proyecto de &lt;a href=&quot;https://www.crowdware.at&quot;&gt;CrowdWare&lt;/a&gt;&lt;/p&gt;


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
		&lt;h4&gt;&lt;strong&gt;Intercambio de Valor&lt;/strong&gt;&lt;/h4&gt;
&lt;p&gt;Estamos navegando a través de tiempos desafiantes. ¿Y si reconsideramos la forma en que valoramos y recompensamos a los demás?&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Ingreso Básico"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
	&lt;div class=&quot;box-content&quot;&gt;
		&lt;i class=&quot;fa fa-lightbulb-o&quot;&gt;&lt;/i&gt;
		&lt;h4&gt;&lt;strong&gt;Líquido&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;Estamos creando un líquido virtual llamado LMC que se puede usar para mostrar gratitud.&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Líquido"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
	&lt;div class=&quot;box-content&quot;&gt;
		&lt;i class=&quot;fa fa-users&quot;&gt;&lt;/i&gt;
		&lt;h4&gt;&lt;strong&gt;Microblogging&lt;/strong&gt;&lt;/h4&gt;
		&lt;p&gt;Sin censura. Anónimo. Sin publicidad. Sin registro. Sin mal uso de datos.&lt;/p&gt;
	&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Microblogging"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
&lt;div class=&quot;box-content&quot;&gt;
	&lt;i class=&quot;fa fa-flag-checkered&quot;&gt;&lt;/i&gt;
	&lt;h4&gt;&lt;strong&gt;Chat Seguro&lt;/strong&gt;&lt;/h4&gt;
	&lt;p&gt;También estará disponible el chat seguro. ¡No utilizamos servidores! Somos descentralizados.&lt;/p&gt;
&lt;/div&gt;
</div>"
                    adminlabel: "Chat Seguro"
                }
            }
        }

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;p class=&quot;lead&quot;&gt;
La idea principal detrás de Shift es establecer una nueva forma de intercambio de valor utilizando un líquido virtual único, recogido por las personas. Este líquido virtual, conocido como Liquid Micro Coins (LMC), revoluciona la forma en que interactuamos, compartimos y proporcionamos servicios en nuestra red. Con LMC, podemos expresar gratitud, pagar por servicios y contribuir a la comunidad sin esfuerzo.
&lt;/p&gt;
&lt;h3&gt;Meta&lt;/h3&gt;
&lt;p class=&quot;lead&quot;&gt;
Uno de los beneficios significativos de LMC es su inmunidad a las restricciones financieras tradicionales. No es dinero per se, sino una nueva forma de valor que es inmune a la fiscalidad. Es un**Spanish (cont&#x27;d):**
líquido virtual, un símbolo de nuestro agradecimiento mutuo, creado y utilizado por la comunidad. Únete a nosotros para crear una red social más equitativa y libre que valore la contribución y la participación. Con Shift, ¡cambiemos juntos la dinámica de la interacción en línea!
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
			&lt;h4&gt;&lt;strong&gt;Estamos&lt;/strong&gt;&lt;/h4&gt;
			&lt;p class=&quot;lead&quot;&gt;
				creando el cambio...
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
                    text: "&lt;h3&gt;Primera aplicación para Android&lt;/h3&gt;
<p class=&quot;lead&quot;>
La primera aplicación para Android está casi lista

y se lanzará próximamente.
</p>
<h4>Fase I</h4>
<p>
Hemos creado una aplicación muy sencilla que te permite recolectar nuevo líquido.
Con la aplicación, recolectas 10,000 ml (10 l) de LMC cada día que inicias el proceso en la aplicación.
También puedes invitar a otras personas y recolectar 1,500 ml (1.5 l) de LMC por usuario recomendado por día.
Si ellos también invitan a sus amigos, ganas 300 ml adicionales de LMC por día e invitación.
Y si los amigos de tus amigos también se unen, obtienes 60 ml adicionales por cada uno de estos amigos.
Esta es nuestra forma de difundir una buena idea rápidamente.
</p>
<h4>Fase II</h4>
<p>
Cuando alcancemos 1,000,000 de usuarios o cuando encontremos suficientes personas financiando los costos de desarrollo, desarrollaremos una función de gratitud en la aplicación.
Cada usuario podrá mostrar gratitud al dar LMC de una aplicación a otra.
Entonces, el usuario podrá dar el líquido que ha sido creado a otra persona.
La marca de 1,000,000 motivará a algunos desarrolladores a unirse, porque verán que las personas quieren esta aplicación y los desarrolladores serán pagados con LMC en lugar de dinero.
</p>

<h4>Fase III</h4>
<p>
Habrá una función de chat.
También habrá un motor de microblogging para que puedas compartir cosas y servicios.
</p>
<h4>Fase IV</h4>
<p>
Habrá posibilidad de implementar complementos, para que todos podamos crear nuevo contenido para un nuevo cambio de tiempo.
</p>
<h4>Fase V</h4>
<p>
Después de alcanzar la cantidad de 10,000,000 de usuarios, la creación de líquido se reducirá a 1 litro al día, para que todos los usuarios reciban la misma cantidad diaria.
En ese momento, cortaremos la conexión con el servidor y la plataforma funcionará completamente de manera descentralizada.
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
                    text: "&lt;!-- CALLOUT --&gt;
<div class=&quot;bs-callout text-center styleBackground&quot;>
<h3>¿Quieres obtener la <strong>APP</strong> para Android y comenzar a recolectar líquido?<a href=&quot;&quot; target=&quot;_self&quot; class=&quot;btn btn-primary btn-lg&quot;>DESCARGAR - PRÓXIMA SEMANA</a></h3>
</div>
<!-- /CALLOUT -->"
                    adminlabel: "llamada"
                }
            }
        }
    }

    Section {

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;h3&gt;Acerca del líquido&lt;/h3&gt;
<p&gt

;
Cuando hablamos de salarios y dinero, también hablamos de liquidez. Entonces, cuando tienes suficiente dinero, eres líquido.</br>
De hecho, ya no queremos usar dinero, porque en el antiguo sistema, como lo conocíamos antes de 2020, algunas personas abusaron del dinero para acumularlo. Con esta acción, detuvieron el flujo libre de energía. Crearon un bloqueo para guardar mucho dinero para ellos mismos. Todas las demás personas se quedaron sin nada y tuvieron que sufrir.</br></br>

Con LMC, todas las personas serán iguales. En primer lugar, debemos pensar en la gratitud por cada hora. Entonces, cuando hacemos algo durante una hora por alguien más, esa persona puede mostrar gratitud dando 60 litros de LMC.</br> Es decir, un litro por minuto.
Entonces, un desarrollador de software en Noruega recibirá 60 litros de LMC cada hora y lo mismo sucederá con un peluquero en India.</br>
Justo y equitativo, ¿no crees?
</p>
<h3>Inflación/Deflación</h3>
<p>
Para no inundar el planeta, el líquido se evaporará diariamente en un pequeño porcentaje hasta que desaparezca por completo después de 7 años. De esta manera, se crea, se usa y se destruye en un ciclo completo.
</p>
<h3>Seguridad / Integridad</h3>
<p>
La base de datos local ha sido encriptada.
Esto hace que sea imposible que alguien cambie cualquier dato en la base de datos local.
Lo cual, por supuesto, también se aplica al saldo.
Cuando transferimos LMC entre dos teléfonos móviles, también se encriptarán los datos.
Por lo tanto, estos datos de transferencia solo se pueden desencriptar con la aplicación original.</br></br>
Si transfieres una cantidad de LMC a una aplicación móvil falsa, no importa. La persona con la aplicación falsa no te dirá que la transferencia se ha abortado.&lt;/br&gt;&lt;/br&gt;

Si alguien quiere transferir LMC a tu cuenta, entonces tu aplicación no aceptará esta transferencia debido a datos desencriptados incorrectos.
</p>

<h3>Preguntas frecuentes</h3>
<ul>
<li>
¿Suena extraño que recolectemos líquido?</br>
No podemos llamarlo dinero. Cuando creas dinero, tendrías que pagar impuestos ;-)
</li>
<li>
¿Habrá una versión para iPhone? </br>
Sí, cuando tengamos acceso a un iPhone para probarlo. (Aunque no lo creas, tenemos un presupuesto muy limitado)
</li>
<li>
¿Cuándo pasaremos a la fase II?</br>
Pasaremos a la fase II después de alcanzar 1,000,000 de personas que utilicen la aplicación. Entonces hab

rá una masa crítica que aceptará LMC como intercambio de energía. También encontraremos desarrolladores a quienes podamos pagar con LMC. Otra posibilidad será encontrar patrocinadores para financiar los costos de desarrollo.
</li>
</ul>"
                    adminlabel: "Preguntas frecuentes"
                }
            }
        }
    }
}
