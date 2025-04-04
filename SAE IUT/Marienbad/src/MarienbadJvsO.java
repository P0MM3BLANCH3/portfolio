/**
* Créer une variante du jeu de Marienbad contre un joueur
* @author L. Dusch 
* @author D. Pauvert
*/

class MarienbadJvsO {
	
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
		
		String player;
		int nbrBaton = 0, playerPlay = 0, nbrLigne = 0, difficulty;
		int[] tabBaton;
		
		do {
			player = SimpleInput.getString("Nom du joueur : ");

			if (player.length() == 0) {
				System.out.println();
				System.out.println("\t----- Vous ne pouvez pas mettre un nom vide ! -----");
				System.out.println();
			}
		} while (player.length() == 0);
		
		do {
			difficulty = SimpleInput.getInt("Choississez la difficultée de Jacob: Normal (1), Difficile (2), Imbattable (3) : ");

			if (difficulty != 1 &&  difficulty != 2 && difficulty != 3) {
				System.out.println();
				System.out.println("\t----- Mode invalide ! -----");
				System.out.println();
			}
		} while (difficulty != 1 &&  difficulty != 2 && difficulty != 3);
		
		do {
			playerPlay = SimpleInput.getInt("Qui joue en premier ? Vous (0), Jacob (1) ");

			if (playerPlay != 0 && playerPlay != 1) {
				System.out.println();
				System.out.println("\t----- Joueur invalide ! -----");
				System.out.println();
			}
		} while (playerPlay != 0 && playerPlay != 1);
			
	
		/* Implémentation des noms des joueurs, du nombre de lignes pour la partie */
		nbrLigne = setUpGame();		
		
		tabBaton = new int[nbrLigne];
		
		/* Premier affichage + le nombre de baton total pour la partie */
		nbrBaton = initiationBaton(tabBaton);	
		
		while (nbrBaton != 0) {
			
			if (playerPlay == 0) {
				System.out.println("C'est au joueur " + player + " de jouer !");
				playerPlay = 1;
				affichage(tabBaton, nbrLigne);
				nbrBaton -= round(tabBaton);
			} else {
				System.out.print("\033[H\033[2J");
				System.out.println("C'est a Jacob de jouer !");
				playerPlay = 0;
				nbrBaton -= roundByBot(difficulty, tabBaton, nbrBaton, nbrLigne);
			}
			System.out.println();
			System.out.println("Nombre de baton restant : " + nbrBaton);
		}
		
