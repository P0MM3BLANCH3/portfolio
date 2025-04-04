/**
* Créer une variante du jeu de Marienbad contre un joueur
* @author L. Dusch 
* @author D. Pauvert
*/

class MarienbadJvsJ {
	
	void principal() {
		
		/* Lancement de la partie */
		whoPlay();
		//playTest();
	}
	
	/**
	* Dit a quelle joueur de jouer / qui a gagner ou perdu la partie
	*/	
	
	void whoPlay() {

		String[] morpionWelcome = {
      "M     M     A     RRRR    I   EEEEE  NN    N  BBBB      A     DDDD",
			"MM   MM   A   A   R   R   I   E      N N   N  B   B   A   A   D   D",
			"M M M M   A   A   RRRR    I   EEEEE  N  N  N  BBBB    A   A   D    D",
			"M  M  M  AAAAAAA  R R     I   EEEEE  N   N N  B   B  AAAAAAA  D    D",
			"M     M  A     A  R  R    I   E      N    NN  B   B  A     A  D   D", 
			"M     M  A     A  R   R   I   EEEEE  N     N  BBBB   A     A  DDDD"
        };
        
        for (int i = 0; i < morpionWelcome.length; i++) {
            System.out.println(morpionWelcome[i]);
        }

        System.out.println();
		
		String firstPlayer, secondPlayer;
		int nbrBaton = 0, playerPlay = 0, nbrLigne = 0;
		int[] tabBaton;	
		
		do {
			firstPlayer = SimpleInput.getString("Nom du premier joueur : ");
			secondPlayer = SimpleInput.getString("Nom du deuxieme joueur : ");

			if (firstPlayer.length() == 0 || secondPlayer.length() == 0) {
				System.out.println();
				System.out.println("\t----- Vous ne pouvez pas mettre un nom vide ! -----");
				System.out.println();
			}
		} while (firstPlayer.length() == 0 || secondPlayer.length() == 0);
	
		/* Implémentation des noms des joueurs, du nombre de lignes pour la partie */
		nbrLigne = setUpGame();		
		
		tabBaton = new int[nbrLigne];
		
		/* Premier affichage + le nombre de baton total pour la partie */
		nbrBaton = initiationBaton(tabBaton);	
		
		while (nbrBaton != 0) {
			if (playerPlay == 0) {
				System.out.println("C'est au joueur " + firstPlayer + " de jouer !");
				playerPlay = 1;
			} else {
				System.out.println("C'est au joueur " + secondPlayer + " de jouer !");
				playerPlay = 0;
			}
			
			System.out.println();
			affichage(tabBaton, nbrLigne);
			nbrBaton -= round(tabBaton);
		}
		
		if (playerPlay == 1) {
			System.out.print("C'est le joueur " + firstPlayer + " qui a gagne !!");
		} else {
			System.out.print("C'est le joueur " + secondPlayer + " qui a gagne !!");
		}
	}

	/**
	* Lance le mode test
	*/

	void playTest() {

		testInitiationBaton();
	}
	
	/**
	* Préparer le jeu, le nom des deux joueurs et le nombre de ligne qu'il veule utiliser
	*/
	
	int setUpGame() {
		
		int nbrLigne;
		
		do {
			nbrLigne = SimpleInput.getInt("Combien de lignes ? (entre 2 et 15 (inclus)) : ");

			if (nbrLigne < 2 || nbrLigne > 15) {
				System.out.println();
				System.out.println("\t----- Nombre de ligne incorrect ! -----");
				System.out.println();
			}
		} while (nbrLigne < 2 || nbrLigne > 15);
		
		System.out.print("\033[H\033[2J");
		
		return nbrLigne;
	}
	
	/**
	* Afficher le jeu au fil de la partie
	* @param tab le tableau pour ranger les lignes et leurs nombres de batons
	* @param nbrLigne le nombre de lignes défini par les joueurs
	* @return nbrBaton un nombre de baton au total, et un affichage pour avoir un visuel sur les batons restants
	*/
	
	int initiationBaton(int[] tab) {
		
		int nbrBaton = 0, baton = 1;
		
		for(int i = 0; i < tab.length; i++) {
			tab[i] = baton;
			nbrBaton += baton;
			baton += 2;
		}
		
		return nbrBaton;
	}

	/**
	* Teste la méthode initationBaton()
	*/

	void testInitiationBaton() {
		System.out.println();
		System.out.println("*** testInitiationBaton");
		testCasInitiationBaton(3, new int[] {1, 3, 5}, 9);
		testCasInitiationBaton(5, new int[] {1, 3, 5, 7, 9}, 25);
		testCasInitiationBaton(10, new int[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19}, 100);
		testCasInitiationBaton(15, new int[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21, 23, 25, 27, 29}, 225);
	}

	/**
	* Teste l'appel de la méthode initationBaton()
	* @param nbrLigne Le nombre de ligne choisi par l'utilisateur
	* @param resultTab Le tableau attendu
	* @param result Le resultat du nombre de baton attendu
	*/

	void testCasInitiationBaton(int nbrLigne, int[] resultTab, int result) {

		int resExec, i = 0;
		boolean resExecTab = true;
		int[] tab = new int[nbrLigne];

		System.out.print("initiationBaton(");
        displayTab(tab);
        resExec = initiationBaton(tab);
        System.out.print(") \t= " + resExec + ", ");
        displayTab(tab);
        System.out.print("\t :");

        while (i < tab.length && resExecTab) {
			if (tab[i] != resultTab[i]) {
				resExecTab = false;
			}

			i++;
		}

        if (result == resExec && resExecTab) {
			System.out.println("OK");
		} else {
			System.out.println("ERROR");
		}
	}
	
	/**
	* Demander sur quel ligne agir, et combien de baton enlever
	*/
	
	int round(int[] tabBaton) {
		
		int ligne, batonAEnlever;
		
		do {
			ligne = SimpleInput.getInt("Sur quelle ligne voulez vous agir ? ");
			
			if (ligne < 0 || ligne >= tabBaton.length || tabBaton[ligne] == 0) {
				System.out.println();
				System.out.println("\t----- Ligne incorrect ! -----");
				System.out.println();
			}
		} while (ligne < 0 || ligne >= tabBaton.length || tabBaton[ligne] == 0);
		
		do {
			batonAEnlever = SimpleInput.getInt("Combien de batons voulez vous enlever (entre 1 et " + tabBaton[ligne] + " (inclus)) ? ");

			if (batonAEnlever <= 0 || batonAEnlever > tabBaton[ligne]) {
				System.out.println();
				System.out.println("\t----- Nombre incorrect ! -----");
				System.out.println();
			}
		} while (batonAEnlever <= 0 || batonAEnlever > tabBaton[ligne]);
		
		System.out.print("\033[H\033[2J");
		
		tabBaton[ligne] -= batonAEnlever;
		
		return batonAEnlever;
	} 
	
	/**
	* Affiche les batons restant sur la partie
	*/
	
	void affichage(int[] tabBaton, int nbrLigne) {
		
		for (int i = 0; i < nbrLigne; i++) {
			System.out.print(i + " : ");
			for (int n = 0; n < tabBaton[i]; n++) {
				System.out.print(" | ");
			}
			System.out.println();
			System.out.println();
		}
	}

	/**
    * Affiche le tableau t 
    * @param t un tableau d'entiers
    */
     
    void displayTab(int[] t){
        int i = 0;
        System.out.print("{");
        while(i<t.length-1){
            System.out.print(t[i] + ",");
            i=i+1;
        }
        System.out.print(t[i]+"}");
    }
}
