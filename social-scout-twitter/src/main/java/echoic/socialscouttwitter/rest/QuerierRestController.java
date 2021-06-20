package echoic.socialscouttwitter.rest;

import echoic.socialscouttwitter.query.Querier;
import echoic.socialscouttwitter.query.QueryTokenizer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Status;

import java.util.List;

@RestController
@Slf4j
public class QuerierRestController
{
    private Querier querier;

    @Autowired
    public QuerierRestController(Querier querier)
    {
        this.querier = querier;
    }

    @GetMapping("/getRecent")
    public List<Status> getRecentTweets()
    {

        List<Status> tweets = querier.queryKeyTerm("#echoicmusic", 5);
        if (tweets.size() > 0) {
            QueryTokenizer.tokenize(tweets.get(0));
        }
        else
            log.info("No tweets found");
        return tweets;
    }
}
