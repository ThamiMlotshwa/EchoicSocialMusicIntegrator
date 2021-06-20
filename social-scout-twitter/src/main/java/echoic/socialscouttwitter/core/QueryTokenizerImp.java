package echoic.socialscouttwitter.core;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class QueryTokenizerImp implements QueryTokenizer {
    public String getSearchString(String post) throws TokenizationException
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
