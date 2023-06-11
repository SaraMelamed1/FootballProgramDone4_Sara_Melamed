import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Match {

    private static int ID = 0;
    private Team homeTeam;
    private Team awayTeam;
    private List<Goal> goals;

    public Match(Team homeTeam,Team awayTeam ){
        ID++;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.goals = new ArrayList<>();
    }

    public void play(){

        Random random = new Random();
        //הגרלה לקבוצת הבית
        int goals = random.nextInt(1,Constants.MAX_GOALS +1);
        IntStream.range(1, goals).forEach(i ->{
            createGoal(this.homeTeam , this.awayTeam);
        });
        //הגרלה לקבוצה המתארחת
        goals = random.nextInt(1,Constants.MAX_GOALS +1);
        IntStream.range(1, goals).forEach(i ->{
            createGoal(this.awayTeam, this.homeTeam);
        });

        calculatePlayResult();
    }

    private void calculatePlayResult(){

        int homeGoal = (int) this.goals.stream()
                .filter(goal -> this.homeTeam.isPlayerInTeam(goal.getScorer()))
                .count();

        int awayGoal = this.goals.size() - homeGoal;

        if (homeGoal > awayGoal)
        {
            this.homeTeam.addPoints(Constants.WIN_POINTS);
            System.out.println(homeGoal + " - " + awayGoal + "\n" + this.homeTeam.getName() +" won");
        } else if (awayGoal > homeGoal) {
            this.awayTeam.addPoints(Constants.WIN_POINTS);
            System.out.println(awayGoal + " - " + homeGoal + "\n" + this.awayTeam.getName() +" won");
        }else {
            this.homeTeam.addPoints(Constants.TIE_POINTS);
            this.awayTeam.addPoints(Constants.TIE_POINTS);
            System.out.println(homeGoal + " - " + awayGoal + "\n" + " Tie");
        }

    }

    private void createGoal(Team team ,Team rival)
    {
        Random random = new Random();
        int player = random.nextInt(0,Constants.NUM_OF_PLAYERS_AT_TEAM);
        Player selectedPlayer = team.getPlayers().get(player);
        this.goals.add(new Goal(selectedPlayer));
        team.addGoals();
        rival.addRivalGoals();
    }

    public boolean isOppositeTeams(Match other)
    {
        return other.awayTeam == this.homeTeam && other.homeTeam == this.awayTeam;
    }

    public boolean isSameTeams(Match other){
        return other.awayTeam == this.awayTeam || other.awayTeam == this.homeTeam ||
                other.homeTeam == this.awayTeam ||  other.homeTeam == this.homeTeam;
    }

    public boolean isTeamExist(int teamId)
    {
        return homeTeam.isEqualId(teamId) || awayTeam.isEqualId(teamId);
    }

//    public int numGoalsByTeam(int teamId){
//       if (isTeamExist(teamId)){
//           this.goals.stream().map(goal -> goal.getScorer()).
//           }
//       }
//    }

    public boolean equals(Match other){
      return this.homeTeam == other.awayTeam && this.awayTeam == other.homeTeam;
    }

    public String toString()
    {
        return this.homeTeam.getName() + "----VS----" + this.awayTeam.getName();
    }
}
