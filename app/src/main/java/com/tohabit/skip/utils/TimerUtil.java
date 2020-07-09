package com.tohabit.skip.utils;


import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器工具类
 */
public class TimerUtil {


    private static HashMap<String,Timer> timerHashMap = new HashMap<>();
    private static HashMap<String,TimerTask> timerTaskHashMap = new HashMap<>();

    /**
     * 开始定时任务  只执行一次
     * @param tag
     * @param delay
     * @param timerTaskDoCallBack
     */
    public static void startTimerTask(final String tag, long delay, final TimerTaskDoCallBack timerTaskDoCallBack){

       stopTimerTask(tag);

       Timer timer = new Timer();
       TimerTask timerTask = new TimerTask() {
           @Override
           public void run() {
               timerTaskDoCallBack.taskDo();
               stopTimerTask(tag);
           }
       };

       timerHashMap.put(tag,timer);
       timerTaskHashMap.put(tag,timerTask);

       timer.schedule(timerTask,delay);

    }

    /**
     * 间隔执行
     * @param tag
     * @param delay
     * @param period
     * @param timerTaskDoCallBack
     */
    public static void startTimerTask(final String tag, long delay, long period, final TimerTaskDoCallBack timerTaskDoCallBack){

        stopTimerTask(tag);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                timerTaskDoCallBack.taskDo();
            }
        };

        timerHashMap.put(tag,timer);
        timerTaskHashMap.put(tag,timerTask);

        timer.schedule(timerTask,delay,period);

    }

    /**间隔执行 times 次
     * @param tag
     * @param delay
     * @param period
     * @param times
     * @param timerTaskDoCallBack
     */
    public static void startTimerTask(final String tag, long delay, long period, final int times, final TimerTaskDoCallBack timerTaskDoCallBack){

        final int currentTimes = 0;
        stopTimerTask(tag);

        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if(currentTimes < times){
                    timerTaskDoCallBack.taskDo();
                }else {
                    stopTimerTask(tag);
                }
            }
        };

        timerHashMap.put(tag,timer);
        timerTaskHashMap.put(tag,timerTask);

        timer.schedule(timerTask,delay,period);

    }

    public static void stopTimerTask(String tag){

        Timer timer= timerHashMap.get(tag);
        TimerTask timerTask = timerTaskHashMap.get(tag);

        if (timer != null) {
            timerHashMap.remove(tag);
            timer.cancel();
            timer.purge();
        }
        if (timerTask != null) {
            timerTaskHashMap.remove(tag);
            timerTask.cancel();
        }
    }
}
