package Default;

import java.util.Arrays;
import java.util.Random;

public class Algorithms {

	public static String arrToStr(String[] chromosome) {
		
		String chromStr = "";
		
		for (int i = 0; i < chromosome.length; i++) {
			
			chromStr += chromosome[i];
		}
		
		return chromStr;
	}	
	
	public static String[] strToArr(String chromStr) {
		
		String[] chromosome = new String[chromStr.length()];
		
		for (int i = 0; i < chromosome.length; i++) {
			
			chromosome[i] = chromStr.substring(0, 1);
			chromStr = chromStr.substring(1);
		}
		return chromosome;
	}
	
	public static double randomDouble() {
		
		Random random = new Random();
		double randomDouble = random.nextDouble();
		
		return randomDouble;
	}	
	public static int randomInt(int lowerLimitInclusive, int upperLimitInclusive) {
		
		Random random = new Random();
		int range = upperLimitInclusive - lowerLimitInclusive + 1;
		int randomInt = lowerLimitInclusive + random.nextInt(range);
		
		return randomInt;
	}
	
	public static boolean coinFlip(double probability) {
		
		boolean bool = false;
		
		if (randomDouble() < probability) {
			
			bool = true;
		}
		
		return bool;
	}

	public static double[] getRelFitArray(Individual[] population) {
		
		double[] fitArray = new double[population.length];
		double fitSum = 0.0;
		
		for (int i = 0; i < fitArray.length; i++) {
			
			fitArray[i] = population[i].getFitness();
			fitSum += fitArray[i];
		}
		
		double[] relFitArray = new double[fitArray.length];
		for (int i = 0; i < relFitArray.length; i++) {
			
			relFitArray[i] = fitArray[i]/fitSum;
		}
		
		return relFitArray;
	}
	public static double[] getRelFitArray(double[] fitnesses) {
		
		double[] fitArray = new double[fitnesses.length];
		double fitSum = 0.0;
		
		for (int i = 0; i < fitArray.length; i++) {
			
			fitArray[i] = fitnesses[i];
			fitSum += fitArray[i];
		}
		
		double[] relFitArray = new double[fitArray.length];
		for (int i = 0; i < relFitArray.length; i++) {
			
			relFitArray[i] = fitArray[i]/fitSum;
		}
		
		return relFitArray;
	}
	
	public static double[] getCumRelFitArray(Individual[] population) {
		
		double[] relFitArray = getRelFitArray(population);
		
		double[] cumRelFitArray = new double[relFitArray.length];
		
		for (int i = 0; i < relFitArray.length; i++) {
			
			cumRelFitArray[i] = relFitArray[i];
			if (i != 0) {
				
				cumRelFitArray[i] += cumRelFitArray[i - 1];
			}
		}
		
		return cumRelFitArray;
	}
	public static double[] getCumRelFitArray(double[] fitnesses) {
		
		double[] relFitArray = getRelFitArray(fitnesses);
		
		double[] cumRelFitArray = new double[relFitArray.length];
		
		for (int i = 0; i < relFitArray.length; i++) {
			
			cumRelFitArray[i] = relFitArray[i];
			if (i != 0) {
				
				cumRelFitArray[i] += cumRelFitArray[i - 1];
			}
		}
		
		return cumRelFitArray;
	}

	public static Individual[] rouletteGetPair(Individual[] matingPool) {
 
	    Individual[] matingPair = new Individual[2];
	    double[] cumRelFitArray;
	    Random random = new Random();
	    double rouletteNum;
        int x;
        
	    for (int i = 0; i < 2; i++)
	    {
		    cumRelFitArray = Algorithms.getCumRelFitArray(matingPool);
		    rouletteNum = random.nextDouble();
	    	x = 0;
	    	boolean mateFound = false;

	        while (mateFound == false)
	        {
	        	if (rouletteNum < cumRelFitArray[x])
	        	{
	        		mateFound = true;
	        		matingPair[i] = matingPool[x];
	        		if (i == 0) {

		        		matingPool = Algorithms.removeIndividual(matingPool, matingPair[0]);
	        		}
	        	}
	        	else
	        	{
	        		x++;
	        	}
	        }
	    }
	    
	    return matingPair;
	}
	public static Individual rouletteGetSingle(Individual[] members) {

		Individual individual = null;
		double[] cumRelFitArray = getCumRelFitArray(members);
    	int counter = 0;
    	boolean mateFound = false;

        while (mateFound == false)
        {
        	if (randomDouble() < cumRelFitArray[counter])
        	{
        		mateFound = true;
        		individual = members[counter];
        	}
        	else
        	{
        		counter++;
        	}
        }
    
    return individual;
}
	public static Individual rouletteGetSingle(Individual[] members, double[] associatedFitnesses) {

			Individual individual = null;
			double[] cumRelFitArray = getCumRelFitArray(associatedFitnesses);
	    	int counter = 0;
	    	boolean mateFound = false;

	        while (mateFound == false)
	        {
	        	if (randomDouble() < cumRelFitArray[counter])
	        	{
	        		mateFound = true;
	        		individual = members[counter];
	        		members = Algorithms.removeIndividual(members, individual);
	        		
	        	}
	        	else
	        	{
	        		counter++;
	        	}
	        }
	    
	    return individual;
	}
	
	public static Individual[] removeIndividual(Individual[] oldPool, Individual memberToBeRemoved) {
		
		Individual[] newPool = new Individual[oldPool.length - 1];
		int index = -1;
		int counter = 0;
		
		while (index == -1) {
			
			if (oldPool[counter].Equals(memberToBeRemoved)) {
				
				index = counter;
			}
			else {
				
				counter++;
			}
		}
		
		int j = 0;
		for (int i = 0; i < oldPool.length; i++) {
			
			if (i != index) {
				
				newPool[j] = oldPool[i];
				j++;
			}
		}
		
		return newPool;
	}
	public static Population removeIndividual(Population population, Individual memberToBeRemoved) {
		
		Individual[] oldPool = population.getMembers();
		Individual[] newPool = new Individual[oldPool.length - 1];
		int index = -1;
		int counter = 0;
		
		while (index == -1) {
			
			if (oldPool[counter].Equals(memberToBeRemoved)) {
				
				index = counter;
			}
			else {
				
				counter++;
			}
		}
		
		int j = 0;
		for (int i = 0; i < oldPool.length; i++) {
			
			if (i != index) {
				
				newPool[j] = oldPool[i];
				j++;
			}
		}
		
		Population newPop = new Population(newPool);
		
		return newPop;
	}
		
	public static String[] generateTspCities() {
		
		String[] tspCities = new String[Main.CHROM_LENGTH];
		
		for (int i = 0; i < tspCities.length; i++) {
			
			tspCities[i] = randomInt(Integer.valueOf(Main.LIMITS[0]), Integer.valueOf(Main.LIMITS[1]) - 1) + "," + randomInt(Integer.valueOf(Main.LIMITS[0]), Integer.valueOf(Main.LIMITS[1]) - 1);
		}
		
		return tspCities;
	}
	
	public static boolean checkSchema(String schemaStr, String[] chromosome) {
		
		boolean schemaMember = true;
		String[] schema = strToArr(schemaStr);
		
		for (int i = 0; i < chromosome.length; i++) {
			
			if (schema[i].equals("*")) {
				
			}
			else if (chromosome[i].equals(schema[i])) {
				
			}
			else {
				schemaMember = false;
				i = chromosome.length;
			}
		}
		
		return schemaMember;
	}
	
}
