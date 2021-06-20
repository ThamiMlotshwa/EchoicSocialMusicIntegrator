package echoic.linkgenerator.core.interfaces;

import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

public interface SearcherConverter<T> extends Searcher<T>, MusicEntityConverter<T>
{

}
