package echoic.linkgenerator.core.unittests;

import echoic.linkgenerator.external.ExternalAgnosticMusicEntity;

public interface ExternalAgnosticMusicEntityGenerator extends AgnosticMusicEntityGenerator<ExternalAgnosticMusicEntity>
{
    String getGeneratorHost();
}
