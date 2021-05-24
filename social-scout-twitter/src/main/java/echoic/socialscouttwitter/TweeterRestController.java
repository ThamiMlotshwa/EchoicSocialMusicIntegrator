package echoic.socialscouttwitter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @GetMapping("/test")
    public Status postTestTweet() {
        Status status = tweeter.postTestTweet();
        return status;
    }
}
