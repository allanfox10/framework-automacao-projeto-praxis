# language: pt
Funcionalidade: Gerenciamento de posts e usuários via API
  Testes de API para consumir os endpoints do JSONPlaceholder

  Contexto:
    Dado que a base URL da API é "https://jsonplaceholder.typicode.com"

  @get @api @buscaUsuario
  Cenário: Buscar um usuário específico com sucesso
    Quando eu fizer uma requisição GET para "/users/1"
    Então o status da resposta deve ser 200
    E o corpo da resposta deve conter a chave "id" com o valor 1
    E o corpo da resposta deve conter a chave string "name"

  @post @api @criaPost
  Cenário: Criar um novo post com sucesso
    Dado que eu tenho os dados de um novo post
      | titulo   | corpo     | usuarioId |
      | Meu Titulo | Meu Corpo | 1         |
    Quando eu fizer uma requisição POST para "/posts" com esses dados
    Então o status da resposta deve ser 201
    E o corpo da resposta deve conter o "titulo" e "corpo" enviados
    E o corpo da resposta deve conter uma chave "id" que nao seja nula

  @put @api @atualizaPost
  Cenário: Atualizar um post com sucesso
    Dado que eu tenho os dados atualizados para o post de id 1
      | titulo         | corpo           |
      | Titulo Editado | Corpo Editado   |
    Quando eu fizer uma requisição PUT para "/posts/1" com esses dados
    Então o status da resposta deve ser 200
    E o corpo da resposta deve conter o "titulo" e "corpo" atualizados

  @delete @api @deletaPost
  Cenário: Deletar um post com sucesso
    Quando eu fizer uma requisição DELETE para "/posts/1"
    Então o status da resposta deve ser 200
    E o corpo da resposta deve ser um JSON vazio