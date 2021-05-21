package pack;

public class GameMenu {

    public static void gameMenu() {

        System.out.print("************************************************************\n");
		System.out.print("|               BIENVENUE AU JEU DU QUORIDOR               |\n");
		System.out.print("************************************************************\n\n\n");
        int choice = 3;
        do {
            printgameMenu();
            choice = UtilEntree.scannerInt(1,4);
            switch(choice) {
                case 1 : gameDifficulty(); break;
                case 2 : Interface.menu(); break;
                case 3 : UtilTxt.rules(); break;
                default : System.out.print("Je n'ai pas compris\n");
            }
        } while(choice == 3);

    }

    public static void printgameMenu() {
		System.out.print("*************************************\n");
		System.out.print("|     Voici vos Possibilités :      |\n");
		System.out.print("|     1 : Jouer contre l'ordinateur |\n");
		System.out.print("|     2 : Joueur contre un joueur   |\n");
		System.out.print("|     3 : Consulter les règles      |\n");
		System.out.print("*************************************\n");
	}

    public static void gameDifficulty() {
        System.out.print("*************************************\n");
        System.out.print("|     Choisissez la difficulté :    |\n");
        System.out.print("|     1 : Facile                    |\n");
        System.out.print("|     2 : Moyen                     |\n");
        System.out.print("|     3 : Difficile                 |\n");
        System.out.print("*************************************\n");
        int choice = UtilEntree.scannerInt(1,3);
        switch(choice) {
            case 1 : gamePlayer(3); break;
            case 2 : gamePlayer(5); break;
            case 3 : gamePlayer(7); break;
            default : System.out.print("Je n'ai pas compris\n");
        }
    }

    public static void gamePlayer(int depth) {
        System.out.print("*************************************\n");
        System.out.print("|     Choisisser votre pion :       |\n");
        System.out.print("|     1 : Blanc                     |\n");
        System.out.print("|     2 : Noir                      |\n");
        System.out.print("*************************************\n");
        int choice = UtilEntree.scannerInt(1,2);
        switch(choice) {
            case 1 : Interface.menuIA2(depth); break;
            case 2 : Interface.menuIA1(depth); break;
            default : System.out.print("Je n'ai pas compris\n");
        }

    }
    
}
