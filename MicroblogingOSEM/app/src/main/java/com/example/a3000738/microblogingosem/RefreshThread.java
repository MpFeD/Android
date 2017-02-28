package com.example.a3000738.microblogingosem;

/**
 * Created by 3000738 on 15/01/2017.
 */

public class RefreshThread extends Thread{
    private static final String TAG = "RefreshThread";
    private boolean isRunning;
    private RefreshStatusService service;
    private TimelineFragment timelineFragment;

    public RefreshThread(RefreshStatusService service){
        this.service = service;
        this.isRunning = true;
    }

    @Override
    public void run() {
        while(this.isRunning) {
            try {
                service.retrieveStatuses();
            }catch(Exception e){
                isRunning = false;
                e.printStackTrace();
            }
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread(){
        this.isRunning = false;
        // Interrupt the sleep
        this.interrupt();
    }

}
