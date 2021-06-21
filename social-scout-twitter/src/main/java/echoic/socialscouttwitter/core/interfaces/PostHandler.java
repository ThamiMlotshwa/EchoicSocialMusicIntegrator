package echoic.socialscouttwitter.core.interfaces;

import twitter4j.Status;

import java.util.Optional;

public interface PostHandler
{
    Optional<Status> handlePost(Status incomingPost);
}
