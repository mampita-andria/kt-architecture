# Architecture de développement

Architecture en couches (6-tiers) pour une meilleure **separation of concerns**, préserver la forte cohésion et surtout le couplage faible. 
Les fonctionnlités pourront donc être développées en parallèle par les membres de l'équipe. Voici les couches : 
- common
- presentation
- data
- service
- repositoery
- constraint


### common
Les entités communes réutilisables partout dans l'application (et les autres couches donc) s'y trouve : les constantes, préférences, utilitaires et autres. 

Voir `StringExtension.kt`: pour ajouter de nouvelles méthodes par extension à la classe String utilisés dans toute l'application. Et est réutilisabale. 

### presentation
Couche la plus proche de l'utilisateur. 

On y repertorie les vues de l'applications : Activity, Fragment, ...

Voir `LoginActivity.kt` en guise d'exemple. 


### repository
Couche pour manipuler les données (de la base de données). 

On y préconsie comme son nom l'indique le pattern repository. (Et donc l'abstraction). 

Pourquoi ? Si aujourd'hui tu utilises Realm et que demain tu changes en Room ou ORMLite ou... tu remercieras ce pattern.

N.B : On utilisait ORMLite mais le choix est vaste.

Voir `ICommonRepository.kt` et `GenericCommonRepository.kt`


### data
Couche où sont placés les modèles de l'application. 

On y trouve : 

- Les `DataTransferObjects` : Modèles de vue en POJO qui seront utilisés pour afficher les données dans la couche `presentation` en et aussi prendre les données issues des endpoints des API; 
- Les `DomainObject` : Modèles de données définissant la structure de données de l'application.

Voir `Product.kt` comme **domainobject** et `ProductDto.kt comme **transfertobject**

### constraint 
Couche ayant une **factory** pour mapper le modèle de données `domainobjet a.k.a do` en `datatransfertobjet ou dto` et inversement. 

Pourquoi ? Pour ne pas exposer la structure de la base de données et la rendre indépendante. 

Voir `ProductFactory.kt` pour transformer l'entité Product justement en do->dto et vice-versa.

### service
Le coeur de l'application. 

On y trouve : 
- le service applicatif où se situe la logique métier de l'application est encapsulée.
- une implémentation du pattern **BusinessDelegate** pour découpler la couche présentation (et l'appel des services web) du métier.


Voir `GenericBdl.kt` pour voir comment l'appel des webservices se fait avec `AndroidNetworking`. 

Ainsi que les paires `AuthentificationSA.kt`, `AuthentificationSAImpl.kt` et 
 `AdministerUserSA.kt` et `AdministerUserSAImpl.kt` pour la logique de l'authentification.

# Comment ça marche ? 

L'exemple qui suit concerne l'authentification. 

Tu es dans la vue de l'authentification, tu entres ton login/password puis cliques sur le bouton se connecter. `LoginActivity.kt`

a.  La vue va faire appel à au service applicatif (via Injection de dépendance)

b. Le service va former le Dto pour l'appel Rest. Ensuite va appeler le BusinessDelegate pour que celui fasse l'appel Rest en Post. `AuthentificationSA.kt`

c. Une fois l'appel réussi (ou pas selon le cas), le service va appeler le Repository pour sauvegarder les données dans la base de données (mais mapper la réponse avant avec le Factory) `AdministerUserSA.kt`

d. Si tout s'est bien passé, on passe à la vue principale. 

And voilàà ! 

Merci pour ton temps jeune (ou moins jeune) Padawan.
