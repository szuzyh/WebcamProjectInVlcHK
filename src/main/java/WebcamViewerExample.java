import com.github.sarxos.webcam.*;
import uk.co.caprica.vlcj.medialist.MediaListItem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/11/23.
 */

//可选择显示某台设备的实时图像
public class WebcamViewerExample extends JFrame implements Runnable, WebcamDiscoveryListener, WindowListener, ItemListener, WebcamListener, Thread.UncaughtExceptionHandler {

    static {
        String name1 = "ace";
        String rtsp1 = "rtsp://admin:12345@192.168.2.68:554/h264/ch1/main/av_stream";
        String name2 = "26";
        String rtsp2 = "rtsp://admin:admin@192.168.2.26:554/h264/ch1/main/av_stream";
//多个视频实时图像
        List<MediaListItem> mediaListItemList=new ArrayList<MediaListItem>();
        mediaListItemList.add(new MediaListItem(name1,rtsp1,new ArrayList<MediaListItem>()));
        mediaListItemList.add(new MediaListItem(name2,rtsp2,new ArrayList<MediaListItem>()));
        Webcam.setDriver(new VlcjDriver(mediaListItemList));
    }

    private static final long serialVersionUID = 1L;
    private Webcam webcam = null;
    private WebcamPanel panel = null;
    private WebcamPicker picker = null;


    @Override
    public void run() {
        Webcam.addDiscoveryListener(this);
        setTitle("Test Viewer");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        addWindowListener(this);

        picker=new WebcamPicker();
        picker.addItemListener(this);

        webcam=picker.getSelectedWebcam();
        if (webcam==null){
            System.out.println("No Webcam Found");
            System.exit(1);
        }
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        webcam.addWebcamListener(WebcamViewerExample.this);

        panel=new WebcamPanel(webcam,false);
        panel.setFPSDisplayed(true);

        add(picker,BorderLayout.NORTH);
        add(panel,BorderLayout.CENTER);

        pack();
        setVisible(true);
        Thread t=new Thread(){
            @Override
            public void run() {
                panel.start();
            }
        };
        t.setName("Start");
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();
    }

    public static void main(String args[]){
        SwingUtilities.invokeLater(new WebcamViewerExample());
    }
    @Override
    public void webcamFound(WebcamDiscoveryEvent webcamDiscoveryEvent) {

    }

    @Override
    public void webcamGone(WebcamDiscoveryEvent webcamDiscoveryEvent) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {
        webcam.close();
    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("window pause");
        panel.pause();
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("window resume");
        panel.resume();
    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getItem()!=webcam){
            if (webcam!=null){
                panel.stop();
                remove(panel);

                webcam.removeWebcamListener(this);
                webcam.close();

                webcam= (Webcam) e.getItem();
                webcam.setViewSize(WebcamResolution.VGA.getSize());
                webcam.addWebcamListener(this);
                System.out.println("select "+webcam.getName());

                panel=new WebcamPanel(webcam,false);
                panel.setFPSDisplayed(true);

                add(picker,BorderLayout.NORTH);
                add(panel,BorderLayout.CENTER);

                pack();
                setVisible(true);
                Thread t=new Thread(){
                    @Override
                    public void run() {
                        panel.start();
                    }
                };
                t.setName("Start");
                t.setDaemon(true);
                t.setUncaughtExceptionHandler(this);
                t.start();
            }
        }
    }

    @Override
    public void webcamOpen(WebcamEvent webcamEvent) {
        System.out.println("webcam open");
    }

    @Override
    public void webcamClosed(WebcamEvent webcamEvent) {
        System.out.println("webcam close");
    }

    @Override
    public void webcamDisposed(WebcamEvent webcamEvent) {
        System.out.println("webcam dispose");
    }

    @Override
    public void webcamImageObtained(WebcamEvent webcamEvent) {

    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(String.format("Exception in thread %s",t.getName()));
        e.printStackTrace();
    }
}
