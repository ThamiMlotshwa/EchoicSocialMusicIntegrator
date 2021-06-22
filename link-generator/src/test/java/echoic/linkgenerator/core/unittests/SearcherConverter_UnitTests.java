package echoic.linkgenerator.core.unittests;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import echoic.linkgenerator.core.MusicEntity;
import echoic.linkgenerator.core.interfaces.ExternalAgnosticMusicEntityGenerator;
import echoic.linkgenerator.core.interfaces.ExternalTrackedUrlGenerator;
import echoic.linkgenerator.core.interfaces.SearcherConverter;
import echoic.linkgenerator.external.ExternalAgnosticMusicEntity;
import echoic.linkgenerator.external.linktracking.TrackedUrl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
class SearcherConverter_Track_UnitTests
{
    @Autowired
    SearcherConverter<Track> searcherConverter;

    @MockBean
    SpotifyApi spotifyApi;

    @MockBean
    ClientCredentialsRequest mockClientCredentialsRequest;

    @MockBean
    ExternalAgnosticMusicEntityGenerator mockAgnosticMusicEntityGenerator;

    @MockBean
    ExternalTrackedUrlGenerator mockTrackedUrlGenerator;

    static Track mockTrack = mock(Track.class, RETURNS_DEEP_STUBS);
    static Paging<Track> mockPaging = mock(Paging.class, RETURNS_DEEP_STUBS);
    static SearchTracksRequest mockSearchTracksRequest = mock(SearchTracksRequest.class);
    static Track[] mockItems = new Track[]{mockTrack};
    static ExternalAgnosticMusicEntity mockAgnosticMusicEntity = mock(ExternalAgnosticMusicEntity.class);
    static TrackedUrl mockTrackedUrl = mock(TrackedUrl.class);
    static MusicEntity musicEntity;

    @BeforeAll
    static void setUp()
    {
        musicEntity = new MusicEntity();
        musicEntity.setSongName("songName");
        musicEntity.setAlbumName("albumName");
        musicEntity.setArtistName("artistName");
        musicEntity.setClicks(0L);
        musicEntity.setCode("code");
        musicEntity.setLink("link");
    }

    @Disabled("Cannot work around static factory method.")
    @Test
    @DisplayName("convertToM (Successful)")
    void getSearchResult_Successful() throws Exception
    {

        when(mockSearchTracksRequest.execute()).thenReturn(mockPaging);
        when(mockPaging.getTotal()).thenReturn(mockItems.length);
        when(mockPaging.getItems()).thenReturn(mockItems);

        Optional<Track> optional = searcherConverter.getSearchResult("searchTerm");

        assertTrue(optional.isPresent());
        assertEquals(optional.get(), mockTrack);

    }

    @Disabled("Cannot work around static factory method.")
    @Test
    @DisplayName("convertToMusicEntity")
    void convertToMusicEntity_Successful()
    {
        when(mockAgnosticMusicEntityGenerator.getAgnosticMusicEntity(anyString())).thenReturn(
                Optional.of(mockAgnosticMusicEntity));
        when(mockTrackedUrlGenerator.getTrackedUrl(anyString())).thenReturn(Optional.of(mockTrackedUrl));
        when(mockTrack.getName()).thenReturn(musicEntity.getSongName());
        when(mockTrack.getAlbum());
        when(mockTrack.getAlbum().getArtists()[0].getName()).thenReturn(musicEntity.getArtistName());
        when(mockTrack.getAlbum().getName()).thenReturn(musicEntity.getAlbumName());
        when(mockTrackedUrl.getShortUrl()).thenReturn(musicEntity.getLink());
        when(mockTrack.getId()).thenReturn(musicEntity.getCode());

        MusicEntity newMusicEntity = null;
        newMusicEntity = searcherConverter.convertToMusicEntity(mockTrack, mockAgnosticMusicEntityGenerator,
                mockTrackedUrlGenerator);

        assertNotNull(newMusicEntity);
        assertEquals(newMusicEntity, musicEntity);
    }


}