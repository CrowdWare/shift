import FlatSiteBuilder 2.0
import RevolutionSlider 1.0
import TextEditor 1.0
import ImageEditor 1.0

Content {
    title: "Shift"
    menu: "default-pt"
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
&lt;strong&gt;Bem-vindo&lt;/strong&gt; ao SHIFT
&lt;/h1&gt;
&lt;p&gt;SHIFT é um projeto da &lt;a href=&quot;https://www.crowdware.at&quot;&gt;CrowdWare&lt;/a&gt;&lt;/p&gt;"
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
	&lt;h4&gt;&lt;strong&gt;Troca de Valor&lt;/strong&gt;&lt;/h4&gt;
&lt;p&gt;Estamos navegando por tempos desafiadores. E se repensarmos a maneira como valorizamos e recompensamos uns aos outros?&lt;/p&gt;
&lt;/div&gt;
&lt;/div&gt;"
                    adminlabel: "Rendimento Básico"
                }
            }

            Column {
                span: 3

                Text {
                    text: "&lt;div class=&quot;featured-box nobg border-only&quot;&gt;
&lt;div class=&quot;box-content&quot;&gt;
	&lt;i class=&quot;fa fa-lightbulb-o&quot;&gt;&lt;/i&gt;
	&lt;h4&gt;&lt;strong&gt;Líquido&lt;/strong&gt;&lt;/h4&gt;
	&lt;p&gt;Estamos criando um líquido virtual chamado LMC, que pode ser usado para mostrar gratidão.&lt;/p&gt;
&lt;/div&gt;
</div>"
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
	&lt;p&gt;Sem censura. Anônimo. Sem anúncios. Sem registro. Sem uso indevido de dados.&lt;/p&gt;
&lt;/div&gt;
</div>"
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
	&lt;p&gt;Também será disponibilizado um chat seguro. Não usamos servidores! Somos descentralizados.&lt;/p&gt;
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
A ideia principal por trás de Shift é estabelecer uma nova forma de troca de valor usando um líquido virtual único, coletado pelas pessoas. Este líquido virtual, conhecido como Liquid Micro Coins (LMC), revoluciona a maneira como interagimos, compartilhamos e fornecemos serviços em nossa rede. Com LMC, podemos expressar gratidão, pagar por serviços e contribuir para a comunidade sem esforço.
&lt;/p&gt;
&lt;h3&gt;Meta&lt;/h3&gt;
&lt;p class=&quot;lead&quot;&gt;
Um dos principais benefícios do LMC é a sua imunidade às restrições financeiras tradicionais. Não é dinheiro em si, mas uma nova forma de valor que está imune à tributação. É um líquido virtual, um símbolo de nosso apreço mútuo, criado e usado pela comunidade. Junte-se a nós para criar uma rede social mais equitativa e livre que valoriza a contribuição e a participação. Com Shift, vamos mudar a dinâmica da interação online juntos!
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
				criando a mudança...
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
                    animation_type: "Entradas Deslizantes"
                }
            }

            Column {
                span: 8

                Text {
                    text: "&lt;h3&gt;Primeiro Aplicativo para Android&lt;/h3&gt;
<p class=&quot;lead&quot;>
O primeiro aplicativo para Android está quase pronto e será lançado em breve.
</p>
<h4>Fase I</h4>
<p>
Criamos um aplicativo muito simples que permite coletar um novo líquido.
Com o aplicativo, você coleta 10.000 ml (10 litros) de LMC todos os dias que inicia o processo no aplicativo.
Você também pode convidar outras pessoas e coletar 1.500 ml (1,5 litros) de LMC por usuário recomendado por dia.
Se eles também convidarem seus amigos, você ganha 300 ml extras de LMC por dia e convite.
E se os amigos deles também participarem, você recebe mais 60 ml para cada um desses amigos.
Esta é a nossa maneira de espalhar uma boa ideia rapidamente.
</p>
<h4>Fase II</h4>
<p>
Quando atingirmos 1.000.000 de usuários ou quando encontrarmos pessoas suficientes financiando os custos de desenvolvimento, iremos desenvolver uma função de gratidão no aplicativo.
Todo usuário poderá mostrar gratidão dando LMC de um aplicativo para outro.
Então, o usuário poderá dar o líquido que foi criado para outra pessoa.
A marca de 1.000.000 motivará alguns desenvolvedores a se juntarem, pois eles verão que as pessoas
desejam esse aplicativo e os desenvolvedores serão remunerados com LMC em vez de dinheiro.
</p>

<h4>Fase III</h4>
<p>
Haverá uma função de chat.
Também haverá um mecanismo de microblogging para que você possa compartilhar coisas e serviços.
</p>
<h4>Fase IV</h4>
<p>
Haverá a possibilidade de implementar plugins, para que todos nós possamos criar novo conteúdo para uma nova mudança de tempo.
</p>
<h4>Fase V</h4>
<p>
Após atingir a marca de 10.000.000 de usuários, a criação de líquido será reduzida para 1 litro por dia,
para que todos os usuários recebam a mesma quantidade por dia.
Nesse ponto, cortaremos a conexão com o servidor e a plataforma funcionará totalmente descentralizada.
</p>"
                }
            }
        }
    }

    Section {

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;!-- DESTAQUE --&gt;
<div class=&quot;bs-callout text-center styleBackground&quot;>
<h3>Você quer obter o <strong>APLICATIVO</strong> para Android e começar a coletar líquido?<a href=&quot;&quot; target=&quot;_self&quot; class=&quot;btn btn-primary btn-lg&quot;>BAIXAR - PRÓXIMA SEMANA</a></h3>
</div>
<!-- /DESTAQUE -->"
                    adminlabel: "Destaque"
                }
            }
        }
    }

    Section {

        Row {

            Column {
                span: 12

                Text {
                    text: "&lt;h3&gt;Sobre o líquido&lt;/h3&gt;
<p>
Quando falamos sobre salários e dinheiro, também falamos sobre liquidez. Então, quando você tem dinheiro suficiente, você está líquido.</br>
Na verdade, não queremos mais usar dinheiro, porque no antigo sistema como conhecíamos antes de 2020, algumas pessoas abusavam do dinheiro para acumulá-lo. Com essa ação, elas interrompiam o fluxo livre de energia. Então, elas criavam um bloqueio para manter muito dinheiro para si mesmas. Todas as outras pessoas ficavam sem recursos e tinham que sofrer.</br></br>

Com o LMC, todos os seres humanos serão iguais. Em primeiro lugar, devemos pensar em gratidão por cada hora. Então, quando fazemos algo por uma hora para outra pessoa, essa pessoa pode mostrar gratidão dando 60 litros de LMC.</br> Ou seja, um litro por minuto.
Portanto, um desenvolvedor de software na Noruega receberá 60 litros de LMC a cada hora, assim como um cabeleireiro na Índia.</br>
Apenas justo, não é mesmo?
</p>
<h3>Inflação/Deflação</h3>
<p>
Para não inundarmos o planeta, o líquido evaporará diariamente uma pequena porcentagem até desaparecer completamente após 7 anos. Assim, ele será criado, usado e destruído em um ciclo completo.
</p>
<h3>Segurança / Integridade</h3>
<p>
O banco de dados local foi criptografado.
Portanto, isso torna impossível que alguém altere quaisquer dados no banco de dados local.
O mesmo é verdade para o saldo.
Quando estamos transferindo LMC entre dois telefones celulares, os dados também serão criptografados.
Portanto, esses dados de transferência só podem ser decifrados com o aplicativo original.</br></br>
Se você transferir uma quantidade de LMC para um aplicativo móvel falso, não importa. A pessoa com o aplicativo móvel falso não lhe dirá que a transferência foi interrompida.&lt;/br&gt;&lt;/br&gt;

Se alguém quiser transferir LMC para sua conta, seu aplicativo não aceitará essa transferência devido 
a dados decifrados incorretos.
</p>

<h3>FAQ</h3>
<ul>
<li>
Parece estranho coletar líquido?</br>
Não podemos chamar de dinheiro. Quando você cria dinheiro, você teria que pagar impostos ;-)
</li>
<li>
Haverá uma versão para iPhone? </br>
Sim, quando tivermos acesso a um iPhone para testá-lo. (acredite ou não, estamos com um orçamento muito baixo)
</li>
<

li>
Quando você entrará na fase II?</br>
Iremos para a fase II após alcançarmos 1.000.000 de pessoas usando o aplicativo. Então, haverá uma massa crítica
que aceitará o LMC como troca de energia. Então também encontraremos desenvolvedores que possamos pagar com LMC.
Outra possibilidade é encontrarmos patrocinadores para financiar os custos de desenvolvimento.
</li>
</ul>"
                    adminlabel: "FAQ"
                }
            }
        }
    }
}
