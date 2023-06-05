import java.util.List;
import java.util.Objects;

public class Match {

    private static int ID = 0;
    private Team homeTeam;
    private Team awayTeam;
    private List<Goal> goals;

    public Match(Team homeTeam,Team awayTeam ){
        ID++;
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
    }

    public boolean isOppositeTeams(Match other)
    {
        return other.awayTeam == this.homeTeam && other.homeTeam == this.awayTeam;
    }

    @Override
    public int hashCode() {
        return Objects.hash(homeTeam, awayTeam, goals);
    }

    public boolean equals(Match other)
    {
        return other.awayTeam == this.homeTeam && other.homeTeam == this.awayTeam;
    }

    public boolean isSameTeams(Match other){
        return other.awayTeam == this.awayTeam || other.awayTeam == this.homeTeam ||
                other.homeTeam == this.awayTeam ||  other.homeTeam == this.homeTeam;
    }

    //להוסיף מתודה שמוסיפה גול ומעדכנת את כמות הגולים בקבוצה בהתאם
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

    public List<Goal> getGoals() {
        return this.goals;
    }
}
