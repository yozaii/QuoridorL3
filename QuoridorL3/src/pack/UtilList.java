package pack;

import java.util.ArrayList;

public class UtilList {

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
