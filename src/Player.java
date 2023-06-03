import java.util.Random;

public class Player {

    private static int ID = 0;
    private String firstName;
    private String lastName;

    public Player(){
        ID++;
        Random random = new Random();
        this.firstName = Constants.PLAYERS_FIRST_NAMES[random.nextInt(0,Constants.PLAYERS_FIRST_NAMES.length)];
        this.lastName = Constants.PLAYERS_LAST_NAMES[random.nextInt(0,Constants.PLAYERS_LAST_NAMES.length)];


    }

    public boolean isEqualId(int id){
        return ID == id;

    }

}
