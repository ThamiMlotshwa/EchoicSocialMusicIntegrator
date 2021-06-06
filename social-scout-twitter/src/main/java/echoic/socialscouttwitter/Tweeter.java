package echoic.socialscouttwitter;

import lombok.extern.slf4j.Slf4j;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

@Slf4j
public class Tweeter
{
    /**
     * Twitter4J Twitter instance
     */
    private Twitter twitter;

    public Tweeter(Twitter twitter)
    {
        this.twitter = twitter;
    }

    /***
     * Post a test tweet to ensure configurations were performed correctly
     * @return JSON from test tweet
     */
    public Status postTestTweet()
    {
        String message = "This was tweeted by my developer (@thamsandwiches) from #twitter4j";
        try
        {
            Status post = this.twitter.updateStatus(message);
            return post;
        }
        catch(TwitterException e)
        {
            log.info(e.getErrorMessage());
            return null;
        }

    }

    public Status replyWithLink(Status incomingPost, MusicEntity song)
    {
        String details = "@" + incomingPost.getUser().getScreenName() +"\n" +
                         "Title: " + song.getSongName() +"\n" +
                         "Artist: " + song.getArtistName() +"\n" +
                         "Album: " + song.getAlbumName() +"\n" +
                         song.getLink();

        StatusUpdate response = new StatusUpdate(details);
        long tweetId = incomingPost.getId();
        response.inReplyToStatusId(tweetId);

        try
        {
            Status post = this.twitter.updateStatus(response);
            return post;
        }
        catch(TwitterException e)
        {
            log.info(e.getErrorMessage());
            return null;
        }





    }
}