# language: pt

Funcionalidade: Validar Conexão Appium (Isolado)
  Como um usuário de automação
  Eu quero iniciar uma sessão mobile
  Para garantir que o setup isolado do Appium (Selenium 4) funciona

  Cenário: Abrir o aplicativo de Configurações do Android
    Dado que o driver mobile (local) foi iniciado
    Quando eu estiver na tela principal de Configurações
    # CORREÇÃO: O emulador exibe "Search Settings" no topo, então validamos isso.
    Então eu devo ver o título "Search Settings"