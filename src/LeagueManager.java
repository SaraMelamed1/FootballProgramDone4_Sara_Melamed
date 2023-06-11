import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class LeagueManager {

   private List<Team> teams;

   private Map<Integer, List<Match>> allRounds;

   private int nowRound;

   public LeagueManager() {
      this.teams = new ArrayList<>();
      this.allRounds = new HashMap<>();
      this.nowRound = 1;
      createTeams();
      createAllRounds();
      startLeague();

   }

   //TODO צריך להוסיף תפריט אחרי כל סיבוב מחזור
   private void startLeague(){
      //הפעלת כלל המשחקים במחזורים בתכנות רגיל
//      for (int i = 0; i<this.allRounds.size();i++)
//      {
//         System.out.println("ROUND " + (i+1));
//         List<Match> round = this.allRounds.get(i);
//
//         for (int j = 0; j< round.size();j++)
//         {
//            Match match = round.get(j);
//            countSeconds();
//            match.play();
//         }
//      }

      IntStream.range(0, this.allRounds.size())
              .forEach(i -> {
                 System.out.println("ROUND " + (i + 1));
                 List<Match> round = this.allRounds.get(i);

                 round.forEach(match -> {
                    System.out.println(match);
//                    countSeconds();
                    match.play();
                 });
                 System.out.println("-------------------------");
                 System.out.println("League Table:");
                 System.out.println(calculateLeagueTable());
                 System.out.println("-------------------------");
                 this.nowRound++;
                 System.out.println("**************************");
//                 menu();
                 System.out.println("**************************");
              });

   }

   private void menu(){
      Scanner scanner = new Scanner(System.in);
      printFirstMenu();
      int firstChoose ,secondChoose;

//      AtomicInteger c= new AtomicInteger(2);
//      IntStream.range(1, c.get()).forEach(i->{
//         firstChoose = Stream.generate(scanner::nextInt).findFirst().get();
//         if (!isEnterValid(firstChoose))
//         {
//            c.getAndIncrement();
//         }
//      });
      do {
         firstChoose = scanner.nextInt();
      }while (!isEnterValid(firstChoose) || !isValidFirstChoose(firstChoose));

      printSecondMenuByChoose(firstChoose);
      do {
         secondChoose = scanner.nextInt();
      }while (!isEnterValid(secondChoose) || !isValidSecondChoose(firstChoose,secondChoose));

      resultMenu(firstChoose,secondChoose);
   }

   private void resultMenu(int firstChoose ,int secondChoose){
      if (firstChoose == Constants.FIND_MATCHES_BY_TEAM)
      {
         List<Match> result = findMatchesByTeam(secondChoose);
         System.out.println(result);

      } else if (firstChoose == Constants.FIND_TOP_SCORING_TEAMS)
      {
         List<Team> result = findTopScoringTeams(secondChoose);
         System.out.println(result);
      } else if (firstChoose == Constants.FIND_PLAYERS_N_GOALS)
      {
         List<Player> result = findPlayersWithAtLeastNGoals(secondChoose);
         System.out.println(result);
      } else if (firstChoose == Constants.FIND_TEAM_BY_POSITION)
      {
          Team result = getTeamByPosition(secondChoose);
         System.out.println(result);
      } else if (firstChoose == Constants.FIND_TOP_SCORERS)
      {
         List<Player> result = createReverseListTopScorers().stream().limit(secondChoose).toList();
         System.out.println(result);
      }
   }

   private void printSecondMenuByChoose(int choose){

      if (choose == Constants.FIND_MATCHES_BY_TEAM)//להגביל ל10
      {
        for (int i = 0; i<this.teams.size(); i++)
        {
           System.out.println("press " + (i+1) + " to "+ this.teams.get(i));
        }

      } else if (choose == Constants.FIND_TOP_SCORING_TEAMS) //להגביל ל10
      {
         System.out.println("Enter num of teams:");
      } else if (choose == Constants.FIND_PLAYERS_N_GOALS)
      {
         System.out.println("Enter num of goals:");
      } else if (choose == Constants.FIND_TEAM_BY_POSITION) //להגביל ל10
      {
         System.out.println("Enter num of position:");
      } else if (choose == Constants.FIND_TOP_SCORERS) //להגביל ל150
      {
         System.out.println("Enter num of players:");
      }
   }

   private boolean isValidSecondChoose(int firstChoose, int secondChoose){
      boolean result = true;

      if(firstChoose == Constants.FIND_MATCHES_BY_TEAM ||
              firstChoose == Constants.FIND_TOP_SCORING_TEAMS||
              firstChoose == Constants.FIND_TEAM_BY_POSITION){
         if (secondChoose <1||secondChoose>10){
            System.out.println("Please choose number between 1 TO 10");
            result = false;
         }
      } else if (firstChoose == Constants.FIND_TOP_SCORERS) {
         if (secondChoose <1||secondChoose>150){
            System.out.println("Please choose number between 1 TO 150");
            result = false;
         }
      }
      return result;
   }
   private boolean isEnterValid(int choose){
      boolean result = true;
      if ( !isAllNum(String.valueOf(choose))){
         System.out.println("Please write only Numbers");
         result = false;
      }
      return result;
   }
   private boolean isValidFirstChoose(int choose){
      boolean result = true;
      if (choose <1||choose>5){
         System.out.println("Please choose number between 1 TO 5");
         result = false;
      }
      return result;
   }

   private boolean isAllNum(String number) {
      boolean only = false;
      int counter = 0;
      for (int i = 0; i < number.length(); i++) {
         if (Character.isDigit(number.charAt(i)))
            counter++;
      }
      if (counter == number.length())
         only = true;
      return only;
   }

   private void printFirstMenu(){
      System.out.println("press 1 to find Matches By Team \n " +
              "press 2 to find Top Scoring Teams \n " +
              "press 3 to find Players With At Least N Goals \n" +
              "press 4 to get Team By Position in League table \n" +
              "press 5 to get Top Scorers");

   }
   private List<Team> calculateLeagueTable()
   {
      List<Team> result = new ArrayList<>();
      Map<Team, Integer> sortedPoints= sortByPoints();

      if (isHaveSameValues(sortedPoints))
      {  Map<Team, Integer> sortedGoalDifference = sortByGoalDifference();
         result = convertMapToReverseList(sortedGoalDifference);
         if (isHaveSameValues(sortedGoalDifference)){
           result= sortByTeam();
            Collections.reverse(result);
            System.out.println("name");
         }
         System.out.println("הפרש");
      }else {
         result = convertMapToReverseList(sortedPoints);
      }
      return result;
   }


   private <T> List<T> convertMapToReverseList(Map<T, Integer> map){
      List<T> matches = new ArrayList<>(map.entrySet()
              .stream().map(Map.Entry::getKey).toList());
      Collections.reverse(matches);
      return matches;
   }
   private List<Team> sortByTeam(){
      return this.teams.stream()
              .sorted(Comparator.comparing(Team::getName))
              .collect(Collectors.toList());
   }
   private boolean isHaveSameValues(Map<Team, Integer> map){
      boolean result = true;
      List<Integer> values = map.entrySet().stream().map(teamIntegerEntry -> teamIntegerEntry.getValue() ).toList();
      List<Integer> distinctValues = values.stream().distinct().toList();
      if (values.size() == distinctValues.size())
      {
         result = false;
      }
      return result;
   }


   private Map<Team, Integer> sortByGoalDifference(){
      Map<Team, Integer> goalDifferenceByTeam = this.teams.stream()
              .collect(Collectors.toMap(team -> team, Team::getGoalDifference));
      return sortByValueMap(goalDifferenceByTeam);
   }

   private Map<Team, Integer> sortByPoints()
   {
      Map<Team, Integer> pointsByTeam = this.teams.stream()
              .collect(Collectors.toMap(team -> team, Team::getPoints));
      return sortByValueMap(pointsByTeam);
   }

   private <T>  Map< T , Integer>  sortByValueMap  (Map< T, Integer> map){
      return map.entrySet().stream()
              .sorted(Map.Entry.comparingByValue())
              .collect(Collectors.toMap(
                      Map.Entry::getKey,
                      Map.Entry::getValue,
                      (oldValue, newValue) -> oldValue,
                      LinkedHashMap::new
              ));
   }

   private void createTeams(){
      File groups = new File("src/teams.csv");
      List<String> groupsName = readFromFile(groups);

      this.teams = groupsName.stream().map(Team::new).toList();

   }

   private void createAllRounds(){
      this.allRounds = new HashMap<>();
      IntStream.range(0, Constants.ROUNDS).forEach(i -> this.allRounds.put(i, new ArrayList<>()));
//יצירת כל האפשרויות של משחקים עם הקבוצות
      List<Match> allMatches = this.teams.stream()
              .flatMap(currentTeam -> this.teams.stream()
                      .filter(team -> !team.equals(currentTeam))
                      .map(opponentTeam -> new Match(currentTeam, opponentTeam)))
              .collect(Collectors.toList());


//הסרת כפיליות של משחקים a vs b OR b vs a

      List<Match> uniqueMatches = new ArrayList<>();
       IntStream.range(0,allMatches.size()).forEach(i -> {
               IntStream.range(i+1 ,allMatches.size())
               .forEach(j -> {
                  if (allMatches.get(i).equals(allMatches.get(j)))
                     uniqueMatches.add(allMatches.get(i));
               });});

      //חלוקה ל5 קבוצות בכל סיבוב בלי כפל קבוצה בליגה
      IntStream.range(0, Constants.ROUNDS)
              .forEach(roundIndex -> {
                 List<Match> round = this.allRounds.get(roundIndex);
                 allMatches.stream()
                         .filter(match -> round.size() < Constants.NUM_MATCHES_EVERY_ROUND && canAddToRound(roundIndex, match))
                         .limit(Constants.NUM_MATCHES_EVERY_ROUND - round.size())
                         .forEach(round::add);
              });
   }
   private void countSeconds()
   {
      IntStream.range(0, Constants.GAME_SECONDS )
                    .forEach(i -> {
                       Utils.sleep(Constants.SECOND);
                       System.out.println(Constants.GAME_SECONDS - i );
                    });
   }

   private boolean canAddToRound(int round, Match toAdd){
      return this.allRounds.get(round) == null ||
              this.allRounds.get(round).stream()
                      .noneMatch(match -> match.isSameTeams(toAdd));
   }



   private List<String> readFromFile(File file) {
      List<String> lines = new ArrayList<>();
      if (file != null && file.exists())
      {
         BufferedReader bufferedReader = null;
         FileReader fileReader = null;
         try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();
            while (line != null)
            {
               lines.add(line);
               line = bufferedReader.readLine();
            }
         } catch (Exception e) {
            e.printStackTrace();
         } finally {
            try {
               if (bufferedReader != null)
                  bufferedReader.close();
               if (fileReader != null)
                  fileReader.close();
            } catch (IOException e) {
               throw new RuntimeException(e);
            }
         }
      }
      return lines;
   }

   private List<Match> findMatchesByTeam(int teamId){
      List<Match> allMatch = new ArrayList<>();
     IntStream.range(0, this.nowRound).forEach(i -> allMatch.addAll(this.allRounds.get(i)));
     return allMatch.stream().filter(match -> match.isTeamExist(teamId)).toList();

   }

   private List<Team> findTopScoringTeams(int n) {

      Map<Team,Integer> mapByGoal = this.teams.stream()
              .collect(Collectors.toMap(team -> team, Team::getNumGoals));
      mapByGoal = sortByValueMap(mapByGoal);
      List<Team> result = convertMapToReverseList(mapByGoal);
      return result.stream().limit(n).toList();

   }


   private List<Player> findPlayersWithAtLeastNGoals(int n){
      Map<Player, Integer> playerMap = createPlayerMapByGoals();
      playerMap =playerMap.entrySet().stream()
              .filter(playerIntegerEntry -> playerIntegerEntry.getValue()>=n)
              .collect(Collectors.toMap(Map.Entry::getKey,
                      Map.Entry::getValue));
      playerMap = sortByValueMap(playerMap);
      return convertMapToReverseList(playerMap);


   }

   //להגביל את המיקום ל10
   private Team getTeamByPosition(int position){
       return calculateLeagueTable().get(position-1);
   }

   //להגביל את N ל150
   private Map<Integer, Integer> getTopScorers(int n){
         List< Player> players = createReverseListTopScorers();
         return players.stream().limit(n)
                 .collect(Collectors
                         .toMap(Player::getId, Player::getGoals));
   }

   private List<Player> createReverseListTopScorers(){
      Map<Player, Integer> playerByGoals = createPlayerMapByGoals();
      playerByGoals = sortByValueMap(playerByGoals);
      return convertMapToReverseList(playerByGoals);
   }
   private Map<Player, Integer> createPlayerMapByGoals(){
      List<Player> allPlayers = new ArrayList<>();
      IntStream.range(0,this.teams.size()).forEach(i ->  allPlayers.addAll(this.teams.get(i).getPlayers()));
      return allPlayers.stream()
              .collect(Collectors.toMap(player -> player, Player::getGoals));
   }

}
