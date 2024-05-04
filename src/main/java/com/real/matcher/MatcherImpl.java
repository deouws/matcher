package com.real.matcher;

import java.util.*;

import com.real.matcher.externaldb.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MatcherImpl implements Matcher {

  private static final Logger LOGGER = LoggerFactory.getLogger(MatcherImpl.class);

  private final Map<MovieEntryInternal, MovieEntryInternal> internalMoviesMap = new HashMap<>();
  private final static int MOVIESDBCOLUMNSLENGTH = 3;

  /**
   *
   * @param movieDb - the internal movie db
   * @param actorAndDirectorDb - This parameter currently is not used. However, in future if someone wants to implement
   *                           rules for matching based on actors/directors or for some other reason we could use this csv as well.
   */
  public MatcherImpl(CsvStream movieDb, CsvStream actorAndDirectorDb) {
    LOGGER.info("importing database");
      /* only importing movieDb for now **/
    movieDb.getDataRows().forEach(entry -> {
      String[] parts = entry.split(",");
      if (parts.length == MOVIESDBCOLUMNSLENGTH) {
        int id = Integer.parseInt(parts[0]);
        String title = parts[1].replaceAll("\"","");
        String releaseYear = parts[2];
        MovieEntryInternal movieObject = new MovieEntryInternal(id, title.toUpperCase(), releaseYear);
        /*
         * Below could seem odd to put both key and value same in the hashMap. The reason I did it to make sure in future when more
         * metadata is added for MoviesEntryInternal we have them for further processing. If this is too much we could have just put
         * external id as the value for the hashMap*/
        internalMoviesMap.put(movieObject, movieObject);
      }
    });
    LOGGER.info("database imported");
  }

  @Override
  public List<IdMapping> match(DatabaseType databaseType, CsvStream externalDb) {
    DatabaseProcessor databaseProcessor = createProcessor(databaseType);
    return databaseProcessor.matchData(internalMoviesMap, externalDb);
  }

  static DatabaseProcessor createProcessor(DatabaseType databaseType) {
    switch (databaseType) {
      case XBOX:
        return new XboxProcessor();
      case GOOGLE_PLAY:
        return new GooglePlayProcessor();
      case VUDU:
        return new VuduProcessor();
      case AMAZON_INSTANT:
        return new AmazonInstanceProcessor();
      default:
        throw new IllegalArgumentException("Unsupported database type: " + databaseType);
    }
  }
}
