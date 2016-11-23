import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.github.sarxos.webcam.WebcamUtils;
import com.github.sarxos.webcam.util.ImageUtils;
import uk.co.caprica.vlcj.medialist.MediaListItem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Leo on 2016/11/23.
 */
public class CustomResolutionExample {
    static {
        String name1 = "ace";
        String rtsp1 = "rtsp://admin:12345@192.168.2.68:554/h264/ch1/main/av_stream";
        List<MediaListItem> mediaListItemList=new ArrayList<MediaListItem>();
        mediaListItemList.add(new MediaListItem(name1,rtsp1,new ArrayList<MediaListItem>()));
        Webcam.setDriver(new VlcjDriver(mediaListItemList));
    }

    public static void main(String args[]){
        Dimension[] dimensions=new Dimension[]{
                WebcamResolution.PAL.getSize(),
                WebcamResolution.HD720.getSize(),
                new Dimension(2000,1000),
                new Dimension(1000,500)
        };

        Webcam webcam=Webcam.getDefault();

        webcam.setCustomViewSizes(dimensions);
        webcam.setViewSize(WebcamResolution.HD720.getSize());
        webcam.open();
  //      WebcamUtils.capture(webcam, new File("E:\\test01.bmp"), ImageUtils.FORMAT_BMP);
        WebcamUtils.capture(webcam, new File("E:\\test02.jpg"), "jpg");
        byte[] bytes = WebcamUtils.getImageBytes(webcam, "jpg");
        System.out.println("Bytes length: " + bytes.length);   //可以返回byte数组
        ByteBuffer buffer = WebcamUtils.getImageByteBuffer(webcam, "jpg");
        System.out.println("Buffer length: " + buffer.capacity()); //返回byteBuffer
        BufferedImage image=webcam.getImage();

        System.out.println(image.getWidth()+"xxx"+image.getHeight());
    }
}
