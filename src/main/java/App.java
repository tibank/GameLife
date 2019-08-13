import com.game.GameLife;
import com.game.GameLifeImpl;
import com.game.LoaderConfigLifeGame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class App {
    private final static Logger log = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {
        LoaderConfigLifeGame loader = new LoaderConfigLifeGame();
        loader.loadConfig();
        GameLife gameLife = new GameLifeImpl(loader);
        gameLife.init();
        log.info("Init stage:\n " + gameLife.toString());

        gameLife.play();
        gameLife.writeToFile();
    }
}
