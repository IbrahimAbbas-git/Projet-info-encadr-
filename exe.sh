#!/bin/bash

rm -rf out
mkdir out
javac -d out $(find src -name "*.java")
cp -r src/img out/
cd out
java com.projet.MVC.Main
