package hu.unideb.inf.prog2.exam.domain;

import hu.unideb.inf.prog2.exam.input.reader.FileBeolvasas;

import java.util.*;
import java.util.stream.Collectors;
import java.util.Random;

/**
 * Class for handling the {@link MatchStatistics} objects.
 *
 */
public class FootballDatabase {

    /**
     * {@link List} containing the {@link MatchStatistics}.
     */
    public List<MatchStatistics> database;

    public FootballDatabase(){
        this.database = new List<MatchStatistics>() {
            @Override
            public int size() {
                return database.size();
            }

            @Override
            public boolean isEmpty() {
                return database.isEmpty();
            }

            @Override
            public boolean contains(Object o) {
                return database.contains(o);
            }

            @Override
            public Iterator<MatchStatistics> iterator() {
                return database.iterator();
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(MatchStatistics matchStatistics) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends MatchStatistics> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends MatchStatistics> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public MatchStatistics get(int index) {
                return null;
            }

            @Override
            public MatchStatistics set(int index, MatchStatistics element) {
                return null;
            }

            @Override
            public void add(int index, MatchStatistics element) {

            }

            @Override
            public MatchStatistics remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<MatchStatistics> listIterator() {
                return null;
            }

            @Override
            public ListIterator<MatchStatistics> listIterator(int index) {
                return null;
            }

            @Override
            public List<MatchStatistics> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
    }
    /**
     * Prints the entire {@link #database} to the terminals
     */
    public void printDatabase(){
        for(MatchStatistics m : this.database){
            System.out.println(m);
        }
    }

    public String toString(MatchStatistics m) {
        StringBuilder sb = new StringBuilder();

        sb.append("MatchStatistics: {\n" +
                "\tDate and Time: " + m.getMatchDateTime() + "\n" +
                "\tHome Team Statistics: {\n\t" +
                    m.getHomeTeamStatistics().toString() + "\n\t}\n" +
                "\tAway Team Statistics: {\n\t" +
                    m.getAwayTeamStatistics().toString() + "\n\t}\n" +
                "\tResult of match: " + m.getFullTimeResult() + "\n" +
                "\tName of referee: " + m.getReferee() + "\n" +
                "}");
        return sb.toString();
    }

    /**
     * Privodes the number of matches when a given {@link Team} achived a given {@link MatchResult result}.
     *
     * @param teamName The name of the {@link Team}.
     * @param result The {@link MatchResult} of the match.
     *
     * @return The number of matches.
     */
    public long countMatchWhenTeamResult(String teamName,String result){
        return this.database.stream().filter(match -> (match.getHomeTeamStatistics().getTeam().getName().equals(teamName) ||
                            match.getAwayTeamStatistics().getTeam().getName().equals(teamName)) &&
                        match.getFullTimeResult().getResultCode().equals(result))
                        .count();
    }
    /**
     * Decides if the homeTeam or the awayTeam has scored more points.
     *
     * @param m A {@link MatchStatistics} object.
     * @return The higher primitive.
     */
    private int returnMaxGoalFromMatch(MatchStatistics m){
        return Math.max(m.getHomeTeamStatistics().getFullTimeGoals(),
                m.getAwayTeamStatistics().getFullTimeGoals());
    }

    /**
     * Provides detailed informations about the matches where the three highest goal numbers were achived by any of the participating teams.
     *
     */
    public List<MatchStatistics> top3GoalsStatistics(){
        return this.database.stream().sorted(Comparator.comparingInt(this::returnMaxGoalFromMatch).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    /**
     * Provides the {@link List} of {@link Integer}s containing the highest goal numbers that were achived by any of the participating teams
     */
    public List<Integer> top3Goals(){
        return this.database.stream().sorted(Comparator.comparing(this::returnMaxGoalFromMatch).reversed())
                .limit(3)
                .map(match -> returnMaxGoalFromMatch(match))
                .collect(Collectors.toList());
    }

    /**
     * Provides detailed informations about the referees given in the parameter list.
     * Given the parameter list to be empty, the method prints information about all of the referees.
     *
     * @param optionalName Optional parameter to specify the search to any of the referees.
     */
    public void refereeStatistics(String...optionalName){
        class Referee {
            Integer yellow;
            Integer red;
            String name;

            public Referee(Integer yellow, Integer red, String name){
                this.yellow = yellow;
                this.red = red;
                this.name = name;
            }
            public String toString(){
                return "Referee: {\n" +
                        "\tName: " + this.name + "\n" +
                        "\tNumber of yellow cards: " + this.yellow + "\n" +
                        "\tNumber of red cards: " + this.red + "\n" +
                        "}\n";
            }
        }
        ArrayList<Referee> referees = new ArrayList<Referee>();
        for(MatchStatistics m : this.database) {
            boolean inList = false;
            for(Referee r : referees) {//benne van-e már a bíró?
                if (m.getReferee().equals(r.name)){
                    inList = true;
                    r.yellow += m.getHomeTeamStatistics().getDisciplinaryActionStatistics().getYellowCards();
                    r.yellow += m.getAwayTeamStatistics().getDisciplinaryActionStatistics().getYellowCards();
                    r.red += m.getHomeTeamStatistics().getDisciplinaryActionStatistics().getRedCards();
                    r.red += m.getAwayTeamStatistics().getDisciplinaryActionStatistics().getRedCards();
                }
            }
            if(!inList){
                Integer yellow = 0;
                Integer red = 0;
                yellow += m.getHomeTeamStatistics().getDisciplinaryActionStatistics().getYellowCards();
                yellow += m.getAwayTeamStatistics().getDisciplinaryActionStatistics().getYellowCards();
                red += m.getHomeTeamStatistics().getDisciplinaryActionStatistics().getRedCards();
                red += m.getAwayTeamStatistics().getDisciplinaryActionStatistics().getRedCards();
                Referee referee = new Referee(yellow, red, m.getReferee());
                referees.add(referee);
            }
        }
        if(optionalName.length == 0){
            for (Referee r : referees){
                System.out.println(r);
            }
        }
        else{
            int l = 0;
            for (Referee r : referees){
                for(String s : optionalName) {
                    if (s.equals(r.name)){
                        System.out.println(r);
                        l++;
                    }
                }
            }
            if (l < optionalName.length){
                System.out.println(String.format("%d bíró nem szerepelt az adatbázisban",optionalName.length-l));
            }
        }

    }

    /**
     * Provides a short String containing the given referee's name.
     *
     * @param choosenName The name of the referee.
     */
    public String referee(String choosenName){
        class Referee {
            Integer yellow;
            Integer red;
            String name;

            public Referee(Integer yellow, Integer red, String name){
                this.yellow = yellow;
                this.red = red;
                this.name = name;
            }
            public String toString(){
                return String.format("Given cards: yellow: %d, red: %d",this.yellow,this.red);
            }
        }
        ArrayList<Referee> referees = new ArrayList<Referee>();
        for(MatchStatistics m : this.database) {
            boolean inList = false;
            for(Referee r : referees) {//benne van-e már a bíró?
                if (m.getReferee().equals(r.name)){
                    inList = true;
                    r.yellow += m.getHomeTeamStatistics().getDisciplinaryActionStatistics().getYellowCards();
                    r.yellow += m.getAwayTeamStatistics().getDisciplinaryActionStatistics().getYellowCards();
                    r.red += m.getHomeTeamStatistics().getDisciplinaryActionStatistics().getRedCards();
                    r.red += m.getAwayTeamStatistics().getDisciplinaryActionStatistics().getRedCards();
                }
            }
            if(!inList){
                Integer yellow = 0;
                Integer red = 0;
                yellow += m.getHomeTeamStatistics().getDisciplinaryActionStatistics().getYellowCards();
                yellow += m.getAwayTeamStatistics().getDisciplinaryActionStatistics().getYellowCards();
                red += m.getHomeTeamStatistics().getDisciplinaryActionStatistics().getRedCards();
                red += m.getAwayTeamStatistics().getDisciplinaryActionStatistics().getRedCards();
                Referee referee = new Referee(yellow, red, m.getReferee());
                referees.add(referee);
            }
        }
        for (Referee r : referees){
            if(r.name.equals(choosenName)){
                return r.toString();
            }
        }
        return "Referee not found";
    }

    /**
     * Method that writes randomly generated commands to the input file.
     *
     * @param footballDatabase An object of the {@link FootballDatabase} class.
     * @param n The number of commands to write into the input file.
     */
    public void writeToInputFile(FootballDatabase footballDatabase, int n) {
        ArrayList<String> teams = new ArrayList<>();
        for(Team x : Team.values()){
            teams.add(x.getName());
        }
        ArrayList<String> results = new ArrayList<String>();
        results.add("A");
        results.add("D");
        results.add("H");
        ArrayList<String> referees = new ArrayList<String>(footballDatabase.database.stream().map(MatchStatistics::getReferee).collect(Collectors.toList()));
        Set<String> setReferees = new HashSet<String>(referees);
        if (setReferees.size() > 0){
            Random r = new Random();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < n; i++){

                int rTeam = r.nextInt(teams.size());
                int rResult = r.nextInt(results.size());
                int rReferee = r.nextInt(referees.size());
                String s = String.format("Team: \"%s\"\n" +
                        "Match result: %s\n" +
                        "Referee: \"%s\"\n",teams.get(rTeam),results.get(rResult),referees.get(rReferee));
                sb.append(s);
            }
            FileBeolvasas.toFile(sb.toString(), "inputs.txt");
        }
    }
}