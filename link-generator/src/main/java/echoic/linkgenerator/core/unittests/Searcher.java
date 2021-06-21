package echoic.linkgenerator.core.unittests;

import java.util.Optional;

public interface Searcher<T>
{
    Optional<T> getSearchResult(String searchTerm);
}
