package djaudioconverter.converter;

import ws.schild.jave.Encoder;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;
import java.nio.file.Paths;
import java.util.concurrent.Callable;

public class MP3Converter implements Callable<Boolean> {

    private File fileToConvert;

    public MP3Converter(File file) {
        this.fileToConvert = file;
    }

    public Boolean call() {
        return convertToMP3();
    }

    private boolean convertToMP3() {
        boolean success = false;
        try {
            File source = new File(fileToConvert.getAbsolutePath());
            File target = new File(Paths.get(fileToConvert.getAbsolutePath()).getParent() + "/" + fileToConvert.getName().split("\\.")[0] + ".mp3");

            //Audio Attributes
            AudioAttributes audio = new AudioAttributes();
            audio.setCodec("libmp3lame");
            audio.setBitRate(128000);
            audio.setChannels(2);
            audio.setSamplingRate(44100);

            //Encoding attributes
            EncodingAttributes attrs = new EncodingAttributes();
            attrs.setAudioAttributes(audio);

            //Encode
            Encoder encoder = new Encoder();
            encoder.encode(new MultimediaObject(source), target, attrs);
            success = true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return success;
    }

}
