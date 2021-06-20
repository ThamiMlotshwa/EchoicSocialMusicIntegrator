package echoic.socialscouttwitter.query;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import twitter4j.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class QueryTokenizer
{
    public static String[] tokenize(Status status)
    {
        String payload = status.getText();
        String[] tokens = StringUtils.substringsBetween(payload , "\'", "\'");
        return tokens;

    }
}
