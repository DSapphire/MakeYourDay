package com.duan.util;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class RingClock {
	private final int LOOP_COUNT = 5;
	private Clip clip;
	private final String file="res/alarm.wav";
	public RingClock(){
		try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(
                    new File(file));
            clip = AudioSystem.getClip();
            clip.open(ais);
            ais.close();
            final long totalFrames = ais.getFrameLength() * LOOP_COUNT;
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent e) {
                    if(e.getFramePosition() >= totalFrames) {
                    	if(null != clip && clip.isRunning()) {
                            clip.stop();
                        }
                    }
                }
            });
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
	}
	public void startRing(){
		 if(null != clip) {
             clip.setFramePosition(0); 
             clip.loop(LOOP_COUNT);
         }
	}
	public void stopRing(){
		if(null != clip && clip.isRunning()) {
            clip.stop();
        }
	}
}
