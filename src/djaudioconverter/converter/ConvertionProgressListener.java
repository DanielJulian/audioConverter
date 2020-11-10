package djaudioconverter.converter;

import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.progress.EncoderProgressListener;

public class ConvertionProgressListener implements EncoderProgressListener {

    private volatile double progress = 0;

    public ConvertionProgressListener() {
    }

    public void message(String m) {
    }

    public void progress(int p) {
        progress = p / 10.00;
    }

    public void sourceInfo(MultimediaInfo m) {
    }

    public double getProgress() {
        return progress;
    }

}
