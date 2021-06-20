package echoic.socialscouttwitter.rest;

import echoic.socialscouttwitter.post.TweeterImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Status;

@RestController
public class TweeterRestController
{
    private TweeterImp tweeter;

    @Autowired
    public TweeterRestController(TweeterImp tweeter)
    {
        this.tweeter = tweeter;
    }

    @GetMapping("/test")
    public Status postTestTweet() {
        Status status = tweeter.postTestTweet();
        return status;
    }
}
