import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URL;


public class SoundManager {

    private static AudioClip clip1;
    private static AudioClip clip2;
    private static AudioClip clip3;
    private static AudioClip explosionClip;
    private static AudioClip loseLifeClip;
    private static Media loseSfx;
    private static MediaPlayer loseSfxPlayer;

    private static SoundManager soundManagerInstance;

    private SoundManager() {
        URL path1 = getClass().getResource("/beep1.mp3");
        URL path2 = getClass().getResource("/beep2.mp3");
        URL path3 = getClass().getResource("/beep3.mp3");
        URL explosionPath = getClass().getResource("/explode.mp3");
        URL loseLifeClipPath = getClass().getResource("/loseLife.mp3");
        URL loseSfxPath = getClass().getResource("/loseSfx.mp3");


        assert path1 != null;
        assert path2 != null;
        assert path3 != null;
        assert explosionPath != null;
        assert loseLifeClipPath != null;
        assert loseSfxPath != null;

        clip1 = new AudioClip(path1.toString());

        clip2 = new AudioClip(path2.toString());

        clip3 = new AudioClip(path3.toString());

        explosionClip = new AudioClip(explosionPath.toString());

        loseLifeClip = new AudioClip(loseLifeClipPath.toString());

        loseSfx = new Media(loseSfxPath.toExternalForm());

        loseSfxPlayer = new MediaPlayer(loseSfx);
        loseSfxPlayer.setCycleCount(MediaPlayer.INDEFINITE);
    }


    public static SoundManager getSoundManagerInstance() {
        if (soundManagerInstance == null) {
            soundManagerInstance = new SoundManager();
        }
        return soundManagerInstance;

    }

    public static void playClip1(){
        clip1.play();
    }

    public static void playClip2() {
        clip2.play();
    }

    public static void playClip3() {
        clip3.play();
    }

    public static void playExplosionClip() {
        explosionClip.play();
    }

    public static void playLoseLifeClip() {
        loseLifeClip.play();
    }

    public static void playLoseSfx() {
        loseSfxPlayer.play();
    }

    public static void stopLoseSfx() {
        loseSfxPlayer.stop();
    }

}