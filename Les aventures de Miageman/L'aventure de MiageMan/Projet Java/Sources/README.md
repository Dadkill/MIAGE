# Description du Projet 
Un exemple très sommaire de jeu est fourni qui permet seulement de se déplacer parmi quatre pièces d'un château. 
Quatre commandes de mouvement (N, S, E ou O) permettent de se déplacer d'une pièce à une autre en empruntant la sortie correspondante de la pièce 
(nord, sud, est ou ouest respectivement), lorsqu'elle existe. La commande ? permet d'obtenir la liste des commandes autorisées et la commande Q 
permet de quitter le jeu. Les pièces sont munies d'un certain nombre de sorties pour atteindre les pièces voisines. L'archive [sourcesDuJeuSommaire.jar](http://enseignement.etoile.univ-amu.fr/lucile/l3i_miage/projet/sourcesDuJeuSommaire.jar)
contient les sources du jeu sommaire.

Ce projet de programmation en Java consiste à écrire un jeu d'aventure dont l'objectif est de résoudre une énigme en parcourant un ensemble de 
zones et en récupérant des objets ou des indices durant le parcours.
Vous devrez écrire la documentation javadoc de vos classes et mettre en place des tests automatisés permettant de vérifier le fonctionnement de votre
jeu au fur et à mesure du développement.

## 1ère étape : Définition du scenario
Commencez par lire la totalité du sujet pour connaître les différentes possibilités que pourra utiliser votre jeu : téléportation, sorties haut 
et bas, sortie sans retour, sortie fermée (franchissable une fois ouverte), sortie invisible (franchissable une fois visible), éléments, conteneurs.
Pour définir le thème de votre jeu, choisissez :
* un lieu quelconque à une époque quelconque : un vaisseau spatial, une ville, un train, une forêt, un bâtiment, une fourmilière, un lac, etc.
* un personnage : un footballeur, Mickey, un étudiant Miagiste, un chat, une fleur, etc.
* un objectif circonstancié pour gagner le jeu qui se ramène à délivrer quelqu'un, s'échapper, trouver un trésor, réunir des ingrédients, empêcher une catastrophe, élucider un crime, etc.

*Exemples de thème : A Marseille, un chorégraphe doit trouver des danseurs pour monter un spectacle de danse ou dans un laboratoire, un savant doit construire une machine à remonter le temps.*

Imaginez un scenario complet pour votre jeu (voir des exemples à partir de la page 3). Dessinez la carte des zones de votre jeu, trouvez ou construisez des images pour illustrer les zones, définissez les éléments contenus par chaque zone, déterminez le parcours (c'est-à-dire la liste des commandes) qui permet de résoudre l'énigme que vous avez choisie et les différents pièges qui jalonnent les différents parcours. Identifiez clairement la situation gagnante et les situations perdantes.

## 2ème étape : Parcours des zones
Le plus souvent, la carte des zones permet de passer d'une zone voisine à l'autre en rebroussant chemin. Remarquez toutefois qu'il est possible de définir la carte de telle sorte qu'une sortie n'ait pas de retour possible, par exemple lorsque le joueur tombe dans une
fosse.

Ecrivez le programme qui permet de circuler entre les différentes zones de votre jeu en prenant des sorties N (nord), S (sud), O (ouest), E (est), 
B (bas) ou H (haut). Ajoutez aux zones la possibilité d'avoir une sortie par téléportation qui permet de se déplacer dans une des autres 
zones (le choix de la zone atteinte est aléatoire ou pas). Le nombre d'utilisations de cette fonction de téléportation doit pouvoir être limité.
Ajoutez une commande R qui permet au joueur de revenir dans la zone précédente (sauf après avoir franchi une sortie sans retour évidemment). 
Le joueur doit pouvoir ainsi réaliser plusieurs retours en arrière successifs.
Le nombre de commandes réalisées par le joueur doit pouvoir être limité. Une fois cette limite atteinte, la partie doit se terminer.

## 3ème étape : La gestion des éléments et des conteneurs d'éléments
Les zones doivent pouvoir contenir des éléments : torche, clé, indice, etc. Le joueur peut prendre un élément présent dans la zone pour 
le mettre dans son sac (l'élément disparaît alors de la zone), déposer un élément de son sac dans une zone (l'élément est maintenant dans la zone), 
voir les éléments contenus par son sac. Le sac du joueur peut avoir une capacité limitée en nombre d'éléments ou en poids.
Les zones peuvent aussi contenir des conteneurs (coffre, armoire, etc.) renfermant plusieurs éléments. Un conteneur peut être ouvert ou fermé. 
S'il est fermé, il doit être ouvert en utilisant un élément (clef, code, marteau, etc.) pour avoir accès à son contenu.

Modifiez votre programme de façon à gérer les éléments et les conteneurs d'éléments.
Faites en sorte qu'une sortie d'une zone puisse être fermée et qu'elle ne soit franchie qu'une fois ouverte. De plus, une sortie peut être 
invisible ou obstruée, elle n'est donc pas affichée avec les autres sorties de la zone tant que le joueur n'a pas trouvé le moyen de la rendre visible ou accessible.

## 4ème étape : La solution de l'énigme
Créez un fichier texte qui contienne la suite de commandes à réaliser pour gagner la partie le plus rapidement possible. Faites en sorte de 
pouvoir jouer cette suite de commandes. Pour cela, vous pouvez ajouter une commande T (test) qui permet de lire et interpréter le fichier de commandes.

# Suivi du projet 
- [X] 16 novembre 2018 Rendu du scenario détaillé
- [X] 21 décembre 2018 Rendu des spécifications
- [X] 1er avril 2019 Rendu final (programme, rapport, tests, javadoc) 
