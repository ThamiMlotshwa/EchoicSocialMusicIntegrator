package echoic.linkgenerator.core.unittests;

import echoic.linkgenerator.external.linktracking.TrackedUrl;

import java.util.Optional;

public interface TrackedUrlGenerator
{
    Optional<TrackedUrl> getTrackedUrl(String url);
}
