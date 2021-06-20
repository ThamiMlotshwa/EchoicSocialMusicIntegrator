package echoic.socialscouttwitter.core;


import echoic.socialscouttwitter.post.Tweeter;
import lombok.extern.slf4j.Slf4j;
import twitter4j.Status;

import java.util.Optional;

@Slf4j
public class PostHandlerImp implements PostHandler
{
    private Tweeter tweeter;
    private MusicEntityGenerator musicEntityGenerator;

    public PostHandlerImp(Tweeter tweeter, MusicEntityGenerator musicEntityGenerator)
    {
        this.tweeter = tweeter;
        this.musicEntityGenerator = musicEntityGenerator;
    }

    @Override
    public Optional<Status> handlePost(Status incomingPost)
    {
        Optional<Status> outgoingPostOptional = Optional.empty();

        if (!incomingPost.isRetweet() && incomingPost.getQuotedStatus()==null)
        {
            try
            {
                String searchString = SearchExtractor.getSearchString(incomingPost.getText());
                if (searchString != null)
                {
                    Optional<MusicEntity> optionalSong = musicEntityGenerator.getMusicEntity(searchString);
                    if (optionalSong.isPresent())
                    {
                        MusicEntity song = optionalSong.get();
                        log.info("" + song);
                        outgoingPostOptional = tweeter.replyWithLink(incomingPost, song);
                    }
                    else
                    {
                        outgoingPostOptional = tweeter.replyWithUnavailable(incomingPost);
                    }
                }
            }
            catch (TokenizationException e)
            {
                log.info("TokenizationException" + e.getMessage());
                outgoingPostOptional = tweeter.replyWithTokenizationProblem(incomingPost);
            }
            catch (Exception e)
            {
                log.info(e.toString());
                throw e;
            }
        }

        return outgoingPostOptional;
    }
}
