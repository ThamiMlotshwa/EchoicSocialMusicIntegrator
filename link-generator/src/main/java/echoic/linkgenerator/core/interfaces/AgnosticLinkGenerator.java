package echoic.linkgenerator.core.interfaces;

import echoic.linkgenerator.core.MusicEntity;

public interface AgnosticLinkGenerator
{
    public MusicEntity generateMusicEntity(String searchTerm, SearcherConverter searcherConverter);
}
