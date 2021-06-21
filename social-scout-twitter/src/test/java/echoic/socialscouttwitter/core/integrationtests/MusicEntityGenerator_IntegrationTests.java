package echoic.socialscouttwitter.core.integrationtests;

import echoic.socialscouttwitter.core.MusicEntity;
import echoic.socialscouttwitter.core.interfaces.MusicEntityGenerator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.junit.jupiter.api.Disabled;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@Disabled("Must containerize for CI")
@SpringBootTest
class MusicEntityGenerator_IntegrationTests
{
    @Autowired
    MusicEntityGenerator musicEntityGenerator;

    @Autowired RestTemplate restTemplate;

    public static MusicEntity musicEntity;

    @BeforeAll
    static void initializeTests()
    {
        musicEntity = new MusicEntity();
        musicEntity.setSongName("Veridis Quo");
        musicEntity.setAlbumName("Discovery");
        musicEntity.setArtistName("Daft Punk");
    }
    /*
    // Requires LinkGenerator to be running
    @Disabled("Must containerize for CI")
    @Test
    @DisplayName("findById (Real song 1)")
    void testGetMusicEntity_Successful()
    {
        Optional<MusicEntity> optional = musicEntityGenerator.getMusicEntity("Veridis Quo - Daft Punk");

        Assertions.assertTrue(optional.isPresent());
        MusicEntity newMusicEntity = optional.get();
        Assertions.assertEquals(newMusicEntity.getSongName(), musicEntity.getSongName());
        Assertions.assertEquals(newMusicEntity.getAlbumName(), musicEntity.getAlbumName());
        Assertions.assertEquals(newMusicEntity.getArtistName(), musicEntity.getArtistName());
    }

    @Disabled("Must containerize for CI")
    @Test
    @DisplayName("findById (Real song 2)")
    void testGetMusicEntity_BackwardsSuccessful()
    {
        Optional<MusicEntity> optional = musicEntityGenerator.getMusicEntity("Daft Punk - Veridis Quo");

        Assertions.assertTrue(optional.isPresent());
        MusicEntity newMusicEntity = optional.get();
        Assertions.assertEquals(newMusicEntity.getSongName(), musicEntity.getSongName());
        Assertions.assertEquals(newMusicEntity.getAlbumName(), musicEntity.getAlbumName());
        Assertions.assertEquals(newMusicEntity.getArtistName(), musicEntity.getArtistName());
    }

    @Disabled("Must containerize for CI")
    @Test
    @DisplayName("findById (Real song 3)")
    void testGetMusicEntity_AltSuccessful()
    {
        Optional<MusicEntity> optional = musicEntityGenerator.getMusicEntity("Daft Punk's Veridis Quo");

        Assertions.assertTrue(optional.isPresent());
        MusicEntity newMusicEntity = optional.get();
        Assertions.assertEquals(newMusicEntity.getSongName(), musicEntity.getSongName());
        Assertions.assertEquals(newMusicEntity.getAlbumName(), musicEntity.getAlbumName());
        Assertions.assertEquals(newMusicEntity.getArtistName(), musicEntity.getArtistName());
    }*/

    @Disabled("Must containerize for CI")
    @Test
    @DisplayName("findById (Spelling error)")
    void testGetMusicEntity_SpellingUnsuccessful()
    {
        Assertions.assertThrows(Exception.class, () -> musicEntityGenerator
                .getMusicEntity("Daft Punk - Verdis Quo"));
    }

    @Disabled("Must containerize for CI")
    @Test
    @DisplayName("findById (Non-existent song)")
    void testGetMusicEntity_NonExistentUnsuccessful()
    {
        Assertions.assertThrows(Exception.class, () -> musicEntityGenerator
                .getMusicEntity("jklhafhjkweuhfjwef"));
    }

}
