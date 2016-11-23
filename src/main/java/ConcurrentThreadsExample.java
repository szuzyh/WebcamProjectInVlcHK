/**
 * Created by Leo on 2016/11/23.
 */
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

import com.github.sarxos.webcam.Webcam;
import uk.co.caprica.vlcj.medialist.MediaListItem;


public class ConcurrentThreadsExample {
    static {
//        String name1 = "mov";
//        String rtsp1 = "rtsp://admin:12345@192.168.2.68:554/h264/ch1/main/av_stream";
//        Webcam.setDriver(new VlcjDriver(Arrays.asList(new MediaListItem(name1, rtsp1, new ArrayList<MediaListItem>()))));
        String name2 = "mov";
        String rtsp2 = "rtsp://admin:admin@192.168.2.26:554/h264/ch1/main/av_stream";
        Webcam.setDriver(new VlcjDriver(Arrays.asList(new MediaListItem(name2, rtsp2, new ArrayList<MediaListItem>()))));

    }
    private static AtomicInteger counter = new AtomicInteger(0);

    private static final class Capture extends Thread {

        private static final AtomicInteger number = new AtomicInteger(0);

        public Capture() {
            super("capture-" + number.incrementAndGet());
        }

        @Override
        public void run() {

            Webcam webcam = Webcam.getDefault();
            webcam.open();

            while (true) {

                if (!webcam.isOpen()) {
                    break;
                }

                BufferedImage image = webcam.getImage();
                if (image == null) {
                    break;
                }

                int n = counter.incrementAndGet();
                if (n != 0 && n % 100 == 0) {
                    System.out.println(Thread.currentThread().getName() + ": Frames captured: " + n);
                }
            }
        }
    }

    public static void main(String[] args) throws Throwable {

        /**
         * This example will start several concurrent threads which use single
         * webcam instance.
         */

        int n = Runtime.getRuntime().availableProcessors() * 4;
        for (int i = 0; i < n; i++) {
            System.out.println("Thread: " + i);
            new Capture().start();
        }

        Thread.sleep(5 * 60 * 1000); // 5 minutes

        System.exit(1);
    }
}