import java.io.*;
import java.util.*;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LeagueManager {

   private List<Team> teams;

   private Map<Integer,List<Match>> allRounds;

   public LeagueManager(){

      File groups = new File("src/teams.csv");
      List<String> groupsName = readFromFile(groups);

      this.teams = groupsName.stream().map(Team::new).toList();

      this.allRounds = new HashMap<>();
      IntStream.range(0, 9).forEach(i -> this.allRounds.put(i, new ArrayList<>()));
//יצירת כל האפשרויות של משחקים עם הקבוצות
      List<Match> allMatches = this.teams.stream()
              .flatMap(currentTeam -> this.teams.stream()
                      .filter(team -> !team.equals(currentTeam))
                      .map(opponentTeam -> new Match(currentTeam, opponentTeam)))
              .collect(Collectors.toList());


//הסרת כפיליות של משחקים a vs b OR b vs a

      for (int i =0; i< allMatches.size(); i++){
         if (allMatches.get(i) != null)
         {
            for (int j = i+1; j<allMatches.size(); j++){
               if (allMatches.get(j) != null){
                  if (allMatches.get(i).isOppositeTeams(allMatches.get(j)))
                  {
                     allMatches.remove(allMatches.get(j));
                  }
               }

            }

         }

      }

      IntStream.range(0, 9)
              .forEach(roundIndex -> {
                 List<Match> round = this.allRounds.get(roundIndex);
                 allMatches.stream()
                         .filter(match -> round.size() < 5 && canAddToRound(roundIndex, match))
                         .limit(5 - round.size())
                         .forEach(round::add);
              });


      System.out.println();

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

   //צריך לשנות לא טוב

   public List<Match> findMatchesByTeam(int teamId){

//      return this.games.stream().filter(game -> game.isTeamExist(teamId)).toList();
      return null;
   }


   //צריך לשנות לא טוב
   public List<Team> findTopScoringTeams(int n) {
//      Map<Team, Integer> goalByTeam = this.teamsMap.entrySet().stream()
//              .map(teamsMap -> teamsMap.getValue()).toList()
//              .stream().collect(Collectors.toMap(
//                      team -> team,
//                      team -> countGoalByTeam(team.getId())));
//
//      goalByTeam.entrySet().stream().sorted(Map.Entry.comparingByValue());
//      List<Team> teams = goalByTeam.entrySet().stream()
//              .map(teamIntegerEntry -> teamIntegerEntry.getKey()).toList();
//      Collections.reverse(teams);
//
//      return teams.stream().limit(n).toList();

      return null;
   }

   //צריך לשנות לא טוב
   private int countGoalByTeam(int teamId){
//      List<Goal> allGoal = new ArrayList<>();
//      Team team = this.teamsMap.get(teamId);
//      this.games.stream()
//              .filter(game -> game.isTeamExist(teamId))
//              .map(game -> game.getGoals()).map(goal -> allGoal.addAll(goal) );
//
//      return allGoal.stream()
//              .filter(goal -> team.isPlayerInTeam(goal.getScorer()))
//              .toList().size();

      return 0;
   }



}
