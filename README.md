<h1 align="center" font-style="bold">
    <img width="584" alt="imagem header" src="https://user-images.githubusercontent.com/81285428/161086434-d0770f3d-6ff5-4636-801c-3f294d3908b6.png"><br>
Track History - API
</h1>

### URL: [track-history.herokuapp.com](https://track-history.herokuapp.com/)

## 💻 Sobre o projeto

- <p style="color: red;">A Bolinho de Java Corp teve como desafio desenvolver uma aplicação para manter todo o histórico de telemetria de um entregador para um determinado pedido.</p>

Para ver o **Front-end**, clique aqui: [Frontend Track History iFood](https://github.com/GeraldinJr/FrontendTrackHistory-iFood)

## 👨🏻‍💻 Desenvolvedores

- [Debora Brum](https://github.com/DeboraBrum)
- [Edvan Jr.](https://github.com/Edvan-Jr)
- [Geraldo Jr.](https://github.com/GeraldinJr)
- [Lucas Paixão](https://github.com/lucasfpds)
- [Magnólia Medeiros](https://github.com/magnoliamedeiros)

## 💡 Mentor

- João Lello

## 🚀 Tecnologias

Tecnologias que utilizamos para desenvolver esta API Rest:

- [Java](https://www.java.com/pt-BR/)
- [Spring](https://spring.io/)
- [PostgreSQL](https://www.postgresql.org/)
- [Swagger](https://swagger.io/)

## ▶️ Iniciando

- As instruções a seguir irão te guiar para que você crie uma cópia do projeto na sua máquina local.

### Pré-requisitos

- Configure um banco de dados [PostgreSQL](https://www.postgresql.org/) na sua máquina e crie um novo banco.

**Clone o projeto, e acesse a pasta**

```bash
$ git clone https://github.com/GeraldinJr/BackendTrackHistory-iFood && cd BackendTrackHistory-iFood
```

**Siga as etapas abaixo**

Edite o arquivo "./src/main/resources/application.properties" com as configurações do seu banco de dados:

```
spring.datasource.username = seu_usuario
spring.datasource.password = sua_senha
spring.datasource.url = jdbc:postgresql://localhost:5432/nome_do_seu_banco
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQL10Dialect
spring.jpa.show_sql = true
springdoc.api-docs.path=/api-docs
```

E rode o projeto na sua IDE.

Tudo pronto! Agora, para uma visão geral da API, basta acessar http://localhost:8080/swagger-ui.html, onde você encontra a documentação de todos os endpoints disponíveis com índice e descrição, numa interface amigável do Swagger, além de poder utiliza-los com requisições e repostas, interagindo com o seu banco de dados local.

No início do projeto, estávamos desenvolvendo a documentação manualmente aqui no README, até termos a ideia de gerá-la automaticamente com o swagger. Ainda assim, mantivemos a documentação manual aqui também no final do README, como registro da mudança, e como outro exemplo de abordagem.

Para acessar a API diretamente no seu browser, acesse https://herokuapp.com/



## ⚙️ Funcionalidades

Funcionalidades que a API oferece:
- Cadastro e Login de Pessoa Entregadora (User)
- Criptografia de senhas
- Autenticação de pessoa entregadora
- Validação de requisições
- Mensagens de erro customizadas *user friendly*
- Paginação dos dados desde o banco de dados
- Verificação se a pessoa entregadora já possui pedido atribuído EM_ROTA, em caso de reinícios aleatórios da aplicação
- Listagem de todos os pedidos (com todos os status)
- Listagem de todos os pedidos com status EM_ABERTO
- Atribuição do pedido à pessoa entregadora (com consequente alteração do status de EM_ABERTO para EM_ROTA)
- Registro de geolocalização
- Recuperação da última geolocalização do pedido
- Listagem de todas as geolocalizações do pedido
- Conclusão do pedido, com alteração do status de EM_ROTA para CONCLUIDO
- Cancelamento do pedido, com alteração do status de EM_ROTA para EM_ABERTO, e o pedido retornando à lista de pedidos em aberto, disponível para nova tentativa de entrega
- Cancelamento definitivo do pedido, quando este se encontra há muito tempo (no nosso caso, arbitrariamente 30 minutos) sem atualização da geolocalização, com status alterado para CANCELADO
- Documentação dos endpoints integrada ao código e automatizada, através da springdoc-openapi-ui, o que também facilita atualização.

## 📄 Licença

Este projeto está sob a licença de Bolinho de Java Corp.

### Endpoints:

#### - POST: /pessoa-entregadora/login

Este endpoint faz a autenticação da pessoa entregadora já cadastrada na base de dados e retorna um token de autenticação para acesso aos endpoints protegidos.

##### Requisição:

```json
{
    "email": "pessoa@email.com",
    "senha": "senhaDaPessoa"
}
```
##### Resposta:

###### Em caso de sucesso, a seguinte resposta será obtida (código `200`):

```json
{
    "token": "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJHZXJhbGRvIiwiaXNzIjoiUHJvZklzaWRyb0lmb29kIiwiZXhwIjoxNjQ3NDY2NTMxfQ.yFfygoULOM3vkYU0ZlwhKeNenTPU-wPHyQPZepE8wao",
    "nome": "Pessoa"
}
```

###### Em caso de email e/ou senha incorreta(s) (código `401`):

```json
{
    "message": "E-mail e/ou senha incorreto(s)"
}
```

###### Em caso de ausência do email e/ou senha (código `400`):

```json
{
    "message": "Favor, informar e-mail e senha"
}
```

#### - POST: /pessoa-entregadora/cadastro

Este endpoint cadastra uma nova pessoa entregadora na base de dados.

##### Requisição:

```json
{
    "email": "pessoa@email.com",
    "nome": "pessoa entregadora",
    "senha": "senhaDaPessoa",
    "confirmacao_senha": "senhaDaPessoa"
}
```

Validações:

* Validação de e-mail
* A senha deve ter no mínimo 6 caracteres
* Valida a confirmação de senha
* Verifica se já possui cadastro com o mesmo e-mail no banco de dados
* Todos os campos são obrigatórios

##### Resposta:

###### Em caso de sucesso, a seguinte resposta será obtida (código `201`):

```json
{
    "id": 1,
    "email": "pessoa@email.com",
    "nome": "pessoa entregadora"
}
```

###### Em caso de algum erro na requisição (como e-mail já existente ou problema com a senha) (código `40X`):

```json
{
    "message": "menssagem personalizada aqui"
}
```

**A partir daqui, todas os endpoints exigem autenticação.**

#### - GET: /pedidos?numeroPagina=1&tamanhoPagina=10

Este endpoint deve fornecer os dados com uma paginação, onde a página e o tamanho da mesma deve ser informado na URL como mostra o exemplo. Caso não sejam informados, o padrão é retornar a primeira página com o seu tamanho limitado a 10 elementos.

##### Resposta:

###### Em caso de sucesso, a seguinte resposta será obtida (código `200`):

```json
{
    "paginaAtual": 1,
    "tamanhoPagina": 10,
    "totalPaginas": 5,
    "totalPedidos": 46,
    "pedidos": []
}
```

#### - GET: /pedidos/em-aberto?numeroPagina=1&tamanhoPagina=10

Este endpoint deve fornecer os dados com uma paginação, onde a página e o tamanho da mesma deve ser informado na URL como mostra o exemplo. Caso não sejam informados, o padrão é retornar a primeira página com o seu tamanho limitado a 10 elementos.

##### Resposta:

###### Em caso de sucesso, a seguinte resposta será obtida (código `200`):

```json
{
    "paginaAtual": 1,
    "tamanhoPagina": 10,
    "totalPaginas": 5,
    "totalPedidos": 46,
    "pedidos": []
}
```

#### - POST: /pedidos/id/atribuir-pedido

Este endpoint atribui um pedido (necessariamente com o status 'EM_ABERTO') à pessoa entregadora, quando esta inicia uma entrega, consequentemente alterando o status do pedido para 'EM_ROTA'.

##### Requisição:


```json
{
    "latitude": -12.8479257,
    "longitude": -38.4623286
}
```

###### Em caso de sucesso, a seguinte resposta será obtida (código `200`): **No body**


###### Em caso de algum problema, a seguinte resposta será obtida (código `40X`):

```json
{
    "message": "Menssagem personalizada aqui"
}
```

Validações:

* Só é possível atribuir pedido com status **EM_ABERTO**

Procedimento:
1. Verifica se o status do pedido está em aberto antes de prosseguir
2. Altera o status para EM_ROTA
3. Faz o devido registro no track_history

#### - POST: /pedidos/id/geolocalizacao

Este endpoint faz o registro da geolocalização atual do pedido.

##### Requisição:


```json
{
    "latitude": -12.8479257,
    "longitude": -38.4623286
}
```

###### Em caso de sucesso, a seguinte resposta será obtida (código `200`):

```json
{
    "latitude": -12.8479257,
    "longitude": -38.4623286,
    "instante": 1648123652533
}
```

###### Em caso de geolocalização inválida, a seguinte resposta será obtida (código `400`):

```json
{
    "message": "Menssagem personalizada aqui"
}
```

#### - GET: /pedidos/id/trackings

Este endpoint retorna o histórico de geolocalização do pedido no formato indicado abaixo:

###### Em caso de sucesso, a seguinte resposta será obtida (código `200`):

```json
{
    "data":[{ "latitude": -12.8479257,
              "longitude": -38.4623286,
              "instante": 1648123652533},
            { "latitude": -12.8479257,
              "longitude": -38.4623286,
              "instante": 1648123652533},
            {},{}],
    "status_pedido": "EM_ROTA"
}
```
###### Em caso de pedido em aberto, a seguinte resposta será obtida (código `404`):

```json
{
    "message": "Pedido em Aberto"
}
```

#### - GET: /pedidos/id/geolocalizacao

Este endpoint retorna a última geolocalização registrada do pedido no formato indicado abaixo:

###### Em caso de sucesso, a seguinte resposta será obtida (código `200`):


```json
{
    "latitude": -12.8479257,
    "longitude": -38.4623286,
    "instante": 1648123652533
}
```

###### Caso nenhum registro seja encontrado, a seguinte resposta será obtida (código `404`):

```json
{
    "message": "Nenhum registro de geolocalização foi encontrado"
}
```



#### - PATCH: /pedidos/id/concluir

Este endpoint deve indicar que o pedido foi concluído. Para isto, o status do pedido deve estar 'EM_ROTA', o qual consequentemente é alterado para 'CONCLUÍDO'.



##### Requisição:

```json
{
    "latitude": -12.8479257,
    "longitude": -38.4623286
}
```



###### Em caso de sucesso, a seguinte resposta será obtida (código `200`):

```json
{
    "message": "Pedido concluído com sucesso"
}
```

###### Em caso de insucesso, a seguinte resposta será obtida (código `40X`):

```json
{
    "message": "Menssagem personalizada aqui"
}
```

Validações:

* O pedido para ser concluído precisa ter o status **EM_ROTA**
* A pessoa entregadora logada precisa ser a mesma atríbuída no último registro de geolocalização.

#### - PATCH: /pedidos/id/cancelar

Este endpoint deve alterar o status de um pedido de 'EM_ROTA' para 'CANCELADO', isto é, somente pedidos em rota podem ser cancelados.

##### Requisição:

```json
{
    "latitude": -12.8479257,
    "longitude": -38.4623286
}
```



###### Em caso de sucesso, a seguinte resposta será obtida (código `200`):

```json
{
    "message": "Entrega cancelada com sucesso"
}
```

###### Em caso de status inválido, a seguinte resposta será obtida (código `400`):

```json
{
    "message": "Menssagem personalizada aqui"
}
```
Validações:

* O pedido para ser cancelado precisa ter o status **EM_ROTA**
* A pessoa entregadora logada precisa ser a mesma atríbuída no último registro de geolocalização.

#### - GET: /pedidos/em-aberto

Este endpoint retorna apenas os pedidos em aberto.

###### Em caso de sucesso, a seguinte resposta será obtida (código `200`):


```json
{
  "paginaAtual": 1,
  "tamanhoPagina": 10,
  "totalPaginas": 2,
  "totalPedidos": 11,
  "pedidos": [
    
  ]
}
```
    
#### - GET: /pessoa-entregadora/possui_pedido

Este endpoint serve para o caso de a pessoa entregadora fechar a aplicação aleatoriamente sem fazer logout, e ao retornar à aplicação ela ser redirecionada para a página da entrega em andamento, ao invés de lhe ser permitido selecionar outro pedido, antes de concluir o anterior.

###### Em caso de já haver pedido em aberto atribuído à pessoa entregadora, ela é redirecionada para a página de rastreamento do pedido.