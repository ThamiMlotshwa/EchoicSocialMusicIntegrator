package echoic.socialscouttwitter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
public class Querier
{
    private Twitter twitter;

    public Querier(Twitter twitter)
    {
        this.twitter = twitter;
    }

    /**
     * Pull tweets with a specified key-term and post them to the logs
     * @param keyTerm term that will be listened for
     * @param maxNoTweets maximum number of tweets to be queried
     */
    public List<Status> queryKeyTerm(String keyTerm, int maxNoTweets)
    {
        Query query = new Query(keyTerm);
        query.setCount(maxNoTweets);
        query.setSince("2020-05-23");

        try
        {
            QueryResult result = twitter.search(query);
            List<Status> tweets = result.getTweets();

            if (tweets.size() > 0)
            {
                for (Status tweet : tweets)
                {
                    log.info("User: @" + tweet.getUser().getScreenName() +
                            " said '" + tweet.getText() + "'");
                }
            }
            else
                log.info("No tweets returned");
            return tweets;
        }
        catch(TwitterException e)
        {
            log.info(e.getErrorMessage());
            return new ArrayList<Status>();
        }
    }

}
