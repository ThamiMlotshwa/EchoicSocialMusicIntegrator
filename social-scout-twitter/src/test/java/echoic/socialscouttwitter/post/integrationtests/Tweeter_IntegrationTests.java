package echoic.socialscouttwitter.post.integrationtests;

import echoic.socialscouttwitter.core.MusicEntity;
import echoic.socialscouttwitter.post.interfaces.Tweeter;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class Tweeter_IntegrationTests
{
    @Autowired
    Tweeter tweeter;

    @Autowired
    Twitter twitter;


}
