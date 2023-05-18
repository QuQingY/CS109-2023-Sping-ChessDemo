package Stream;

import javax.sound.sampled.*;
import java.io.File;

public class Audio {
    public static Thread bgmAudio;
    public static Thread voice;

    public static void playAudio(String name){
        if(bgmAudio!=null&& bgmAudio.isAlive()){
            bgmAudio.interrupt();
        }
        bgmAudio = new Thread(){
            public void run() {
                AudioInputStream stream;
                AudioFormat format;
                try {
//                    stream= AudioSystem.getAudioInputStream(new File("sound/"+name+".wav"));
                    stream= AudioSystem.getAudioInputStream(new File(name));
                }catch(Exception e) {
                    throw new RuntimeException(e);
                }
                format=stream.getFormat();
                DataLine.Info info=new DataLine.Info(SourceDataLine.class,format,AudioSystem.NOT_SPECIFIED);
                SourceDataLine line=null;
                byte buffer[]=new byte[1024];
                int cnt=0;
                try {
                    line=(SourceDataLine)AudioSystem.getLine(info);
                    line.open(format);
                    line.start();
                    while(!isInterrupted()) {
                        cnt=stream.read(buffer,0,buffer.length);
                        if(cnt==-1)
                            break;
                        line.write(buffer,0,cnt);
                    }
                }catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
         bgmAudio.start();
        }

    public static void playVoice(String name){
        if(voice!=null&& voice.isAlive()){
            voice.interrupt();
        }
        voice = new Thread(){
            public void run() {
                AudioInputStream stream;
                AudioFormat format;
                try {
//                    stream= AudioSystem.getAudioInputStream(new File("sound/"+name+".wav"));
                    stream= AudioSystem.getAudioInputStream(new File(name));
                }catch(Exception e) {
                    throw new RuntimeException(e);
                }
                format=stream.getFormat();
                DataLine.Info info=new DataLine.Info(SourceDataLine.class,format,AudioSystem.NOT_SPECIFIED);
                SourceDataLine line=null;
                byte buffer[]=new byte[1024];
                int cnt=0;
                try {
                    line=(SourceDataLine)AudioSystem.getLine(info);
                    line.open(format);
                    line.start();
                    while(!isInterrupted()) {
                        cnt=stream.read(buffer,0,buffer.length);
                        if(cnt==-1)
                            break;
                        line.write(buffer,0,cnt);
                    }
                }catch(Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };
        voice.start();
    }
    }

