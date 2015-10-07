### av001

* Build le projet avec 

```java
gradle build
```

* Deployer le war dans le repertoire webapp de votre tomcat

* Acceder a l'url
```java
http://localhost:8080/av001/GetProduct?idProduct=2
```

* Verifier le resultat json
```java
    {
        "id":2,
        "name":"Nom Produit",
        "description":"La description du produit",
        "available":true,
        "online":true,
        "price":15.9,
        "currency":"EUR",
        "category":{
            "id":2,
            "name":"Bottom caterory",
            "description":"description Bottom caterory",
            "parentCategory":{
                "id":1,
                "name":"Top caterory",
                "description":"description Top caterory",
                "parentCategory":null
                }
        }
    }
```

### La suite
* REST HATEOAS
* Base de donnees
* Test Unitaire
* Compte Utilisateur
* Securisation spring security
* Logger
* Ajout Panier
* Passer Commande
* Mode Paiement
* Payer Commande
* Mode Livraison
* Livraison Commande
* Localisation
* Administration Produit
* Administration Commande
* Administration Utilisateur
* Admenistration Mode de paiement
* Administration Mode de livraison

