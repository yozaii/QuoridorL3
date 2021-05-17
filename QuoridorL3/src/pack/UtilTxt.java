package pack;

public class UtilTxt {

    public static void rules() {
        System.out.println("************************************************************");
		System.out.println("|                      REGLES DU JEU :                     |");
		System.out.println("************************************************************");
        System.out.println("***BUT DU JEU***");
        System.out.println("Atteindre le premier la ligne opposée à sa ligne de départ");
        System.out.println("***DEBUT DU JEU***");
        System.out.println("Au debut de la partie, chaque joueur possède 10 murs.Chaque pion est posé sur la case de départ.");
        System.out.println("Le joueur blanc commence.");
        System.out.println("***DEROULEMENT DE LA PARTIE***");
        System.out.println("A tour de rôle, chaque joueur choisit de déplacer son pion ou de poser un de ses murs.");
        System.out.println("Lorsqu’il n’a plus de murs, un joueur est obligé de déplacer son pion.");
        System.out.println("---Deplacement des pions :");
        System.out.println("Les pions se déplacent d’une case, horizontalement ou verticalement, en avant ou en arrière.");
        System.out.println("Les pions ne peuvent pas sauter par dessus les murs, ils doivent être contournés");
        System.out.println("Quand les 2 pions se retrouvent face à face sur 2 cases voisines non séparées par un mur, le joueur dont c’est le tour peut sauter son adversaire et se placer derrière lui.");
        System.out.println("Si un mur est située derrière le pion sauté, le joueur peut choisir de bifurquer à droite ou à gauche du pion adverse.");
        System.out.println("---Pose des murs:");
        System.out.println("Un mur doit être posée exactement entre 2 blocs de 2 cases, il ne peut être posé à l'intersection de 4 cases");
        System.out.println("Pour poser un mur il vous faut indiquer les coordonnées x et y les plus en haut a gauche possible.");
        System.out.println("C'est a dire qu'une fois les coordonnées entrées, le mur sera aussi sur les 2 cases en dessous ou les 2 cases a droite");
        System.out.println("La pose des murs a pour but de se créer son propre chemin ou de ralentir l’adversaire,\nMais il est interdit de lui fermer totalement l’accès à sa ligne de but: il faut toujours lui laisser une solution.");
        System.out.println("***FIN DE LA PARTIE***");
        System.out.println("Le premier joueur qui atteint une des 9 cases de la ligne opposée à sa ligne de départ gagne la partie.");
        System.out.println("\n\n");
    }
    
}
