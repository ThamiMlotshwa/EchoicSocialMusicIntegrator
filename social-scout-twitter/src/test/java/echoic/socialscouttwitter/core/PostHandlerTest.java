package echoic.socialscouttwitter.core;

import echoic.socialscouttwitter.core.interfaces.MusicEntityGenerator;
import echoic.socialscouttwitter.core.interfaces.PostHandler;
import echoic.socialscouttwitter.core.interfaces.QueryTokenizer;
import echoic.socialscouttwitter.post.interfaces.Tweeter;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import twitter4j.Status;

import static org.mockito.ArgumentMatchers.any;
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