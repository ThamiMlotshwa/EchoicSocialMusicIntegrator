package echoic.socialscouttwitter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import twitter4j.*;

@Component
@Slf4j
public class QueryListener
{
    private TwitterStream twitterStream;

    public QueryListener(TwitterStream twitterStream, FilterQuery filterQuery)
    {
        this.twitterStream = twitterStream;
        this.twitterStream.addListener(new StatusListener() {
            @Override
            public void onStatus(Status status) {
                log.info("User @" + status.getUser().getScreenName()
                        + " tweeted " + status.getText() +
                        " at " + status.getCreatedAt().toString());
                QueryTokenizer.tokenize(status);
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

            }

            @Override
            public void onTrackLimitationNotice(int i) {

            }

            @Override
            public void onScrubGeo(long l, long l1) {

            }

            @Override
            public void onStallWarning(StallWarning stallWarning) {

            }

            @Override
            public void onException(Exception e) {

            }
        });
        twitterStream.filter(filterQuery);
    }


}
