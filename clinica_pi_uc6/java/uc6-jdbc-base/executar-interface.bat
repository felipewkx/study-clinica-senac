@echo off
cd /d "%~dp0"
title Clinica PI - Modulo de Relatorios

:: Se ainda nao foi compilado, compila na hora usando o javac nativo
if not exist "target\classes\br\senac\uc6\Main.class" (
    echo [!] Compilando projeto pela primeira vez...
    if not exist "target\classes" mkdir target\classes
    javac -cp "lib\postgresql-42.7.11.jar" -d target\classes src\main\java\br\senac\uc6\*.java
)

:: Abre a Interface Grafica diretamente das classes compiladas
start "" javaw -cp "target\classes;lib\postgresql-42.7.11.jar" br.senac.uc6.Main --gui