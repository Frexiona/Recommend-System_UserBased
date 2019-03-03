/**
 * Compute the similarity between two items based on the Cosine between item ratings
 */ 

package alg.np.similarity.metric;

import java.util.Set;

import profile.Profile;
import util.reader.DatasetReader;

public class RatingMetric implements SimilarityMetric
{
	private DatasetReader reader; // dataset reader

	/**
	 * constructor - creates a new RatingMetric object
	 * @param reader - dataset reader
	 */
	public RatingMetric(final DatasetReader reader)
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
		// calculate similarity using Cosine
		Profile rateX = reader.getItemProfiles().get(X);
		Profile rateY = reader.getItemProfiles().get(Y);
		Set<Integer> common = rateX.getCommonIds(rateY);
		
		double n = 0, dX = 0, dY = 0;
		for (Integer item_id: common)
		{
			n += rateX.getValue(item_id) * rateY.getValue(item_id);
		}
		dX = rateX.getNorm();
		dY = rateY.getNorm();
		
		return n / (dX * dY) > 0 ? n / (dX * dY) : 0;
	}
}
