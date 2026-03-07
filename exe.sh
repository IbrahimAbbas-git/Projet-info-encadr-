#!/bin/bash

rm -rf out
mkdir out
javac -d out $(find src -name "*.java")
cp -r src/img out/
cd out
java src.com.projet.MVC.Main
