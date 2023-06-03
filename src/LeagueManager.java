import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class LeagueManager {

   private Map<Integer, Team> teamsMap;//צריך למחוק
   private List<Match> games; //צריך למחוק

   private List<Team> teams;

   private Map<Integer,List<Match>> allRounds;

   public LeagueManager(){

      File groups = new File("/src/teams.csv");
      List<String> groupsName = readFromFile(groups);

      this.teams = groupsName.stream().map(Team::new).toList();


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

      return this.games.stream().filter(game -> game.isTeamExist(teamId)).toList();
   }


   //צריך לשנות לא טוב
   public List<Team> findTopScoringTeams(int n) {
      Map<Team, Integer> goalByTeam = this.teamsMap.entrySet().stream()
              .map(teamsMap -> teamsMap.getValue()).toList()
              .stream().collect(Collectors.toMap(
                      team -> team,
                      team -> countGoalByTeam(team.getId())));

      goalByTeam.entrySet().stream().sorted(Map.Entry.comparingByValue());
      List<Team> teams = goalByTeam.entrySet().stream()
              .map(teamIntegerEntry -> teamIntegerEntry.getKey()).toList();
      Collections.reverse(teams);

      return teams.stream().limit(n).toList();
   }

   //צריך לשנות לא טוב
   private int countGoalByTeam(int teamId){
      List<Goal> allGoal = new ArrayList<>();
      Team team = this.teamsMap.get(teamId);
      this.games.stream()
              .filter(game -> game.isTeamExist(teamId))
              .map(game -> game.getGoals()).map(goal -> allGoal.addAll(goal) );

      return allGoal.stream()
              .filter(goal -> team.isPlayerInTeam(goal.getScorer()))
              .toList().size();
   }



}
