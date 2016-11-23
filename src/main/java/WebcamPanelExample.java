import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import uk.co.caprica.vlcj.medialist.MediaListItem;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/11/23.
 */
//显示某一台设备的实时图像
public class WebcamPanelExample {
    static {

        String name1 = "ace";
        String rtsp1 = "rtsp://admin:12345@192.168.2.68:554/h264/ch1/main/av_stream";
        List<MediaListItem> mediaListItemList=new ArrayList<MediaListItem>();
        mediaListItemList.add(new MediaListItem(name1,rtsp1,new ArrayList<MediaListItem>()));
        Webcam.setDriver(new VlcjDriver(mediaListItemList));
    }
    public static void main(String args[]){
        Webcam webcam=Webcam.getDefault();
        webcam.setViewSize(WebcamResolution.VGA.getSize());
        WebcamPanel panel=new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setDisplayDebugInfo(true);
        panel.setImageSizeDisplayed(true);
        panel.setMirrored(false);

        JFrame frame=new JFrame("Test Panel");
        frame.add(panel);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
