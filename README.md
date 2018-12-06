# Gestion parc vert, mode d'emploi

## Ajouter une tâche

### Générée aléatoirement autour de la position du téléphone

Dans la toolbar, cliquer sur "Ajouter données" pour ajout automatiquement 5 tâches. Si les permissions de geoloc ne sont pas données, il faudra les donner et recliquer sur le bouton<br/>
Elles sont ajoutées environ 1 seconde plus tard (le temps de trouver la position) à la liste.

### Manuellement

Cliquer sur "Ajouter une tâche" ou sur l'icone "+" en bas de l'écran.<br/>
La permission pour la position du téléphone est demandée, si elle est déjà permise, l'adresse est automatiquement ajoutée.<br/>
On peut également regénérer l'adresse en apuyant sur le bouton de localisation (il va générer une adresse aléatoire dans les 500 mètres).<br/>
Puis choisir le type de tâche et remplir le champs description (optionnel).<br/>
Il est possible ensuite de sauvegarder la tâche, qu'on retrouvera dans la liste.<br/>

## Voir les tâches

Cliquer sur "Voir les tâches" ou sur la loupe en bas de l'écran.

## Voir le détail d'une tâche

Il suffit de cliquer sur une des tâches de la liste.

- Afficher l'adresse sur une carte :
- Supprimer la tâche :

## Vider la base de données

Dans la toolbar, cliquer sur "Tout supprimer" pour supprimer complétement toutes les tâches.


# Rapport technique
Précision : ce projet utilise le nouveau système Navigation de Jetpack, pour pouvoir naviguer dans les resources navigation, il faut activer dans : Settings > Experimental > Navigation Editor<br/>
Android Studio 3.2.1 & plugin Kotlin 1.3.10<br/>


## Architecture du projet

Le projet contient 4 packages & 1 activity.
- db : qui va contenir tout ce qui concerne la liaison à la base de données SQLite via Room et l'entité Issue
- helper : qui va contenir des classes utiles et des fonctions statiques
- ui : qui va contenir le comportement des vues
- viewmodel : qui va contenir tout les viewmodels

## Jetpack
J'ai essayé d'intégrer au maximum les dernières nouveautés d'android en matière de bonnes pratiques.
Cela passe par l'utilisation du nouveau système de navigation (voir res/navigation).
Le support des anciennes versions des API via androidx.
La migration vers le langage Kotlin (Android KTX).
Et d'autres notions, qui suivent..

## Databinding, Live Data, Room
Le databinding va servir à simplifier le code et à déléguer l'affichage au maximum dans le xml.
Le livedata va permettre de synchroniser nos données directement à la base de données.
La base de données, justement, qui via un ORM simple, permet de créer des entités directement liés aux objets en BDD. Et qu'on va pouvoir simplement requêter avec les Dao.

## Viewmodel
Les viewmodel, qui ont un cycle de vie qui simplifie le passage de données entre fragment, vont aussi permettre de lier les autres aspects de jetpack : on va pouvoir récupérer les données dans un viewmodel automatiquement connecté au reste.