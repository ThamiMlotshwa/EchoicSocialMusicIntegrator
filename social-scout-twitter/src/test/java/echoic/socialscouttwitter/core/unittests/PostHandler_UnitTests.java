package echoic.socialscouttwitter.core.unittests;

import echoic.socialscouttwitter.core.MusicEntity;
import echoic.socialscouttwitter.core.TokenizationException;
import echoic.socialscouttwitter.core.interfaces.MusicEntityGenerator;
import echoic.socialscouttwitter.core.interfaces.PostHandler;
import echoic.socialscouttwitter.core.interfaces.QueryTokenizer;
import echoic.socialscouttwitter.post.interfaces.Tweeter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import twitter4j.Status;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class PostHandler_UnitTests
{
    @Autowired
    PostHandler postHandler;

    @MockBean
    Tweeter mockTweeter;

    @MockBean
    QueryTokenizer mockQueryTokenizer;

    @MockBean
    MusicEntityGenerator mockMusicEntityGenerator;

    public static Status mockIncomingStatus;

    public static Status mockOutgoingStatus;

    public static MusicEntity musicEntity;

    @BeforeAll
    public static void initializeTests()
    {
        mockIncomingStatus = mock(Status.class);
        mockOutgoingStatus = mock(Status.class);
        musicEntity = new MusicEntity();
        musicEntity.setSongName("songName");
        musicEntity.setAlbumName("albumName");
        musicEntity.setArtistName("artistName");
        musicEntity.setLink("link");

    }

    @Test
    @DisplayName("handlePost (Successful)")
    void testHandlePost_Successful() throws Exception
    {
        when(mockIncomingStatus.getText()).thenReturn("searchString");
        when(mockIncomingStatus.isRetweet()).thenReturn(false);
        when(mockIncomingStatus.getQuotedStatus()).thenReturn(null);

        when(mockQueryTokenizer.getSearchString(any(String.class))).thenReturn("searchString");

        when(mockMusicEntityGenerator.getMusicEntity("searchString"))
                .thenReturn(Optional.of(musicEntity));

        when(mockTweeter.replyWithLink(mockIncomingStatus, musicEntity))
                .thenReturn(Optional.of(mockOutgoingStatus));

        Optional<Status> optional = postHandler.handlePost(mockIncomingStatus);

        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(optional.get(), mockOutgoingStatus);
    }

    @Test
    @DisplayName("handlePost (Search string empty or null unsuccessful)")
    void testHandlePost_SearchStringEmptyOrNullUnSuccessful() throws Exception
    {
        when(mockIncomingStatus.getText()).thenReturn("searchString");
        when(mockIncomingStatus.isRetweet()).thenReturn(false);
        when(mockIncomingStatus.getQuotedStatus()).thenReturn(null);

        when(mockQueryTokenizer.getSearchString(any(String.class))).thenReturn("");

        when(mockMusicEntityGenerator.getMusicEntity("searchString"))
                .thenReturn(Optional.of(musicEntity));

        when(mockTweeter.replyWithLink(mockIncomingStatus, musicEntity))
                .thenReturn(Optional.of(mockOutgoingStatus));

        Optional<Status> optional = postHandler.handlePost(mockIncomingStatus);

        Assertions.assertFalse(optional.isPresent());

        when(mockQueryTokenizer.getSearchString(any(String.class))).thenReturn(null);

        optional = postHandler.handlePost(mockIncomingStatus);

        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("handlePost (Retweet or Quote Unsuccessful)")
    void testHandlePost_RetweetOrQuoteUnsuccessful() throws Exception
    {
        when(mockIncomingStatus.getText()).thenReturn("searchString");
        when(mockIncomingStatus.isRetweet()).thenReturn(true);
        when(mockIncomingStatus.getQuotedStatus()).thenReturn(null);

        when(mockQueryTokenizer.getSearchString(any(String.class))).thenReturn("searchString");

        when(mockMusicEntityGenerator.getMusicEntity("searchString"))
                .thenReturn(Optional.of(musicEntity));

        when(mockTweeter.replyWithLink(mockIncomingStatus, musicEntity))
                .thenReturn(Optional.of(mockOutgoingStatus));

        Optional<Status> optional = postHandler.handlePost(mockIncomingStatus);

        Assertions.assertFalse(optional.isPresent());

        when(mockIncomingStatus.isRetweet()).thenReturn(false);
        when(mockIncomingStatus.getQuotedStatus()).thenReturn(mock(Status.class));

        optional = postHandler.handlePost(mockIncomingStatus);

        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("handlePost (Optional MusicEntity empty Successful)")
    void testHandlePost_EmptyMusicEntitySuccessful() throws Exception
    {
        when(mockIncomingStatus.getText()).thenReturn("token searchString token");
        when(mockIncomingStatus.isRetweet()).thenReturn(false);
        when(mockIncomingStatus.getQuotedStatus()).thenReturn(null);

        when(mockQueryTokenizer.getSearchString(any(String.class))).thenReturn("searchString");

        when(mockMusicEntityGenerator.getMusicEntity("searchString"))
                .thenReturn(Optional.empty());

        when(mockTweeter.replyWithUnavailable(mockIncomingStatus))
                .thenReturn(Optional.of(mockOutgoingStatus));

        Optional<Status> optional = postHandler.handlePost(mockIncomingStatus);

        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(optional.get(), mockOutgoingStatus);
    }

    @Test
    @DisplayName("handlePost (TokenizerException Successful)")
    void testHandlePost_ThrowsTokenizerExceptionSuccessful() throws Exception
    {
        when(mockIncomingStatus.getText()).thenReturn("token searchString token");
        when(mockIncomingStatus.isRetweet()).thenReturn(false);
        when(mockIncomingStatus.getQuotedStatus()).thenReturn(null);

        when(mockQueryTokenizer.getSearchString(any(String.class))).thenThrow(TokenizationException.class);

        when(mockMusicEntityGenerator.getMusicEntity("searchString"))
                .thenReturn(Optional.empty());

        when(mockTweeter.replyWithTokenizationProblem(mockIncomingStatus))
                .thenReturn(Optional.of(mockOutgoingStatus));

        Optional<Status> optional = postHandler.handlePost(mockIncomingStatus);

        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(optional.get(), mockOutgoingStatus);
    }

    @Test
    @DisplayName("handlePost (Misc. RuntimeException Unsuccessful)")
    void testHandlePost_ThrowsRuntimeExceptionSuccessful() throws Exception
    {
        when(mockIncomingStatus.getText()).thenReturn("token searchString token");
        when(mockIncomingStatus.isRetweet()).thenReturn(false);
        when(mockIncomingStatus.getQuotedStatus()).thenReturn(null);

        when(mockQueryTokenizer.getSearchString(any(String.class))).thenReturn("searchString");

        when(mockMusicEntityGenerator.getMusicEntity("searchString"))
                .thenThrow(RuntimeException.class);

        when(mockTweeter.replyWithTokenizationProblem(mockIncomingStatus))
                .thenReturn(Optional.of(mockOutgoingStatus));

        Assertions.assertThrows(Exception.class, () -> postHandler.handlePost(mockIncomingStatus));
    }


}