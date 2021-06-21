package echoic.socialscouttwitter.core;

public class SearchExtractor
{
    public static String getSearchString(String post) throws TokenizationException
    {
        int index = post.indexOf("\uD83C\uDFB6");

        // No tokens found
        if (index == -1)
            throw new TokenizationException("No tokens founds");

        String substring = post.substring(index+2);
        index = substring.indexOf("\uD83C\uDFB6");

        // Only one token found
        if (index == -1)
            throw new TokenizationException("One token found");

        // Successfully tokinized
        String searchString = substring.substring(0, index);
        searchString = searchString.strip();

        if (searchString.isEmpty())
            throw new TokenizationException("Empty search string");

        return searchString;
    }


}

