package hu.unideb.inf.prog2.exam;

import hu.unideb.inf.prog2.exam.domain.MatchStatistics;
import hu.unideb.inf.prog2.exam.domain.FootballDatabase;
import hu.unideb.inf.prog2.exam.input.parser.MatchStatisticsCSVParser;
import hu.unideb.inf.prog2.exam.input.reader.FileBeolvasas;
import hu.unideb.inf.prog2.exam.input.reader.MatchStatisticsReader;
import hu.unideb.inf.prog2.exam.input.supplier.InputDataSupplier;

public class Main {

    private static final String INPUT_FILE_PATH = "premier_2019-2020.csv";
    private static final String INPUT_FILE2_PATH = "inputs.txt";

    private static final MatchStatisticsCSVParser MATCH_STATISTICS_CSV_PARSER = new MatchStatisticsCSVParser();
    private static final MatchStatisticsReader MATCH_STATISTICS_READER = new MatchStatisticsReader(MATCH_STATISTICS_CSV_PARSER);
    private static final InputDataSupplier INPUT_DATA_SUPPLIER = new InputDataSupplier(MATCH_STATISTICS_CSV_PARSER);

    public static void main(String[] args) {

        FootballDatabase footballDatabase = new FootballDatabase();
        footballDatabase.database = MATCH_STATISTICS_READER.read(INPUT_FILE_PATH);

        //footballDatabase.printDatabase();

        System.out.println("HÁNYSZOR GYŐZÖTT HOME TEAM MIKOR ARSENAL JÁTSZOTT? " + footballDatabase.countMatchWhenTeamResult("Arsenal","H"));
        System.out.println("TOP 3 GÓL STATS: ");
        for(MatchStatistics m : footballDatabase.top3GoalsStatistics()){
            System.out.println(m);
        }
        System.out.println("Bírók listája: ");
        footballDatabase.refereeStatistics("J Moss", "S Attwell");
        footballDatabase.writeToInputFile(footballDatabase, 10);
        System.out.println("--------------------FROM FILE STARTS--------------------");
        FileBeolvasas.runFromFile(footballDatabase, INPUT_FILE2_PATH);
        System.out.println("--------------------FROM FILE ENDS--------------------");/**/
    }
}