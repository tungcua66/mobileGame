#################################
###      ENGLISH Version      ###
#################################

Author: TRAN Manh Bach Tung
Project: football application for Android/Desktop mode

Description: All the description is included in football_game.docx

Version of Java required: java 8, please dont test with OpenJDK-11 or something greater than java 8. 
Why?: because LibGDX dont work with version > 8 :(
How to install java 8: sudo apt-get install openjdk-8-jdk-openjdk-8-jre
Set default java verion: sudo update-alternatives --config java and then choose the version java 8

Left player : W, A, S, D
Right player: arrow button
==> How to change the player controller: core/src/com.mygdx.game/screens/Game.java in the 77th line

##How to run the prog##
before running: dont forget to change the location of the android SDK in local.properties
1. place in the root folder, then ./gladlew desktop:run
2. import the src file into InteliJ or any IDE and run it.

--GOOD LUCK AND HAVE FUN WITH THE APP--

#################################
###        FR Version         ###
#################################

Auteur: TRAN Manh Bach Tung
Projet: jeu de foot application pour Android/Desktop

Description: Veuillez regarder le fichier jeuDeFoot.docx pour les détails

Version demandée: java 8. Ne utilise pas une version > 8 parceque LibGDX ne fonctionnera pas avec
Installer java 8 si besoin: sudo apt-get install openjdk-8-jdk-openjdk-8-jre
Faire défaut: udo update-alternatives --config java et après choisissez la version corespondante

Joueur à gauche: W, A, S, D
Joueur à droite: les flèches
==>Comment changer: core/src/com.mygdx.game/screens/Game.java en 77ème ligne

##Comment tester##
Avant: il ne faut pas oublier de préciser le bon chemin pour le SDK(fichier local.properties)
1. placez-vous dans le fichier principal, puis ./gradlew desktop:run
2. importer le fichier src dans InteliJ ou peu n'importe quel IDE, puis clique droite sur 
desktopLauncher et Run

--AMUSEZ-VOUS BIEN AVEC LE JEU--
