package com.real.matcher.externaldb;

import com.real.matcher.Matcher;
import com.real.matcher.MatcherImpl;
import com.real.matcher.MovieEntryInternal;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Processor that needs is implemented for Xbox csv files. Currently we are only looking into movies and mapping only based on
 * title and release year. This could be modified in future to map movies based on other meta data or for considering TVSeries or TVEpisode.
 */


public class XboxProcessor implements DatabaseProcessor{

    private static final Logger LOGGER = LoggerFactory.getLogger(XboxProcessor.class);

    /** only matching based on title and year for now **/
    static final int indexTitle = 3;
    static final int indexYear = 4;
    static final int indexId = 2;
    static final int indexMediaType = 5;

    static final String mediaTypeMovie = "Movie";

    static final String releaseDateSplitter = "/";
    static final String releaseYearTimeSplitter = " ";

    @Override
    public List<Matcher.IdMapping> matchData(Map<MovieEntryInternal, MovieEntryInternal> internalMoviesMap, Matcher.CsvStream externalDb){
        LOGGER.info("matching xbox data start");
        List<Matcher.IdMapping> idMappings = new ArrayList<>();
        Set<String> uniqueExternalIds = new HashSet<>();
        // a set to make sure only once IdMapping is created
        externalDb.getDataRows().forEach( entry -> {
            String[] parts = entry.split(",");
            if (parts.length > indexMediaType) {
                String externalMediaId = parts[indexId];
                String mediaType = parts[indexMediaType];
                if (mediaTypeMovie.equalsIgnoreCase(mediaType)) {
                    // It is a movie and therefore try to match
                    String externalTitle = parts[indexTitle];
                    if (externalTitle!=null && !externalTitle.isEmpty()){
                        // not null or empty so have some value
                        String externalYear = "";
                        String externalYearFullFormat = parts[indexYear].trim();
                        /* format is MM/d/yyyy hh:mm:ss a, we need just the year , so just split on "/" and then on " "
                         SimpleDateFormat would be extra processing for not much value I think **/
                        String[] yearAllValues = externalYearFullFormat.split(releaseDateSplitter);
                        if (yearAllValues.length==3){
                            String yearWithTime = yearAllValues[2];
                            String[] yearWithTimeParts = yearWithTime.split(releaseYearTimeSplitter);
                            if (yearWithTimeParts.length>1){
                                externalYear = yearWithTimeParts[0];
                            }
                        }
                        MovieEntryInternal internalMovieEntry = internalMoviesMap.get(new MovieEntryInternal(externalTitle, externalYear));
                        if (internalMovieEntry!=null){
                            // found entry, now create and add IdMapping
                            if (!uniqueExternalIds.contains(externalMediaId)) {
                                idMappings.add(new Matcher.IdMapping(internalMovieEntry.getId(), externalMediaId));
                                LOGGER.info(String.format("Match Found %s %s " , internalMovieEntry.getTitle() , internalMovieEntry.getReleaseYear()));
                                uniqueExternalIds.add(externalMediaId);
                            }
                        }
                    }
                }
            }
        });
        LOGGER.info("matching xbox data ends");
        return idMappings;
    }
}
