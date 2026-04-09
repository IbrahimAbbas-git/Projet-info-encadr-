# Projet Informatique Encadré S6

## Sommaire

1. [Introduction](#introduction)  
2. [Méthodologie et Organisation](#méthodologie-et-organisation)  
3. [Conception et Architecture](#conception-et-architecture)  
4. [Implémentation et Technique](#implémentation-et-technique)  
5. [Exécution et Extensions](#exécution-et-extensions)  
6. [Conclusion](#conclusion)  

---

## Groupe

**Binome3**  
- Nahel GANA
- Ibrahim ABBAS

---

## Structure GitHub

- `README.md` : Rapport détaillé du projet  
- `projet.pdf` : Fichier des consignes
- `UML class.pdf` : Fichier du diagramme de classes UML
- `javadoc` : Dossier contenant la Javadoc générée  
- `src` : Dossier contenant le code source  
- `exe.sh` : Script pour exécuter le jeu  

PS : Pour exécuter les fichiers Java sur terminal, taper `./execution.sh`, `bash execution.sh` ou `source execution.sh`.

---

## Introduction

Dans le cadre du module de Projet Informatique Encadré, nous avons conçu et développé un jeu de démineur se basant sur l'architecture Modèle - Vue / Contrôleur (MVC).
L’objectif principal était de créer un jeu générique, modulable et extensible, capable de gérer des grilles carrées comme hexagones, tout en offrant une base commune pour l’ajout futur de nouvelles extensions.

Ce projet, réalisé en binôme, nous a permis de mettre en pratique les concepts de **Programmation Orientée Objet**, de **gestion collaborative via Git**, ainsi que de **l'architecture MVC**.

---

## Méthodologie et Organisation

### Organisation du travail

**Répartition des tâches :**  
- **Ibrahim** : Menu, Boutons “Recommencer”, “Quitter”, “Indice”, Perfectionnement affichage
- **Nahel** : Grille Hexagonale, Sauvegardabilité, Messages victoire et défaite, Images, Javadoc

**Travail commun** : Grille Carrée, Gestion des voisins, Placement des icônes à son endroit

### Flux de travail (Workflow)

1. Conception initiale du diagramme de classes pour définir la structure générale.  
2. Développement des classes principales en parallèle avec des mises à jour du diagramme si nécessaire (ajout de classes ou méthodes).  
3. Utilisation intensive de **Git** pour la coordination : commits fréquents, communication continue, gestion de branches sans conflits.  

---

## Conception et Architecture

### Architecture globale

Le jeu de démineur repose sur une architecture modulaire et orientée objet basée sur le modèle MVC :

- **Jeu** : Gère le déroulement de la partie, l’état du jeu (gagné/perdu), les interactions du joueur ainsi que la sauvegarde et le chargement.
- **Grille (abstraite)** : Définit la structure du plateau de jeu et les traitements communs (placement des mines, calcul des voisins, révélation des cases).
- **GrilleC** / **GrilleH** : Implémentent respectivement les règles spécifiques des grilles carrée et hexagonale, notamment le calcul des voisins.
- **Case** : Représente une cellule du plateau avec ses propriétés (mine, révélée, drapeau, nombre de voisins).
- **MF (Vue)** : Gère l’affichage graphique et les interactions utilisateur via les boutons.
- **SimpleUI** : Permet la configuration de la partie et l’affichage des messages (menu, victoire, défaite).

**Déroulement d’une partie :**

1. L’utilisateur configure la partie via l’interface (type de grille, taille, difficulté).
2. Le modèle initialise la grille correspondante.
3. Le joueur interagit avec la vue via des clics (gauche/droit).
4. Le modèle traite l’action (révélation, drapeau, placement des mines au premier clic).
5. L’état du jeu est mis à jour (cases révélées, victoire ou défaite).
6. La vue est notifiée et met à jour l’affichage en conséquence.

### Diagrammes UML

Le diagramme de classes est disponible dans le fichier `UML class.pdf`.

### Justification des choix de conception

- **Classes abstraites :** Permettent de factoriser le comportement commun des grilles.
- **Héritage :** `Grille` → `GrilleC` / `GrilleH`
- **Modularité :** Facilite l’ajout de nouvelles extensions sans modifier le jeu principal.  

---

## Implémentation et Technique

### Environnement technique

- **Java OpenJDK 25** (compatible jusque Java 8)

### Structure du dépôt Git

- **Conventions de nommage :**  
  - Classes : `PascalCase`  
  - Méthodes : `camelCase`  

- **Arborescence :**  
  - `src/` # Code source
  - `javadoc/` # Javadoc générée
  - `UML class.pdf` # Diagramme UML
  - `exe.sh` # Script d'exécution

---

## Exécution et Extensions

### Exécution

- **Jeu principal :**  
```bash
bash execution.sh
```
