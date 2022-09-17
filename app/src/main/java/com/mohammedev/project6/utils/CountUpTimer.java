package com.mohammedev.project6.utils;

import android.os.CountDownTimer;

public abstract class CountUpTimer extends CountDownTimer {
    private static final long INTERVAL_MS = 1000;
    private final long duration;

    public CountUpTimer(long durationMs, long countDownInterval) {
        super(durationMs, countDownInterval);
        this.duration = durationMs;
    }

    public abstract void onTick(int second);

    @Override
    public void onTick(long l) {
        int second = (int) ((duration - l) / 1000);
        onTick(second);
    }

    @Override
    public void onFinish() {
        onTick(duration / 1000);
    }

}
