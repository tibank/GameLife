import com.game.GameLife;
import com.game.GameLifeImpl;
import com.game.LoaderConfigLifeGame;

class App {

    public static void main(String[] args) {
        LoaderConfigLifeGame loader = new LoaderConfigLifeGame();
        loader.loadConfig();
        GameLife gameLife = new GameLifeImpl(loader);
        gameLife.init();
        gameLife.play();
        gameLife.writeToFile();
    }
}
