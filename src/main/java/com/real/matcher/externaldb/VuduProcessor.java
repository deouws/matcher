package com.real.matcher.externaldb;

import com.real.matcher.Matcher;
import com.real.matcher.MovieEntryInternal;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Processor that needs to be implemented for VUDU csv files. This is currently not implemented.
 */
public class VuduProcessor implements DatabaseProcessor {
    @Override
    public List<Matcher.IdMapping> matchData(Map<MovieEntryInternal, MovieEntryInternal> internalMoviesMap, Matcher.CsvStream externalDb){
        return Collections.emptyList();
    }
}
