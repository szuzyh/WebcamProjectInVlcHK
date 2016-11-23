import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IPixelFormat;
import com.xuggle.xuggler.IVideoPicture;
import com.xuggle.xuggler.video.ConverterFactory;
import com.xuggle.xuggler.video.IConverter;
import uk.co.caprica.vlcj.medialist.MediaListItem;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Leo on 2016/11/23.
 */
public class RecordTest {



    public static void main(String args[]) throws Throwable {
        String name1 = "ace";
        String rtsp1 = "rtsp://admin:12345@192.168.2.68:554/h264/ch1/main/av_stream";
        List<MediaListItem> mediaListItemList=new ArrayList<MediaListItem>();
        mediaListItemList.add(new MediaListItem(name1,rtsp1,new ArrayList<MediaListItem>()));
        Webcam.setDriver(new VlcjDriver(mediaListItemList));

        File file=new File("output.ts");
        IMediaWriter mediaWriter= ToolFactory.makeWriter(file.getName());
        Dimension size= WebcamResolution.QVGA.getSize();
        mediaWriter.addVideoStream(0,0, ICodec.ID.CODEC_ID_H264,size.width,size.height);
        Webcam webcam=Webcam.getDefault();
        webcam.setViewSize(size);
        webcam.open(true);

        long start=System.currentTimeMillis();
        for (int i=0;i<50;i++){
            System.out.println("Capture Frame "+ i);

            BufferedImage image= ConverterFactory.convertToType(webcam.getImage(),BufferedImage.TYPE_3BYTE_BGR);
            IConverter converter=ConverterFactory.createConverter(image, IPixelFormat.Type.YUV420P);

            IVideoPicture videoPicture=converter.toPicture(image,(System.currentTimeMillis()-start)*1000);
            videoPicture.setKeyFrame(i==0);
            videoPicture.setQuality(0);
            mediaWriter.encodeVideo(0,videoPicture);

            Thread.sleep(100);
        }
        mediaWriter.close();
        System.out.println("file path"+file.getAbsolutePath());
    }
}
