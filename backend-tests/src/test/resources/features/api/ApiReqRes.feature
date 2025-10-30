# language: pt
Funcionalidade: Testes de API do JSONPlaceholder

  Contexto:
    # A URL base foi alterada para JSONPlaceholder
    Dado que a base URL da API é "https://jsonplaceholder.typicode.com"

  @get @api
  Cenario: Buscar um usuário específico com sucesso
    # O endpoint /users/1 retorna um usuário específico
    Quando eu fizer uma requisição GET para "/users/1"
    Entao o status da resposta deve ser 200
    # Verifica o ID do usuário retornado
    E o corpo da resposta deve conter a chave "id" com o valor 1
    # Verifica se a chave "name" existe e é uma string
    E o corpo da resposta deve conter a chave string "name"

  @post @api
  Cenario: Criar um novo post com sucesso
    # Prepara os dados para criar um post (/posts)
    Dado que eu tenho os dados de um novo post
      | titulo   | corpo     | usuarioId |
      | Meu Titulo | Meu Corpo | 1         |
    Quando eu fizer uma requisição POST para "/posts" com esses dados
    Entao o status da resposta deve ser 201
    # Verifica se o post criado contém os dados enviados
    E o corpo da resposta deve conter o "titulo" e "corpo" enviados
    # Verifica se um ID foi gerado para o novo post
    E o corpo da resposta deve conter uma chave "id" que nao seja nula

  @put @api
  Cenario: Atualizar um post com sucesso
    # Prepara os dados para atualizar o post com ID 1 (/posts/1)
    Dado que eu tenho os dados atualizados para o post de id 1
      | titulo         | corpo           |
      | Titulo Editado | Corpo Editado   |
    Quando eu fizer uma requisição PUT para "/posts/1" com esses dados
    Entao o status da resposta deve ser 200
    # Verifica se o post atualizado contém os novos dados
    E o corpo da resposta deve conter o "titulo" e "corpo" atualizados

  @delete @api
  Cenario: Deletar um post com sucesso
    # Deleta o post com ID 1 (/posts/1)
    Quando eu fizer uma requisição DELETE para "/posts/1"
    # JSONPlaceholder retorna 200 OK para delete
    Entao o status da resposta deve ser 200
    # A resposta de delete do JSONPlaceholder é um corpo JSON vazio {}
    E o corpo da resposta deve ser um JSON vazio