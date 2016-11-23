import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamImageTransformer;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.util.jh.JHBlurFilter;
import com.github.sarxos.webcam.util.jh.JHFlipFilter;
import uk.co.caprica.vlcj.medialist.MediaListItem;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/11/23.
 */
public class ImageTransformerRotationExample implements WebcamImageTransformer {
    static {
        String name1 = "ace";
        String rtsp1 = "rtsp://admin:12345@192.168.2.68:554/h264/ch1/main/av_stream";
        List<MediaListItem> mediaListItemList=new ArrayList<MediaListItem>();
        mediaListItemList.add(new MediaListItem(name1,rtsp1,new ArrayList<MediaListItem>()));
        Webcam.setDriver(new VlcjDriver(mediaListItemList));
    }

    private final BufferedImageOp filter=new JHFlipFilter(JHFlipFilter.FLIP_90CW);
    public ImageTransformerRotationExample() {
        Dimension size= WebcamResolution.VGA.getSize();
        Webcam webcam=Webcam.getDefault();
        webcam.setViewSize(size);
      //  webcam.setImageTransformer(this);   //旋转90度

        webcam.open();

        JFrame frame=new JFrame("Test");
        WebcamPanel panel=new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setFillArea(true);  //铺满

        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    @Override
    public BufferedImage transform(BufferedImage bufferedImage) {
        return filter.filter(bufferedImage,null);
    }
    public static void main(String args[]){
        new ImageTransformerRotationExample();
    }
}
