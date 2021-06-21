package echoic.socialscouttwitter.post.unittests;

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
class Tweeter_UnitTests {

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

    // Only replyWithLink is tested, as the functional units for the other two methods are the same

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
    @DisplayName("replyWithLink (NullUnsuccessful)")
    void replyWithLink_NullUnsuccessful() throws TwitterException
    {
        when(mockTwitter.updateStatus(any(StatusUpdate.class))).thenReturn(mockOutgoingStatus);
        Optional<Status> optional = tweeter.replyWithLink(null, musicEntity);
        Assertions.assertFalse(optional.isPresent());

        optional = tweeter.replyWithLink(mockIncomingStatus, null);
        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("replyWithLink (Exception Unsuccessful)")
    void replyWithLink_ExceptionUnsuccessful() throws TwitterException
    {
        when(mockTwitter.updateStatus(any(StatusUpdate.class))).thenThrow(TwitterException.class);
        Optional<Status> optional = tweeter.replyWithLink(mockIncomingStatus, musicEntity);
        Assertions.assertFalse(optional.isPresent());
    }
}