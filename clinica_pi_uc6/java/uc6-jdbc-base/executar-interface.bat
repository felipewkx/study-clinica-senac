@echo off
cd /d "%~dp0"
title Clinica PI - Modulo de Relatorios

if not exist "target\relatorios-clinica.jar" (
    echo [!] Primeira execucao detectada. Compilando o projeto...
    if not exist "target\classes" mkdir "target\classes"
    javac -cp "lib\postgresql-42.7.11.jar" -d target\classes src\main\java\br\senac\uc6\*.java
    cd target\classes
    jar -xf ..\..\lib\postgresql-42.7.11.jar
    cd ..\..
    jar -cfe target\relatorios-clinica.jar br.senac.uc6.Main -C target\classes .
)

echo [✓] Abrindo Interface Grafica...
start "" javaw -jar target\relatorios-clinica.jar --gui