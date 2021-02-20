package com.khahani.extractor;

public class HumanMessageValidation implements Runnable {
    private final long HUMAN_WRITING_SPEED_MILLISECOND = 250L;
    private long firstTime;
    private long secondTime;
    private boolean isHuman = false;

    public boolean isHuman() {
        return isHuman;
    }

    public void setFirstTime(long firstTime) {
        this.firstTime = firstTime;
    }

    public void setSecondTime(long secondTime) {
        this.secondTime = secondTime;
    }

    @Override
    public void run() {
        compare();
    }

    private void compare() {
        if (firstTime > secondTime) {
            long temp = firstTime;
            firstTime = secondTime;
            secondTime = temp;
        }

        isHuman = secondTime - firstTime < HUMAN_WRITING_SPEED_MILLISECOND;
    }


}
