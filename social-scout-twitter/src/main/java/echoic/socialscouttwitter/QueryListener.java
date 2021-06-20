package echoic.socialscouttwitter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import twitter4j.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
@Slf4j
public class QueryListener
{
    private TwitterStream twitterStream;
    private RestTemplate restTemplate;
    private Tweeter tweeter;

    public QueryListener(TwitterStream twitterStream, RestTemplate restTemplate, Tweeter tweeter, FilterQuery filterQuery)
    {
        this.twitterStream = twitterStream;
        this.restTemplate = restTemplate;
        this.tweeter = tweeter;

        this.twitterStream.addListener(new StatusListener() {
            @Override
            public void onStatus(Status status) {
                log.info("User @" + status.getUser().getScreenName()
                        + " tweeted \"" + status.getText() +
                        "\" at " + status.getCreatedAt().toString());
                //QueryTokenizer.tokenize(status);
                if (!status.isRetweet()) {
                    String searchString = SearchExtractor.getSearchString(status.getText());

                    if (searchString != null) {
                        MusicEntity song = getMusicEntity(searchString).orElseThrow();
                        log.info("" + song);
                        tweeter.replyWithLink(status, song);
                    }
                }
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

    private Optional<MusicEntity> getMusicEntity(String searchTerm)
    {
        MusicEntity song = null;
        Map<String, String> urlVars = new HashMap<>();
        urlVars.put("searchTerm", searchTerm);
        try {
            ResponseEntity<MusicEntity> response = restTemplate.getForEntity("http://localhost:8081/returnLinks/{searchTerm}",
                    MusicEntity.class, urlVars);
            if (response.hasBody())
            {
                song = response.getBody();
            }
        }
        catch(Exception e)
        {
            log.error(e.getMessage());
        }
        return Optional.ofNullable(song);

    }
}
