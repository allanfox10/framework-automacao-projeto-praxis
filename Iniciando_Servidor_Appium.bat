@echo off
title Servidor Appium (Jenkins Mode)
color 0A
cls

echo ======================================================
echo        FRAMEWORK PRAXIS - SERVIDOR APPIUM
echo ======================================================
echo.
echo [INFO] Preparando ambiente para conexao com Docker...
echo.
echo Configuracoes:
echo   [*] Endereco: 0.0.0.0 (Permite acesso externo)
echo   [*] Porta:    4723
echo   [*] CORS:     Habilitado (Jenkins pode conectar)
echo.
echo ======================================================
echo    AGUARDE AS LINHAS DE LOG ABAIXO PARA CONFIRMAR
echo    NAO FECHE ESTA JANELA ENQUANTO O JENKINS RODA
echo ======================================================
echo.

call appium --address 0.0.0.0 --port 4723 --allow-cors

pause