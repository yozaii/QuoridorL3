package pack;

import java.util.Scanner;

public class UtilEntree {
	
	static Scanner sc = new Scanner(System.in);
	
	/**
	 * 
	 * @param min Minimum value for the input
	 * @param max Maximum value for the input
	 * @return Input from user
	 */
	public static int scannerInt(int min, int max) {
		int choice=min+1;
		boolean incorrectInput = true;
		while(choice<min || choice >max || incorrectInput) {
			System.out.print("Votre choix ? ");
			if(sc.hasNextInt()) {
				choice = sc.nextInt();
				incorrectInput = false;
			}
			else sc.next();
			if(choice<min || choice>max) System.out.print("Le nombre est incorrect, il doit être compris entre " + min + " et "+ max +".\n");
		}
		return choice;
	}

}
