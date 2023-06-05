import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

public class Team {

    private static int ID = 0;
    private String name;
    private List<Player> players;
    private int countGoals;

    public Team(String name){
        this.name = name;
        this.players = new ArrayList<>();
        this.players = IntStream
                .range(0, Constants.PLAYERS_NUM).mapToObj(i -> new Player()).toList();
    }


    public boolean isEqualId(int id){
        return ID == id;
    }

    public boolean isExistPlayer(Player player){
        return this.players.stream().anyMatch(player1 -> player1.equals(player));
    }

    public void setGoals(int add){
        this.countGoals+=add;
    }

    public boolean isPlayerInTeam(Player player){
       return this.players.stream().filter(player1 -> player1 == player).toList().size() == 1;
    }

    public int getId() {
        return ID;
    }
}
