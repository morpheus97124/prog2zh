package hu.unideb.inf.prog2.exam.input.reader;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import hu.unideb.inf.prog2.exam.domain.MatchStatistics;
import hu.unideb.inf.prog2.exam.input.parser.MatchStatisticsCSVParser;

/**
 * Class for reading football match statistics.
 */
public class MatchStatisticsReader {

    private final MatchStatisticsCSVParser matchStatisticsCSVParser;

    public MatchStatisticsReader(MatchStatisticsCSVParser matchStatisticsCSVParser) {
        this.matchStatisticsCSVParser = matchStatisticsCSVParser;
    }

    /**
     * Reads a text file on the specified path
     * into a list of {@link MatchStatistics}.
     *
     * @param path file path where the input file is expected to be
     * @return the list of read {@link MatchStatistics}
     */
    public List<MatchStatistics> read(String path) {
        StringBuilder inp = new StringBuilder();
        boolean fileAvailable = true;
        try{
            inp.append(FileBeolvasas.fromFileBufferedVersionIgnoreFirstLine(path));
        }catch (IOException e){
            System.out.println(String.format("IOException: File is not available.\nMessage: %s",e.getMessage()));
            fileAvailable = false;
        }

        String RAW_INPUT = inp.toString();
        if (fileAvailable){
            return Arrays.stream(inp.toString().split("\n"))
                    .map(matchStatisticsCSVParser::parse)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        else{
            return List.of();
        }
    }
}