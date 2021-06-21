package echoic.socialscouttwitter.core;

import echoic.socialscouttwitter.core.interfaces.MusicEntityGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class MusicEntityGeneratorTest
{
    @Autowired
    MusicEntityGenerator musicEntityGenerator;

    @MockBean
    RestTemplate mockRestTemplate;

    public static MusicEntity musicEntity;

    @BeforeAll
    static void initializeTests()
    {
        musicEntity = new MusicEntity();
        musicEntity.setSongName("songName");
        musicEntity.setAlbumName("albumName");
        musicEntity.setArtistName("artistName");
        musicEntity.setLink("link");
    }
    @Test
    @DisplayName("findById (Successful REST request)")
    void testGetMusicEntity_SuccessfulRest()
    {
        when(mockRestTemplate.getForEntity(
                anyString(), any(Class.class), any(Map.class)))
          .thenReturn(new ResponseEntity(musicEntity, HttpStatus.OK));

        Optional<MusicEntity> optional = musicEntityGenerator.getMusicEntity("songName");

        Assertions.assertTrue(optional.isPresent());
        Assertions.assertEquals(optional.get(), musicEntity);
    }

    @Test
    @DisplayName("findById (Empty payload successful REST request)")
    void testGetMusicEntity_EmptySuccessfulRest()
    {
        when(mockRestTemplate.getForEntity(
                anyString(), any(Class.class), any(Map.class)))
                .thenReturn(new ResponseEntity(null, HttpStatus.OK));

        Optional<MusicEntity> optional = musicEntityGenerator.getMusicEntity("songName");

        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("findById (Empty payload unsuccessful REST request)")
    void testGetMusicEntity_EmptyUnsuccessfulRest()
    {
        when(mockRestTemplate.getForEntity(
                anyString(), any(Class.class), any(Map.class)))
                .thenReturn(new ResponseEntity(null, HttpStatus.BAD_REQUEST));

        Optional<MusicEntity> optional = musicEntityGenerator.getMusicEntity("songName");

        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("findById (Unsuccessful REST request)")
    void testGetMusicEntity_UnsuccessfulRest()
    {
        when(mockRestTemplate.getForEntity(
                anyString(), any(Class.class), any(Map.class)))
                .thenReturn(new ResponseEntity(musicEntity, HttpStatus.BAD_REQUEST));

        Optional<MusicEntity> optional = musicEntityGenerator.getMusicEntity("songName");

        Assertions.assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("findById (Wrong payload successful REST request)")
    void testGetMusicEntity_WrongPayloadSuccessfulRest()
    {
        when(mockRestTemplate.getForEntity(
                anyString(), any(Class.class), any(Map.class)))
                .thenReturn(new ResponseEntity("stringValue", HttpStatus.OK));

        //Optional<MusicEntity> optional = musicEntityGenerator.getMusicEntity("songName");

        Assertions.assertThrows(Exception.class, () -> musicEntityGenerator.getMusicEntity("songName"));
    }
}