# Submissão para Teste de Hard Skills da Cortex
Este projeto contém minha submissão.
A sessão de **Instruções** possui orientações de como executar o projeto.

## Tecnologias
* SpringBoot Web
* Java 8
* Gradle
* SpringBoot Cloud OpenFeign
* SpringBoot Cloud OpenFeign
* EHCache

## Instructions
Garanta que o JDK do Java 8 está instalado em sua máquina.
Após a instalação, use o Gradle Wrapper contido na aplicação para executar o projeto.

```
./gradlew bootRun
```
A aplicação deverá inicializar na porta 8080.

Acesse `http://localhost:8080/swagger-ui.html` em seu browser para acessar a especificação da API.

Instruções de como executar requisições estarão descritos no Swagger.

## Escolhas de Tecnologias

Nessa sessão irei brevemente comentar sobre algumas das escolhas de tecnologias e design de API realizadas no projeto.

### SpringBoot, Java 8 and Gradle

Após ler os requisitos do teste, o projeto proposta pareceu consistir principalmente de integrações, e pouca lógica de negócio.
Por esse motivo, optei por utilizar SpringBoot pela familiaridade e facilidade em oferecer as integrações e funcionalidades necessárias.

O projeto também se apresentou como uma boa oportunidade para utilizar a abstração de caches da Spring, algo que eu tinha o interesse de
fazer.

Sobre a versão de Java, optei pela 8 para que pudesse subir minha aplicação no ambiente de ElasticBeanstalk da AWS com mais facilidade.
Seria possível subir qualquer versão, mas a custo de dockerizar a execução. Prezando pela simplicidade e eficiência do meu tempo, não
enxerguei vantagens em usar uma versão acima da 11 que justificasse o upgrade.

### EHCache

EHCache foi escolhida simplesmente por ser uma Cache em memória com integração ao Spring bem documentada, e que me permitisse ter controle
sobre o TTL de entradas na Cache.

Vale ressaltar que a escolha foi feita pensando em um ambiente de demonstração com uma única instância da aplicação.
Em uma situação produtiva, seria interessante utilizar um Redis compartilhado entre as múltiplas instâncias. Uma proposta seria 
de hostear um em um cluster do Elasticache integrado com o environment da aplicação no ElasticBeanstalk.

### Serviços Externos

O serviço consumido pela aplicação é de uma API do Banco Central consumida pela [funcionalidade de conversão de moedas do site](https://www.bcb.gov.br/conversao).
As rotas foram obtidas inspecionando manualmente as chamadas realizadas pelo navegador, e o contrato foi inferido.

Inicialmente foi estudada a utilização das APIs expostas em https://dadosabertos.bcb.gov.br/dataset/dolar-americano-usd-todos-os-boletins-diarios/resource/ae69aa94-4194-45a6-8bae-12904af7e176,
mas a utilização se provou difícil, possivelmente por documentação incompleta e/ou falta de manutenção.

## Modelagem da API

Optei por oferecer o serviço de conversão como a obtenção de um recurso, análogo à forma como o Banco Central oferece o serviço.
Isso foi feito para que o uso de REST e o cacheamento fizesse mais sentido. Ao invés de criar (POST) um recurso (que esperaria-se que fosse
consultável posteiormente, atualizável, e removível), preferi tratá-lo como a obtenção de uma conversão que possui um valor, além de um
metadata que informa se o dado veio da cache ou não. O metadado foi incluído devido aos requisitos, embora eu não enxergue tanto propósito em sua
inclusão.

Como as moedas passadas no path da conversão se originam do próprio serviço do banco central, expus sua obtenção para o cliente da API também.
 
## Escalando

Atendendo ao exercício, fiz um diagrama com uma proposta de escalabilidade do serviço, implementando um sistema assíncrono de requisições
utilizando filas com prioridade.

![Diagrama de proposta de escalabilidade](docs/scalability-proposal.pdf)

Para atender a um sistema de requisições assíncrono enfileirado, proponho 2 filas (de forma a separar as requisições de alta prioridade),
 nesse caso SQSs, subscritas a um tópico (SNS) que recebe postagens dos consumidores do serviço. A filtragem seria realizada com base em um
atributo na mensagem que refletiria o nível de prioridade.

A aplicação implementaria um listener que escutasse as filas, de forma a atender requisições das duas prioridades.

O comportamento da aplicação ao receber uma requisição enfileirada seria análogo ao de receber uma requisição REST, 
consultando o serviço externo e cacheando o resultado.

O resultado da conversão poderia ser consultado por meio de um polling em uma rota da aplicação que só retorne resultados cacheados, ou preferencialmente,
por meio de um tópico onde seria postado o resultado, de forma a sobrecarregar a aplicação com um long-polling.

### Ressalvas

* A modelagem do serviço não favorece tanto o cacheamento, visto que é improvável que os mesmos dados exatos sejam consultados por dois requisitantes distintos 
(principalmente o valor da conversão). O serviço retornar a taxa de conversão poderia ser uma alternativa, delegando o cálculo do valor final ao requisitante.
* A consulta do resultado da conversão por um tópico é ineficiente visto que seria necessário o processamento de mensagens resultadas de outras requisições até
receber a que está aguardando.

## Passos Futuros

Passos futuros na implementação do projeto consistiriam em implementar as ressalvas descritas acima, além de concretizar os outros componentes da arquitetura
escalável proposta.

É esperado que outros passos futuros se originem de uma interação futura com avaliadores a respeito da aplicação implementada e da arquitetura proposta.