		if (playerPlay == 1) {
			System.out.print("C'est le joueur " + player + " qui à gagné !!");
		} else {
			System.out.print("C'est Jacob qui à gagné !!");
		}
	}

	/**
	* Lance le mode test
	*/

	void playTest() {

		testInitiationBaton();
		testPositionGagnante();
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
        System.out.print(") \t= " + result + ", ");
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
				System.out.println("\t----- Nombre de baton incorrect ! -----");
				System.out.println();
			}
		} while (batonAEnlever <= 0 || batonAEnlever > tabBaton[ligne]);
		
		System.out.print("\033[H\033[2J");
		
		tabBaton[ligne] -= batonAEnlever;
		
		return batonAEnlever;
	}
	
	/**
    * Cerveau du bot, c'est à lui de jouer
    */
    
    int roundByBot(int difficulty, int[] tabBaton, int nbrBaton, int nbrLigne) {
		
		int batonAEnlever = 0, nimSum = 0, step = 0;
		boolean mouvement = false;
        
		if (difficulty == 1) {
			batonAEnlever = jeuAleatoire(tabBaton, nbrLigne);
		} else if (difficulty == 2) {
			
			if (step == 1) {
				batonAEnlever = jeuAleatoire(tabBaton, nbrLigne);
				step = 2;
				
			} else {
				for (int i = 0; i < tabBaton.length; i++) {
					nimSum ^= tabBaton[i];
				}
				
				if (nimSum != 0) {
					batonAEnlever = positionGagnante(tabBaton, nimSum);
					
				} else {
					batonAEnlever = jeuAleatoire(tabBaton, nbrLigne);
				}
				
				step = 1;
			}
			
		} else {
			for (int i = 0; i < tabBaton.length; i++) {
				nimSum ^= tabBaton[i];
			}
			
			if (nimSum != 0) {
				batonAEnlever = positionGagnante(tabBaton, nimSum);
				
			} else {
				batonAEnlever = jeuAleatoire(tabBaton, nbrLigne);
			}
			
			mouvement = true;
		}
		
        return batonAEnlever;
    }
    
    /**
    * Jouer la position gagnante
    */
    
    int positionGagnante (int[] tabBaton, int nimSum) {
        
        int retirer = 0, i = 0;
        boolean mouvement = false;

		while (i < tabBaton.length && !mouvement) {
            int value = tabBaton[i] ^ nimSum;

            if (value < tabBaton[i]) {
                retirer = tabBaton[i] - value;
                tabBaton[i] = value;
                mouvement = true;
            } 
            
            i++;
        }

        //System.out.println("Jacob à enlevé " + retirer + " batons à la ligne " + (i - 1) + " .");
		return retirer;
    }

    /**
    * Teste la méthode positionGagnanet()
    */

    void testPositionGagnante() {

		System.out.println();
		System.out.println("*** testPositionGagnante()");
		testCasPositionGagnante(new int[] {1, 3, 5, 7, 9}, new int[] {1, 3, 5, 7, 0}, 9);
		testCasPositionGagnante(new int[] {1, 3, 5, 4, 9, 11, 13}, new int[] {1, 3, 5, 4, 5, 11, 13}, 4);
		testCasPositionGagnante(new int[] {1, 0, 1, 7, 2, 0, 13, 8, 12, 19, 21, 8}, new int[] {1, 0, 1, 5, 2, 0, 13, 8, 12, 19, 21, 8}, 2);
		testCasPositionGagnante(new int[] {1, 3, 3, 0, 9, 11}, new int[] {1, 0, 3, 0, 9, 11}, 3);
	}

	/**
	* Teste l'appel de la méthode positionGagnante
	* @param tab le tableau au moment ou jacob joue
	* @param resultTab le tableau après le jeu de jacob
	* @param result Nombre de baton à retirer attendu
	*/

	void testCasPositionGagnante(int[] tab, int[] resultTab, int result) {

		int resExec, k = 0, nimSum = 0;
		boolean resExecTab = true;

		for (int i = 0; i < tab.length; i++) {
			nimSum ^= tab[i];
		}

		System.out.print("positionGagnante(");
        displayTab(tab);
        resExec = positionGagnante(tab, nimSum);
        System.out.print(") \t= " + resExec + ", ");
        displayTab(tab);
        System.out.print("\t :");

        while (k < tab.length && resExecTab) {
			if (tab[k] != resultTab[k]) {
				resExecTab = false;
			}

			k++;
		}

        if (result == resExec && resExecTab) {
			System.out.println("OK");
		} else {
			System.out.println("ERROR");
		}
	}
		    
    /**
    * Jouer la position perdante
    */
    
    int jeuAleatoire(int[] tabBaton, int nbrLigne) {

        int ligne, batonAEnlever;
        
        do {
            ligne = (int) (Math.random() * (nbrLigne - 1));
        } while (tabBaton[ligne] == 0);
        
        batonAEnlever = (int) (Math.random() * tabBaton[ligne] + 1);
      
        tabBaton[ligne] -= batonAEnlever;
        
        System.out.println("Jacob à enlevé " + batonAEnlever + " batons à la ligne " + ligne + " .");
        
        return batonAEnlever;
    }
	
	/**
	* Affiche les batons restant sur la partie
	*/
	
	void affichage(int[] tabBaton, int nbrLigne) {
		
		System.out.println();
		
		for (int i = 0; i < nbrLigne; i++) {
			System.out.print(i + " : ");
			for (int n = 0; n < tabBaton[i]; n++) {
				System.out.print(" | ");
			}
			System.out.println();
			System.out.println();
		}
	}

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
