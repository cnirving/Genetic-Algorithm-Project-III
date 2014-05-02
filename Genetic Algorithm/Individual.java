public class Individual {

	
	private String[] chromosome;
	private double fitness;
	
	public Individual()	{
		
		chromosome = new String[Main.CHROM_LENGTH];
	}

	public Individual(String[] chromosome)	{
		
		this.chromosome = chromosome;
	}
	
	public void setChromosome() {
		
		chromosome = Processes.geneCoding(Main.CODING_TYPE);
	}
	public void setChromosome(String chromStr) {
		
		// set chromosome for individual when initial generation is fixed
		String[] chromArray = Algorithms.strToArr(chromStr);
		this.chromosome = chromArray;
	}
	
	public String[] getChromosome() {
		
		return chromosome;
	}
	
	public double getFitness() {

		this.fitness = Processes.computeFitness(chromosome);
		return fitness;
	}

	public boolean Equals(Individual compare) {
		
		boolean equals = false;
		
		if (this.chromosome == compare.chromosome) {
			
			equals = true;
		}
		
		return equals;
	}
	
	public String toString() {

		String indivString;
		indivString = "[";
		for (int i = 0; i < chromosome.length; i++) {
			
			indivString += chromosome[i] + " / ";
		}
		indivString += "] " + getFitness();
		
		return indivString;
	}
}
