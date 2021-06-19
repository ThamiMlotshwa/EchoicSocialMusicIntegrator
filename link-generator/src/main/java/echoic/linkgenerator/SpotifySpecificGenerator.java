package echoic.linkgenerator;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import echoic.linkgenerator.external.ExternalMusicEntity;
import echoic.linkgenerator.external.linktracking.TrackedUrl;
import echoic.linkgenerator.external.linktracking.TrackedUrlHeader;
import echoic.linkgenerator.repos.mongo.MusicEntityRepo;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
@Slf4j
public class SpotifySpecificGenerator implements SpecificGenerator
{
    private SpotifyApi spotifyApi;
    private RestTemplate restTemplate;

    @Autowired
    private MusicEntityRepo musicEntityRepo;

    public SpotifySpecificGenerator(SpotifyApi spotifyApi, RestTemplate restTemplate)
    {
        this.spotifyApi = spotifyApi;
        this.restTemplate = restTemplate;
    }

    @Override
    public MusicEntity generateMusicEntity(String searchTerm) {

        MusicEntity musicEntity = null;
        try
        {
            Paging<Track> trackPaging = getSearcher(searchTerm).execute();

            if (trackPaging.getTotal()>0)
            {
                Track track = trackPaging.getItems()[0];
                log.info("Spotify track returned: "+track);
                musicEntity = convertTrackToMusicEntity(track);
            }
        }
        catch (IOException | SpotifyWebApiException | ParseException e)
        {
            log.info(e.toString());
        }

        return musicEntity;

    }

    private SearchTracksRequest getSearcher(String searchTerm)
    {
        return spotifyApi.searchTracks(searchTerm)
                .limit(1)
                .market(CountryCode.ZA)
                .market(CountryCode.GB)
                .market(CountryCode.BR)
                .market(CountryCode.US)
                .market(CountryCode.JP)
                .market(CountryCode.NG)
                .market(CountryCode.ML)
                .build();
    }

    private MusicEntity convertTrackToMusicEntity(Track track)
    {
        ExternalMusicEntity externalMusicEntity = getExternalMusicEntity(track.getExternalUrls().get("spotify"));
        TrackedUrl trackedUrl = getTrackedUrl(externalMusicEntity.getPageUrl());
        MusicEntity musicEntity = new MusicEntity();
        musicEntity.setSongName(track.getName());
        musicEntity.setArtistName(track.getAlbum().getArtists()[0].getName());
        musicEntity.setAlbumName(track.getAlbum().getName());
        musicEntity.setLink(trackedUrl.getShortUrl());
        musicEntity.setClicks(0L);
        musicEntity.setCode(track.getId());
        return musicEntity;
    }

    private ExternalMusicEntity getExternalMusicEntity(String url)
    {
        ExternalMusicEntity externalMusicEntity = restTemplate.getForObject("https://api.song.link/v1-alpha.1/links?url={link}",
                ExternalMusicEntity.class,
                url);

        return externalMusicEntity;
    }

    private TrackedUrl getTrackedUrl(String url)
    {
        HttpHeaders headers = new HttpHeaders();

        String base64Creds = Base64.getEncoder().encodeToString("thami:1280acc1-86b5-4bda-99bc-4a49dbb77813".getBytes());
        headers.set("Authorization", "Basic "+base64Creds);
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> request = new HttpEntity<>("{\"urls\": [{\"long_url\": \"" + url + "\"}]}", headers);
        ResponseEntity<TrackedUrlHeader> trackedUrls = restTemplate.postForEntity("https://tiny.cc/tiny/api/3/urls",
                request,
                TrackedUrlHeader.class);
        TrackedUrlHeader trackedUrlHeader = trackedUrls.getBody();
        TrackedUrl trackedUrl = trackedUrlHeader.getUrls().get(0);
        return trackedUrl;
    }
}
