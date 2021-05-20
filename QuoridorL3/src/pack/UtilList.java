package pack;

import java.util.ArrayList;

public class UtilList {

	/**
	 * Combines an integer list with a string list
	 * @param strList : string list
	 * @param intList : integer list
	 * @return : combination of the two ( integer list comes last)
	 */
	public static ArrayList<String> intStringComb(ArrayList<String> strList, ArrayList<Integer> intList){
		
		ArrayList<String> sList = strList;
		ArrayList<Integer> iList = intList;
		
		/*Transformation of integer list to string list*/
		ArrayList<String> newIList = new ArrayList<>(iList.size());
		for (Integer myInt : iList) { 
			newIList.add(String.valueOf(myInt)); 
		}
		
		
		/*Combination of the two new lists*/
		ArrayList<String> combination = new ArrayList<String>(strList);
		combination.addAll(newIList);
		return combination;
	}

}
