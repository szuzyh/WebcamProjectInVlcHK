/**
 * Created by Leo on 2016/11/23.
 */
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import uk.co.caprica.vlcj.medialist.MediaListItem;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;



public class Main {

    static {
//        String name1 = "mov";
//        String rtsp1 = "rtsp://admin:12345@192.168.2.68:554/h264/ch1/main/av_stream";
//        Webcam.setDriver(new VlcjDriver(Arrays.asList(new MediaListItem(name1, rtsp1, new ArrayList<MediaListItem>()))));
        String name2 = "mov";
        String rtsp2 = "rtsp://admin:admin@192.168.2.26:554/h264/ch1/main/av_stream";
        Webcam.setDriver(new VlcjDriver(Arrays.asList(new MediaListItem(name2, rtsp2, new ArrayList<MediaListItem>()))));

    }

    public static void main(String[] args) throws InterruptedException, IOException {
//获取实例
        Webcam webcam = Webcam.getWebcams().get(0);
        webcam.setViewSize(WebcamResolution.VGA.getSize());
//抓取图片并保存
        webcam.open();
        BufferedImage image=webcam.getImage();
        ImageIO.write(image,"JPEG",new File("E:\\hi.jpg"));
        //桌面应用实时显示视频
        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setImageSizeDisplayed(true);
        JFrame window = new JFrame("Webcam Panel");
        window.add(panel);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);
    }
}