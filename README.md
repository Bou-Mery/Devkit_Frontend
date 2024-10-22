
# DevKit

DevKit est une application Android qui utilise la bibliothèque Volley pour gérer les appels réseau et la communication avec un backend développé en PHP. Ce projet a pour but de fournir un kit de développement simple pour les interactions client-serveur, avec une interface utilisateur Android connectée à un backend capable de gérer les opérations courantes telles que la récupération et la mise à jour des données.

## Fonctionnalités

### Fonctionnalités principales

- **Gestion des requêtes réseau** : Utilisation de la bibliothèque **Volley** pour envoyer des requêtes HTTP (GET, POST) et récupérer des réponses JSON.
- **Affichage des données** : Les données récupérées depuis le serveur sont affichées de manière dynamique sur l'application Android.
- **CRUD** : Création, lecture, mise à jour et suppression (CRUD) des données à partir de l'application Android.

### Architecture du projet

Le projet est structuré autour de deux parties :

1. **Frontend (Android)** : Développement en Java avec la bibliothèque Volley pour gérer les requêtes réseau.
2. **Backend (PHP)** : API RESTful développée en PHP, utilisant une base de données MySQL pour stocker et gérer les données.

---

## Prérequis

### Pour le Frontend
- **Android Studio** : Version 4.0 ou plus récente.
- **Bibliothèque Volley** : Ajoutée comme dépendance dans le fichier `build.gradle` et autres bibliothéques.
  
```gradle
    //Volley
    implementation("com.android.volley:volley:1.2.1")

    //Gson
    implementation("com.google.code.gson:gson:2.11.0")

    //CircleView
    implementation("de.hdodenhof:circleimageview:3.1.0")

    //Swipe
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.0.0")

    //Glide
    implementation ("com.github.bumptech.glide:glide:4.16.0")
```

### Pour le Backend
- **PHP** : Version 7.4 ou plus récente.
- **Serveur Web** : Apache/Nginx.
- **Base de données MySQL**.

---


## Utilisation

1. **Récupération des données** :

   - L'application envoie une requête GET au serveur pour récupérer les données.
   - Les réponses sont traitées et affichées dynamiquement dans l'interface utilisateur.

2. **Modification et suppression des données** :

   - Les utilisateurs peuvent modifier ou supprimer des données à partir de l'application.
   - Ces actions déclenchent des requêtes POST vers le serveur pour mettre à jour les données dans la base de données.

---

## Technologies utilisées

### Frontend
- **Java** pour le développement Android.
- **Volley** pour la gestion des requêtes réseau.

### Backend
- **PHP** pour le développement des API RESTful.
- **MySQL** pour la gestion de la base de données.

---



## Auteurs

- **BOUKHRAIS Meryem** 

---


