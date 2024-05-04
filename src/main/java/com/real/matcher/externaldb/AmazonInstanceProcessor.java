package com.real.matcher.externaldb;

import com.real.matcher.Matcher;
import com.real.matcher.MovieEntryInternal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Processor that needs to be implemented for AMAZON_INSTANT csv files. This is currently not implemented.
 */
public class AmazonInstanceProcessor implements DatabaseProcessor {
    @Override
    public List<Matcher.IdMapping> matchData(Map<MovieEntryInternal, MovieEntryInternal> internalMoviesMap, Matcher.CsvStream externalDb){
        return Collections.emptyList();
    }
}
