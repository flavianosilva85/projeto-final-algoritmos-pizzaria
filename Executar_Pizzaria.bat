@echo off
chcp 65001 > nul
title Sistema de Gestao de Pizzaria - Unyleya (Atividade Final)
cls

echo ==================================================================
echo         INICIANDO PROJETO FINAL: PIZZARIA (UNYLEYA ADS)           
echo ==================================================================
echo.

set "JDK_BIN=C:\Users\Flaviano_silva\.vscode\extensions\redhat.java-1.58.0-win32-x64\jre\25.0.4.1-win32-x86_64\bin"

if exist "%JDK_BIN%\javac.exe" (
    set "COMPILADOR=%JDK_BIN%\javac.exe"
    set "EXECUTOR=%JDK_BIN%\java.exe"
) else (
    set "COMPILADOR=javac"
    set "EXECUTOR=java"
)

echo [1/2] Compilando os arquivos Java do pacote Projeto...
"%COMPILADOR%" Projeto\Cardapio.java Projeto\Cliente.java Projeto\Pizza.java Projeto\Pedido.java Projeto\Pizzaria.java

if errorlevel 1 (
    echo.
    echo [ERRO] Falha na compilacao! Verifique os arquivos.
    pause
    exit /b
)

echo [2/2] Compilacao bem-sucedida! Executando a aplicacao...
echo.
"%EXECUTOR%" -cp . Projeto.Pizzaria

echo.
echo ==================================================================
echo Aplicacao finalizada. Pressione qualquer tecla para fechar.
pause > nul
