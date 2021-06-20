package echoic.socialscouttwitter.core;

import echoic.socialscouttwitter.post.Tweeter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import twitter4j.Status;
import twitter4j.Twitter;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostHandlerTest
{
    @Autowired
    PostHandler postHandler;

    @MockBean
    QueryTokenizer mockQueryTokenizer;

    @MockBean
    Tweeter mockTweeter;

    @MockBean
    MusicEntityGenerator musicEntityGenerator;

    public static Status mockIncomingStatus;

    @BeforeAll
    public static void initializeTests()
    {
        mockIncomingStatus = mock(Status.class);
    }

    @Test
    @DisplayName("handlePost (Successful)")
    void testHandlePost_Successful()
    {
        when(mockIncomingStatus.getText()).thenReturn("searchString");
        when(mockIncomingStatus.isRetweet()).thenReturn(false);
        when(mockIncomingStatus.getQuotedStatus()).thenReturn(null);


    }


}