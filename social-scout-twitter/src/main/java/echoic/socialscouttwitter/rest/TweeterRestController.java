package echoic.socialscouttwitter.rest;

import echoic.socialscouttwitter.post.TweeterImp;
import echoic.socialscouttwitter.post.interfaces.Tweeter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import twitter4j.Status;

@RestController
public class TweeterRestController
{
    private Tweeter tweeter;

    @Autowired
    public TweeterRestController(Tweeter tweeter)
    {
        this.tweeter = tweeter;
    }

}
