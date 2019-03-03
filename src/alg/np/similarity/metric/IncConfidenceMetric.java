/**
 * Compute the similarity between two items based on increase in confidence
 */ 

package alg.np.similarity.metric;

import java.util.Map;
import java.util.Set;

import profile.Profile;
import util.reader.DatasetReader;

public class IncConfidenceMetric implements SimilarityMetric
{
	private static double RATING_THRESHOLD = 4.0; // the threshold rating for liked items 
	private DatasetReader reader; // dataset reader
	
	/**
	 * constructor - creates a new IncConfidenceMetric object
	 * @param reader - dataset reader
	 */
	public IncConfidenceMetric(final DatasetReader reader)
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
		// calculate similarity using conf(X => Y) / conf(!X => Y) (22 lines)
		Map<Integer,Profile> users_p = reader.getUserProfiles();
		Set<Integer> users = users_p.keySet();
		
		double sX = 0, sXY = 0, sNX = 0, sNXY = 0;
		double cXY = 0.0, cNXY = 0.0, res = 0.0;
		
		for(Integer user_id: users)
		{
			if (users_p.get(user_id).contains(X))
			{
				if (users_p.get(user_id).getValue(X) < 4.0)
					{
						sNX++;
						if (users_p.get(user_id).contains(Y) && users_p.get(user_id).getValue(Y) >= 4.0) sNXY++;
					}
				else if (users_p.get(user_id).getValue(X) >= 4.0)
					{
						sX++;
						if (users_p.get(user_id).contains(Y) && users_p.get(user_id).getValue(Y) >= 4.0) sXY++;
					}
			}
		}
		if (sX == 0) cXY = 0;
		else cXY = sXY / sX;
		if (sNX == 0) cNXY = 0;
		else cNXY = sNXY / sNX;
		if (cNXY == 0)  res= 0;
		else res = cXY / cNXY;

		return res;
	}
}
