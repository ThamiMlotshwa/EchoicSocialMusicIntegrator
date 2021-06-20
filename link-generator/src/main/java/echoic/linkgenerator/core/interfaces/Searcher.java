package echoic.linkgenerator.core.interfaces;

import java.util.Optional;

public interface Searcher<T>
{
    Optional<T> getSearchResult(String searchTerm);
}
