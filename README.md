# Track History - API [EM CONSTRUÇÃO]

## URL: [track-history.herokuapp.com](https://)

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

**OBS:** Verificar possibilidade de paginação. Caso seja facilmente replicável, bastará somente reutilizar a lógica implementada em pedidos.

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

Este endpoint retorna a ultima geolocalização registrada do pedido no formato indicado abaixo:

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

Este endpoint deve indicar que o pedido foi concluído.



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
