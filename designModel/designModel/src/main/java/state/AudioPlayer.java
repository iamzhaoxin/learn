package state;

import lombok.Data;

/**
 * @Author: 赵鑫
 * @Date: 2022/8/30
 */

@Data
// 音频播放器(AudioPlayer)：上下文content，维护指向状态类实例的引用（用于表示音频播放器当前的状态）
public class AudioPlayer {

    private State state;

    public void changeState(State state){
        this.state=state;
    }

    // *** 当前状态不同，引用的state不同，执行的方法就不用 ***
    public void clickLock(){
        state.clickLock();
    }

    public void clickPlay(){
        state.clickPlay();
    }
}

abstract class State {

    protected AudioPlayer player;

    // 上下文将自身传递给状态构造函数。这可帮助状态在需要时获取一些有用的上下文数据
    State(AudioPlayer player) {
        this.player = player;
    }

    abstract public void clickLock();

    abstract public void clickPlay();

    abstract public void clickNext();

    abstract public void clickPrevious();
}

// 具体状态会实现与上下文状态相关的多种行为。
class LockedState extends State {

    LockedState(AudioPlayer player) {
        super(player);
    }

    @Override
    // 当你解锁一个锁定的播放器时，它可能处于两种状态之一
    public void clickLock() {
        if (player.getState() instanceof PlayingState){
            player.changeState(new PlayingState(player));
        }else {
            player.changeState(new ReadyState(player));
        }
    }

    @Override
    public void clickPlay() {
        // 已锁定，什么也不做
    }

    @Override
    public void clickNext() {
        // 已锁定，什么也不做
    }

    @Override
    public void clickPrevious() {
        // 已锁定，什么也不做
    }
}

class PlayingState extends State{

    PlayingState(AudioPlayer player) {
        super(player);
    }

    @Override
    public void clickLock() {
        player.changeState(new LockedState(player));
    }

    @Override
    public void clickPlay() {

    }

    @Override
    public void clickNext() {

    }

    @Override
    public void clickPrevious() {

    }
}

class ReadyState extends State{

    ReadyState(AudioPlayer player) {
        super(player);
    }

    @Override
    public void clickLock() {

    }

    @Override
    public void clickPlay() {

    }

    @Override
    public void clickNext() {

    }

    @Override
    public void clickPrevious() {

    }
}
