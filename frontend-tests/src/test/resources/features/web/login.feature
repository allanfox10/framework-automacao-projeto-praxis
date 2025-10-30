# language: pt
Funcionalidade: Login na plataforma SauceDemo

  Cenário: Login com credenciais válidas
    Dado que eu acesse a página de login do SauceDemo
    Quando eu preencher o campo de usuário com "standard_user"
    E eu preencher o campo de senha com "secret_sauce"
    E eu clicar no botão de login
    Então eu devo ser redirecionado para a página de inventário
    E eu devo visualizar o título "Products"

  Cenário: Login com usuário bloqueado
    Dado que eu acesse a página de login do SauceDemo
    Quando eu preencher o campo de usuário com "locked_out_user"
    E eu preencher o campo de senha com "secret_sauce"
    E eu clicar no botão de login
    Então eu devo permanecer na página de login
    E eu devo visualizar a mensagem de erro "Epic sadface: Sorry, this user has been locked out."