package echoic.socialscouttwitter.query;

import echoic.socialscouttwitter.core.interfaces.PostHandler;
import echoic.socialscouttwitter.post.interfaces.Tweeter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import twitter4j.*;

@Component
@Slf4j
public class QueryListener
{
    private TwitterStream twitterStream;
    private RestTemplate restTemplate;
    private Tweeter tweeter;
    private PostHandler postHandler;

    public QueryListener(TwitterStream twitterStream,
                         RestTemplate restTemplate,
                         Tweeter tweeter,
                         FilterQuery filterQuery,
                         PostHandler postHandler)
    {
        this.twitterStream = twitterStream;
        this.restTemplate = restTemplate;
        this.tweeter = tweeter;
        this.postHandler = postHandler;

        this.twitterStream.addListener(new StatusListener() {
            @Override
            public void onStatus(Status incomingStatus) {
                log.info("User @" + incomingStatus.getUser().getScreenName()
                        + " tweeted \"" + incomingStatus.getText() +
                        "\" at " + incomingStatus.getCreatedAt().toString());

                Status outgoingStatus = postHandler.handlePost(incomingStatus).orElseThrow();

                log.info(("User @" + outgoingStatus.getUser().getScreenName()
                        + " tweeted \"" + outgoingStatus.getText() +
                        "\" at " + outgoingStatus.getCreatedAt().toString()));
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
