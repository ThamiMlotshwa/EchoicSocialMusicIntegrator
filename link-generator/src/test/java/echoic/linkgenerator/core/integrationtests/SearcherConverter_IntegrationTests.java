package echoic.linkgenerator.core.integrationtests;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import echoic.linkgenerator.core.MusicEntity;
import echoic.linkgenerator.core.unittests.ExternalAgnosticMusicEntityGenerator;
import echoic.linkgenerator.core.unittests.ExternalTrackedUrlGenerator;
import echoic.linkgenerator.core.unittests.SearcherConverter;
import echoic.linkgenerator.external.ExternalAgnosticMusicEntity;
import echoic.linkgenerator.external.linktracking.TrackedUrl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.event.annotation.BeforeTestMethod;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest

public class SearcherConverter_IntegrationTests
{
    @Autowired
    SearcherConverter<Track> searcherConverter;

    @Autowired
    SpotifyApi spotifyApi;

    @Autowired
    ClientCredentialsRequest clientCredentialsRequest;

    @Autowired
    ExternalAgnosticMusicEntityGenerator agnosticMusicEntityGenerator;

    @Autowired
    ExternalTrackedUrlGenerator trackedUrlGenerator;

    static String FIRST_ARTIST = "Robert Glasper";
    static String FIRST_SONG = "Cherish The Day";
    static String FIRST_ALBUM = "Black Radio";

    static String SECOND_ARTIST = "Volcano Choir";
    static String SECOND_ALBUM = "Repave";
    static String SECOND_SONG = "Keel";

    static Track TRACK;

    @Test
    @DisplayName("getSearchResult (Successful)")
    void getSearchResult_Successful()
    {
        Optional<Track> optional = searcherConverter.getSearchResult("cherish The day robert glasper");
        assertTrue(optional.isPresent());
        Track track = optional.get();
        assertAll(
                () -> assertEquals(track.getName(), FIRST_SONG),
                () -> assertEquals(track.getAlbum().getName(), FIRST_ALBUM),
                () -> assertEquals(track.getAlbum().getArtists()[0].getName(), FIRST_ARTIST)
        );
        TRACK = track;
    }

    @Test
    @DisplayName("getSearchResult (AltSuccessful)")
    void getSearchResult_AltSuccessful()
    {
        Optional<Track> optional = searcherConverter.getSearchResult("Volcano Choir's Keel");
        assertTrue(optional.isPresent());
        Track track = optional.get();
        assertAll(
                () -> assertEquals(track.getName(), SECOND_SONG),
                () -> assertEquals(track.getAlbum().getName(), SECOND_ALBUM),
                () -> assertEquals(track.getAlbum().getArtists()[0].getName(), SECOND_ARTIST)
        );
    }

    @Test
    @DisplayName("getSearchResult (Non-existent song)")
    void getSearchResult_NonExistentSong()
    {
        Optional<Track> optional = searcherConverter.getSearchResult("bkjasfhkfhadhf - asjhbdfhfb");
        assertTrue(optional.isEmpty());
    }

    @Test
    @DisplayName("convertToMusicEntity (Successful) ")
    void convertToMusicEntity_Successful()
    {
        Track track = searcherConverter.getSearchResult("cherish the day robert glasper").orElseThrow();
        assertNotNull(track, "getSearchResult_Successful() was not run before");

        MusicEntity musicEntity = searcherConverter.convertToMusicEntity(track, agnosticMusicEntityGenerator, trackedUrlGenerator);
        assertAll(
                () -> assertEquals(musicEntity.getSongName(), FIRST_SONG),
                () -> assertEquals(musicEntity.getAlbumName(), FIRST_ALBUM),
                () -> assertEquals(musicEntity.getArtistName(), FIRST_ARTIST),
                () -> assertNotNull(musicEntity.getLink()),
                () -> assertNotEquals(musicEntity.getLink(), ""),
                () -> assertNotNull(musicEntity.getCode()),
                () -> assertNotEquals(musicEntity.getCode(), "")
        );
    }

    @Test
    @DisplayName("convertToMusicEntity (Unsuccessful)")
    void convertToMusicEntity_TrackNullUnsuccessful()
    {
        assertThrows(Exception.class, () -> searcherConverter.convertToMusicEntity(null, agnosticMusicEntityGenerator,
                trackedUrlGenerator), "Track cannot be null");
    }
}
