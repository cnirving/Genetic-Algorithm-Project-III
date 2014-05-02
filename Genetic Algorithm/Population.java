public class Population {

	
	private Individual[] members;
	private Individual fittest;
	private double meanFitness;
	
	public Population()	{
		
		members = new Individual[Main.POP_SIZE];
	}

	public Population(Individual[] members)	{

		this.members = members.clone();
	}
	
	// Generate an initial (random) population
	public void setMembers() {
		
		for (int i = 0; i < Main.POP_SIZE; i++) {
			
			members[i] = new Individual();
			members[i].setChromosome();
		}
	}
	public void setMembers(String[] chromosomes) {
		
		// set members when initial population is fixed
		for (int i = 0; i < Main.POP_SIZE; i++) {
			
			members[i] = new Individual();
			members[i].setChromosome(chromosomes[i]);
		}
	}

	public Individual[] getMembers() {
		
		return members;
	}
		
	public Individual getFittest() {

		this.fittest = Processes.pickFittest(this.members);
		return fittest;
	}

	public double getMeanFitness() {

		this.meanFitness = Processes.meanFitness(this.members);
		return meanFitness;
	}

	public String toString() {
		
		String popString = "";		
		for (int i = 0; i < members.length; i++) {
			
			popString += members[i].toString() + ", ";
		}
		
		return popString;
	}
}
