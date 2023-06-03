import java.util.List;

public class Match {

    private static int ID = 0;
    private Team homeTeam;
    private Team awayTeam;
    private List<Goal> goals;

    //להוסיף מתודה שמוסיפה גול ומעדכנת את כמות הגולים בקבוצה בהתאם
    public boolean isTeamExist(int teamId){
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
