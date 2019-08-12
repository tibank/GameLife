import com.game.GameLife;
import com.game.GameLifeImpl;

public class App {

    public static void main(String[] args) {
        GameLife gameLife = new GameLifeImpl();
        gameLife.init();
        System.out.println("Init data of game:\n" + gameLife);
    }
}
