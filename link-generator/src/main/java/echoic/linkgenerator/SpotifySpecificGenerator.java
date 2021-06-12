package echoic.linkgenerator;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.ParseException;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Component
@Slf4j
public class SpotifySpecificGenerator implements SpecificGenerator
{
    private SpotifyApi spotifyApi;

    public SpotifySpecificGenerator(SpotifyApi spotifyApi)
    {
        this.spotifyApi = spotifyApi;
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
                .build();
    }

    private MusicEntity convertTrackToMusicEntity(Track track)
    {
        MusicEntity musicEntity = new MusicEntity();
        musicEntity.setSongName(track.getName());
        musicEntity.setArtistName(track.getAlbum().getArtists()[0].getName());
        musicEntity.setAlbumName(track.getAlbum().getName());
        musicEntity.setLink(track.getExternalUrls().get("spotify"));
        return musicEntity;
    }
}
