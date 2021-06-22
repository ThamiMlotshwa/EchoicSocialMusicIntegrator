package echoic.linkgenerator.core.unittests;

import echoic.linkgenerator.core.interfaces.ExternalTrackedUrlGenerator;
import echoic.linkgenerator.external.linktracking.LinkError;
import echoic.linkgenerator.external.linktracking.TrackedUrl;
import echoic.linkgenerator.external.linktracking.TrackedUrlHeader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class TrackedUrlGenerator_UnitTests {

    @Autowired
    ExternalTrackedUrlGenerator externalTrackedUrlGenerator;

    @MockBean
    RestTemplate mockRestTemplate;

    static HttpHeaders mockHeaders = mock(HttpHeaders.class);
    static HttpEntity<String> mockRequest = mock(HttpEntity.class);
    static ResponseEntity<TrackedUrlHeader> mockResponse = mock(ResponseEntity.class);

    static TrackedUrlHeader trackedUrlHeader;
    static List<TrackedUrl> urls;
    static TrackedUrl trackedUrl;
    static LinkError linkError;

    @BeforeEach
    void initializeTests()
    {
        linkError = new LinkError();
        linkError.setCode(0L);
        linkError.setMessage("message");
        linkError.setDetails("setDetails");

        trackedUrl = new TrackedUrl();
        trackedUrl.setShortUrl("shortUrl");
        trackedUrl.setLongUrl("longUrl");
        trackedUrl.setLinkId("linkId");
        trackedUrl.setTotalClicks(10L);
        trackedUrl.setLinkError(linkError);

        urls = List.of(trackedUrl);

        trackedUrlHeader = new TrackedUrlHeader();
        trackedUrlHeader.setUrls(urls);

    }


    @Test
    @DisplayName("getTrackedUrl (Successful REST request)")
    void getTrackedUrl_Successful()
    {
        when(mockRestTemplate.postForEntity(
                anyString(), any(HttpEntity.class), any(Class.class)))
        .thenReturn(new ResponseEntity<TrackedUrlHeader>(trackedUrlHeader, HttpStatus.OK));

        Optional<TrackedUrl> optional = externalTrackedUrlGenerator.getTrackedUrl("url");

        assertTrue(optional.isPresent());
        assertEquals(optional.get(), trackedUrl);
    }

    @Test
    @DisplayName("getTrackedUrl (Empty payload, successful REST request)")
    void getTrackedUrl_EmptyPayload()
    {
        when(mockRestTemplate.postForEntity(
                anyString(), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity(null, HttpStatus.OK));

        Optional<TrackedUrl> optional = externalTrackedUrlGenerator.getTrackedUrl("url");

        assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("getTrackedUrl (Empty payload, unsuccessful REST request)")
    void getTrackedUrl_EmptyPayloadUnsuccessfulREST()
    {
        when(mockRestTemplate.postForEntity(
                anyString(), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity(null, HttpStatus.BAD_GATEWAY));

        Optional<TrackedUrl> optional = externalTrackedUrlGenerator.getTrackedUrl("url");

        assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("getTrackedUrl (Present payload, unsuccessful REST request)")
    void getTrackedUrl_UnsuccessfulREST()
    {
        when(mockRestTemplate.postForEntity(
                anyString(), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity(trackedUrlHeader, HttpStatus.BAD_GATEWAY));

        Optional<TrackedUrl> optional = externalTrackedUrlGenerator.getTrackedUrl("url");

        assertFalse(optional.isPresent());
    }

    @Test
    @DisplayName("getTrackedUrl (Wrong payload, successful REST request)")
    void getTrackedUrl_WrongPayload()
    {
        when(mockRestTemplate.postForEntity(
                anyString(), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity("string", HttpStatus.OK));

        assertThrows(Exception.class, () -> externalTrackedUrlGenerator.getTrackedUrl("url"));
    }

    @Test
    @DisplayName("getTrackedUrl (Different error code, successful REST request)")
    void getTrackedUrl_ErrorCode()
    {
        linkError.setCode(12345L);
        when(mockRestTemplate.postForEntity(
                anyString(), any(HttpEntity.class), any(Class.class)))
                .thenReturn(new ResponseEntity(trackedUrlHeader, HttpStatus.OK));
        Optional<TrackedUrl> optional = externalTrackedUrlGenerator.getTrackedUrl("url");

        assertFalse(optional.isPresent());
    }
}