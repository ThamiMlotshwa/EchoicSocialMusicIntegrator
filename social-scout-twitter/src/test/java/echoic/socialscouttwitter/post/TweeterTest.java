package echoic.socialscouttwitter.post;

import echoic.socialscouttwitter.core.MusicEntity;
import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TweeterTest {

    @Autowired
    Tweeter tweeter;

    @MockBean
    Twitter mockTwitter;

    public static Status mockIncomingStatus;
    public static Status mockOutgoingStatus;
    public static MusicEntity musicEntity = new MusicEntity();

    @BeforeAll
    static void setUp()
    {
        musicEntity.setSongName("songName");
        musicEntity.setArtistName("artistName");
        musicEntity.setAlbumName("albumName");
        musicEntity.setLink("link");

        mockIncomingStatus = mock(Status.class, RETURNS_DEEP_STUBS);
        mockOutgoingStatus = mock(Status.class, RETURNS_DEEP_STUBS);

        when(mockIncomingStatus.getUser().getScreenName()).thenReturn("User");
        when(mockIncomingStatus.getId()).thenReturn(1L);

    }

    @Test
    @DisplayName("replyWithLink (Successful)")
    void replyWithLink_Successful() throws TwitterException
    {
        when(mockTwitter.updateStatus(any(StatusUpdate.class))).thenReturn(mockOutgoingStatus);
        Optional<Status> optional = tweeter.replyWithLink(mockIncomingStatus, musicEntity);
        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(optional.get(), mockOutgoingStatus);

    }

    @Test
    @DisplayName("replyWithLink (Unsuccessful)")
    void replyWithLink_NullUnsuccessful() throws TwitterException
    {
        when(mockOutgoingStatus.getText().contains(musicEntity.getLink())).thenReturn(true);
        when(mockTwitter.updateStatus(any(StatusUpdate.class))).thenReturn(mockOutgoingStatus);
        Optional<Status> optional = tweeter.replyWithLink(null, musicEntity);
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    void replyWithTokenizationProblem() {
    }

    @Test
    void replyWithUnavailable() {
    }
}