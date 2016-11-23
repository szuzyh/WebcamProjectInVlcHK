import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import uk.co.caprica.vlcj.medialist.MediaListItem;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/11/23.
 */
//多个设备同时显示
public class MultipleTest {
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
    public static  void main(String args[]){
        JFrame f = new JFrame("MultipleTest");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new GridLayout(0, 3, 1, 1));

        List<WebcamPanel> panels = new ArrayList<WebcamPanel>();

        for (Webcam webcam : Webcam.getWebcams()) {

            WebcamPanel panel = new WebcamPanel(webcam, new Dimension(640, 480), false);
            panel.setDrawMode(WebcamPanel.DrawMode.FIT);
            panel.setFPSLimited(true);
            panel.setFPSLimit(0.2); // 0.2 FPS = 1 frame per 5 seconds
            panel.setBorder(BorderFactory.createEmptyBorder());

            f.add(panel);
            panels.add(panel);
        }

        f.pack();
        f.setVisible(true);

        for (WebcamPanel panel : panels) {
            panel.start();
        }
    }
}
