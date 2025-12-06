// Music Player
interface State{
    void play();
    void pause();
    void stop();
}
class IdleState implements State{
    MediaPlayer mediaPlayer;
    IdleState( MediaPlayer mediaPlayer ){
        this.mediaPlayer = mediaPlayer;
    }
    @Override
    public void play() {
        System.out.println("Setting to Play State!");
        this.mediaPlayer.setState(this.mediaPlayer.getPlayState());
    }

    @Override
    public void pause() {
        System.out.println("Can't Pause. Already Idle!");
    }

    @Override
    public void stop() {
        System.out.println("Can't Stop. Already Idle!");
    }
}

class PlayState implements State{
    MediaPlayer mediaPlayer;
    PlayState( MediaPlayer mediaPlayer ){
        this.mediaPlayer = mediaPlayer;
    }
    @Override
    public void play() {
        System.out.println("Can't set to Play. Already Playing!");
    }

    @Override
    public void pause() {
        System.out.println("Setting to Pause!");
        this.mediaPlayer.setState(this.mediaPlayer.getPauseState());
    }

    @Override
    public void stop() {
        System.out.println("Setting to Stop!");
        this.mediaPlayer.setState(this.mediaPlayer.getIdleState());
    }
}

class PauseState implements State{
    MediaPlayer mediaPlayer;
    PauseState( MediaPlayer mediaPlayer ){
        this.mediaPlayer = mediaPlayer;
    }
    @Override
    public void play() {
        System.out.println("Setting to Play State!");
        this.mediaPlayer.setState(this.mediaPlayer.getPlayState());
    }

    @Override
    public void pause() {
        System.out.println("Can't Pause. Already Paused!");
    }

    @Override
    public void stop() {
        System.out.println("Setting to Stop!");
        this.mediaPlayer.setState(this.mediaPlayer.getIdleState());
    }
}

class MediaPlayer{
    PlayState playState;
    PauseState pauseState;
    IdleState idleState;
    State currentState;
    MediaPlayer(){
        this.idleState = new IdleState(this);
        this.playState = new PlayState(this);
        this.pauseState = new PauseState(this);
        this.currentState = this.idleState;
    }

    public void setState( State state ){
        this.currentState = state;
    }

    public PlayState getPlayState(){
        return this.playState;
    }

    public IdleState getIdleState(){
        return this.idleState;
    }

    public PauseState getPauseState(){
        return this.pauseState;
    }

    public void play(){
        this.currentState.play();
    }

    public void pause(){
        this.currentState.pause();
    }

    public void stop(){
        this.currentState.stop();
    }
}


public class StateDesignPatternExample {
    public static void main(String[] args) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.play();
        mediaPlayer.play();
        mediaPlayer.pause();
        mediaPlayer.stop();
        mediaPlayer.pause();
        mediaPlayer.play();
        mediaPlayer.stop();
        mediaPlayer.stop();
    }
}
