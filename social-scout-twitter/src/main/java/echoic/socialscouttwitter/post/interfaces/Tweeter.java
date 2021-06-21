package echoic.socialscouttwitter.post.interfaces;

import echoic.socialscouttwitter.core.MusicEntity;
import twitter4j.Status;

import java.util.Optional;

public interface Tweeter {
    Optional<Status> replyWithLink(Status incomingPost, MusicEntity song);

    Optional<Status> replyWithTokenizationProblem(Status incomingPost);

    Optional<Status> replyWithUnavailable(Status incomingPost);

}
