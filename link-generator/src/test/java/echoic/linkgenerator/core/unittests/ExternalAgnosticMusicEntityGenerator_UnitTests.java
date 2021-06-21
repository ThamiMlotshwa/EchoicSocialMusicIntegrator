package echoic.linkgenerator.core.unittests;

import echoic.linkgenerator.external.ExternalAgnosticMusicEntity;
import echoic.linkgenerator.external.Platforms;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class ExternalAgnosticMusicEntityGenerator_UnitTests {

    @Autowired
    ExternalAgnosticMusicEntityGenerator externalAgnosticMusicEntityGenerator;

    @MockBean
    RestTemplate mockRestTemplate;

    static ResponseEntity<ExternalAgnosticMusicEntity> mockResponse;

    static ExternalAgnosticMusicEntity externalAgnosticMusicEntity;


    @BeforeAll
    static void initializeTests()
    {
        mockResponse = mock(ResponseEntity.class);

        externalAgnosticMusicEntity = new ExternalAgnosticMusicEntity();
        externalAgnosticMusicEntity.setEntityUniqueId("id");
        externalAgnosticMusicEntity.setPageUrl("pageUrl");
        externalAgnosticMusicEntity.setLinksByPlatform(new Platforms());

    }

    @Test
    void getGeneratorHost() {
    }

    @Test
    @DisplayName("getAgnosticMusicEntity (Successful REST request)")
    void getAgnosticMusicEntity_Successful()
    {
        when(mockRestTemplate.getForEntity(
                anyString(), any(Class.class), anyString())
        ).thenReturn(new ResponseEntity(externalAgnosticMusicEntity, HttpStatus.OK));

        Optional<ExternalAgnosticMusicEntity> optional = externalAgnosticMusicEntityGenerator.getAgnosticMusicEntity("url");

        assertTrue(optional.isPresent());
        assertEquals(optional.get(), externalAgnosticMusicEntity);

    }

    @Test
    @DisplayName("getAgnosticMusicEntity (Empty payload Successful REST request)")
    void getAgnosticMusicEntity_EmptySuccessful()
    {
        when(mockRestTemplate.getForEntity(
                anyString(), any(Class.class), anyString())
        ).thenReturn(new ResponseEntity(null, HttpStatus.OK));

        Optional<ExternalAgnosticMusicEntity> optional = externalAgnosticMusicEntityGenerator.getAgnosticMusicEntity("url");

        assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("getAgnosticMusicEntity (Empty payload Successful REST request)")
    void getAgnosticMusicEntity_EmptyUnsuccessful()
    {
        when(mockRestTemplate.getForEntity(
                anyString(), any(Class.class), anyString())
        ).thenReturn(new ResponseEntity(null, HttpStatus.BAD_GATEWAY));

        Optional<ExternalAgnosticMusicEntity> optional = externalAgnosticMusicEntityGenerator.getAgnosticMusicEntity("url");

        assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("getAgnosticMusicEntity (Uuccessful REST request)")
    void getAgnosticMusicEntity_UnsuccessfulRest()
    {
        when(mockRestTemplate.getForEntity(
                anyString(), any(Class.class), anyString())
        ).thenReturn(new ResponseEntity(externalAgnosticMusicEntity, HttpStatus.BAD_GATEWAY));

        Optional<ExternalAgnosticMusicEntity> optional = externalAgnosticMusicEntityGenerator.getAgnosticMusicEntity("url");

        assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("getAgnosticMusicEntity (Uuccessful REST request)")
    void getAgnosticMusicEntity_WrongPayLoad()
    {
        when(mockRestTemplate.getForEntity(
                anyString(), any(Class.class), anyString())
        ).thenReturn(new ResponseEntity("stringValue", HttpStatus.OK));

        assertThrows(Exception.class, () -> externalAgnosticMusicEntityGenerator.getAgnosticMusicEntity("url"));
    }
}