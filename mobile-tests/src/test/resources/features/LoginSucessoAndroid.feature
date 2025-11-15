# language: pt
Funcionalidade: Login SwagLabs

  @mobile
  Cenario: Realizar login com sucesso
    Dado que estou na tela de login do SwagLabs
    Quando realizo login com "standard_user" e "secret_sauce"
    Entao devo visualizar a area de produtos

  @mobile
  Cenario: Validar usuario bloqueado
    Dado que estou na tela de login do SwagLabs
    Quando realizo login com "locked_out_user" e "secret_sauce"
    Entao devo ver a mensagem de erro "Sorry, this user has been locked out."

  @mobile
  Cenario: Validar credenciais invalidas
    Dado que estou na tela de login do SwagLabs
    Quando realizo login com "usuario_errado" e "senha_errada"
    Entao devo ver a mensagem de erro "Username and password do not match any user in this service."