package echoic.linkgenerator.core.unittests;

import echoic.linkgenerator.core.MusicEntity;

public interface AgnosticLinkGenerator
{
    public MusicEntity generateMusicEntity(String searchTerm, SearcherConverter searcherConverter);
}
