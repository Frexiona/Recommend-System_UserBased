/**
 * Compute the similarity between two items based on the Cosine between item genome scores
 */ 

package alg.np.similarity.metric;

import java.util.Set;

import profile.Profile;
import util.reader.DatasetReader;

public class GenomeMetric implements SimilarityMetric
{
	private DatasetReader reader; // dataset reader
	
	/**
	 * constructor - creates a new GenomeMetric object
	 * @param reader - dataset reader
	 */
	public GenomeMetric(final DatasetReader reader)
	{
		this.reader = reader;
	}
	
	/**
	 * computes the similarity between items
	 * @param X - the id of the first item 
	 * @param Y - the id of the second item
	 */
	public double getItemSimilarity(final Integer X, final Integer Y)
	{
		// calculate similarity using Cosine (11 lines)
		Profile genomeX = reader.getItem(X).getGenomeScores();
		Profile genomeY = reader.getItem(Y).getGenomeScores();
		Set<Integer> common = genomeX.getCommonIds(genomeY);
		
		double n = 0, dX = 0, dY = 0;
		
		for (Integer item_id: common)
		{
			n += genomeX.getValue(item_id) * genomeY.getValue(item_id);
		}
		
		dX = genomeX.getNorm();
		dY = genomeY.getNorm();
		
		return n / (dX * dY) > 0 ? n / (dX * dY) : 0;
			
	}
}
