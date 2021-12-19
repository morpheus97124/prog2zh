package hu.unideb.inf.prog2.exam.input.reader;

import hu.unideb.inf.prog2.exam.domain.FootballDatabase;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class containing the static methods needed for handling the INPUT/OUTPUT files
 */
public class FileBeolvasas {

    /**
     * Reads the file into a {@link String}.
     *
     * @param input Path to the input file.
     * @return the {@link String} containing the text from the input file.
     */
    public static String fromFileBufferedVersion(String input) throws IOException {
        StringBuilder s = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(input));
        String sor;
        while ((sor = bufferedReader.readLine()) != null) {
            s.append(sor + '\n');
        }
        return s.toString();
    }

    /**
     * Reads the file into a {@link String}, with the exception of the first line.
     *
     * @param input Path to the input file.
     * @return the {@link String} containing the text from the input file.
     */
    public static String fromFileBufferedVersionIgnoreFirstLine(String input) throws IOException {
        StringBuilder s = new StringBuilder();
        BufferedReader bufferedReader = new BufferedReader(new FileReader(input));
        String sor;
        bufferedReader.readLine();
        while ((sor = bufferedReader.readLine()) != null) {
            s.append(sor + '\n');
        }
        return s.toString();
    }

    /**
     * Method to write data into a file.
     *
     * @param outputPath Path to the output file.
     */
    public static void toFile(String inputString, String outputPath) {
        try{
            File file = new File(outputPath);
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.append(inputString);
            fileWriter.flush();
            fileWriter.close();
        }catch (IOException e){
            System.out.println(String.format("IOException: File is not available.\nMessage: %s",e.getMessage()));
        }
    }

    /**
     * Method to parse an input file into a {@link List} of {@link String}s,
     * containing commands for the {@link #executeCommands(FootballDatabase, String, String, String) executeCommands} method.
     *
     * @param footballDatabase An object of the {@link FootballDatabase} class.
     * @param path Path to the input file.
     */
    public static void runFromFile(FootballDatabase footballDatabase, String path){
        StringBuilder sb = new StringBuilder();
        try{
            sb.append(FileBeolvasas.fromFileBufferedVersion(path));
        }catch (IOException e){
            System.out.println(String.format("IOException: File is not available.\nMessage: %s",e.getMessage()));
        }
        List<String> commands = Arrays.stream(sb.toString().split("\n")).collect(Collectors.toList());
        for(int i = 0; i < commands.size(); i += 3){
            System.out.println(String.format("COMMAND %d: ",(i/3)+1));
            executeCommands(footballDatabase, commands.get(i), commands.get(i+1), commands.get(i+2));
        }

    }

    /**
     * Method for executing the commands of the input file
     *
     * @param footballDatabase An object of the {@link FootballDatabase} class.
     * @param c1 String containing the name of the {@link hu.unideb.inf.prog2.exam.domain.Team Team}.
     * @param c2 String containing the {@link hu.unideb.inf.prog2.exam.domain.MatchResult MatchResult}.
     * @param c3 String containing the name of the Referee.
     */
    public static void executeCommands(FootballDatabase footballDatabase, String c1, String c2, String c3){
        String cc1 = c1.substring(7,c1.lastIndexOf('"'));
        String cc2 = c2.substring(14,15);
        String cc3 = c3.substring(10,c3.lastIndexOf('"'));
        StringBuilder answer = new StringBuilder();
        System.out.println(c1 + "\n" + c2 + "\n" + c3 + "\n");
        answer.append("Match count: " + footballDatabase.countMatchWhenTeamResult(cc1, cc2) + "\n");
        answer.append("Top 3 goals count: " + footballDatabase.top3Goals() + "\n");
        answer.append(footballDatabase.referee(cc3) + "\n-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o-o");
        System.out.println(answer);
    }
}