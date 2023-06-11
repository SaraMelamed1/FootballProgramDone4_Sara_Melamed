import java.util.stream.IntStream;

public class Main {
    public static void main(String[] args) {
       new LeagueManager();
    }

    //      for (int i =0; i<9; i++)
//      {
//         List<Match> round = this.allRounds.get(i);
//         for (int j = 0 ; j< allMatches.size() && round.size()<5; j++)
//         {
//            if (canAddToRound(i,allMatches.get(j)))
//            {
//               round.add(allMatches.get(j));
//            }
//         }
//      }


    //      boolean result = true;
//      if (this.allRounds.get(round)!= null)
//      {
//         for (int i = 0; i< this.allRounds.get(round).size();i++){
//            if (this.allRounds.get(round).get(i).isSameTeams(toAdd))
//            {
//               result =false;
//               break;
//            }
//         }
//      }
//      return result;


    //      List<Match> allMatches = new ArrayList<>();
//      for (int i = 0; i < this.teams.size(); i++) {
//
//         Team currentTeam = this.teams.get(i);
//         for (int j = 0; j < this.teams.size(); j++) {
//
//            if (i != j) {
//               Match match = new Match(currentTeam, this.teams.get(j));
//               allMatches.add(match);
//            }
//         }
//      }

//הסרת כפיליות של משחקים a vs b OR b vs a
//      List<Match> uniqueMatches = allMatches.stream()
//              .filter(match -> match != null)
//              .distinct()
//              .collect(Collectors.toList());
    //** like up
    //      for (int i = 0; i < allMatches.size(); i++) {
//         if (allMatches.get(i) != null) {
//            for (int j = i + 1; j < allMatches.size(); j++) {
//               if (allMatches.get(j) != null) {
//                  if (allMatches.get(i).isOppositeTeams(allMatches.get(j))) {
//                     allMatches.remove(allMatches.get(j));
//                  }
//               }
//
//            }
//
//         }
//
//      }

    //// למה לא עובד? זה לא שם את הקבוצה עם עצמה נכון?
//      List<Match> matches = IntStream.range(0, teams.size() - 1)
//              .flatMap(i -> IntStream.range(i + 1, teams.size())
//                      .mapToObj( j -> new Match(teams.get(i), teams.get(j))))
//              .collect(Collectors.toList());


    //      for (int i = 0; i < this.teams.size(); i++) {
//
//         Team currentTeam = this.teams.get(i);
//         for (int j = 0; j < this.teams.size(); j++) {
//
//            if (i != j) {
//               List<Match> roundMatches = this.allRounds.get(j);
//               if (roundMatches != null) {
//                  Match match = new Match(currentTeam, this.teams.get(j));
//                  roundMatches.add(match);
//               }
//            }
//         }
//      }
}