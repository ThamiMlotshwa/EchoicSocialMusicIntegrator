package echoic.linkgenerator.rest;

import echoic.linkgenerator.core.interfaces.AgnosticLinkGenerator;
import echoic.linkgenerator.core.MusicEntity;
import echoic.linkgenerator.core.interfaces.SearcherConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path="/returnLinks", produces="application/json")
@CrossOrigin(origins="*")
public class LinkReturnerRestController
{
    AgnosticLinkGenerator agnosticLinkGenerator;
    SearcherConverter searcherConverter;

    @Autowired
    public LinkReturnerRestController(AgnosticLinkGenerator agnosticLinkGenerator, SearcherConverter searcherConverter)
    {
        this.agnosticLinkGenerator = agnosticLinkGenerator;
        this.searcherConverter = searcherConverter;
    }

    @GetMapping("{searchTerm}")
    public MusicEntity getLinks(@PathVariable("searchTerm") String searchTerm)
    {
        MusicEntity musicEntity = null;
        try
        {
            musicEntity = agnosticLinkGenerator.generateMusicEntity(searchTerm, searcherConverter);
        }
        catch (Exception e)
        {
            throw e;
        }
        finally
        {
            return musicEntity;
        }
    }
}
