Refactor : 

1 : Refactor basique, renommage de certaines méthode (ex : done -> terminerPartie), suppression des commentaires inutiles (ex : getteur du nombre d'essai pour getNbEssai) suppression des méthodes non utilisées dans Reponse avec l'attribut correspondant. 

Idem dans la classe mastermind avec une petite modification : utilisation de la méthode calculeResultat pour alléger la méthode evaluation. Maintenant lors de l'évaluation, si il n'y a pas de partie en cours, on retourne une erreur, sinon on calcule la partie en cours (plutôt que de gérer le tour de jeu, puis de sauvegarder, puis...). 