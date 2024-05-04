package com.real.matcher.externaldb;

import com.real.matcher.Matcher;
import com.real.matcher.Matcher.CsvStream;
import com.real.matcher.MovieEntryInternal;

import java.util.List;
import java.util.Map;

/**
 * An interface that should be extended by different external processors
 */
public interface DatabaseProcessor {
    /**
     * A function that would map the external db to internal movies db
     * @param internalMoviesMap
     * @param externalDb
     * @return List of id Mappings
     */
    public List<Matcher.IdMapping> matchData(Map<MovieEntryInternal, MovieEntryInternal> internalMoviesMap, CsvStream externalDb);
}
