import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

public class MedianFilterPanel extends JPanel {
    private Mat matImage;
    private JLabel imageLabel;
    public MedianFilterPanel() {
        super();

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


        imageLabel = new JLabel();
        applyFilter();
        setLayout(new BorderLayout());

        add(new JLabel("Median filter Image: "), BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
    }

    public void applyFilter() {
        if (matImage == null){
            return;
        }
        Mat grayImage = new Mat();
        Imgproc.cvtColor(matImage, grayImage, Imgproc.COLOR_BGR2GRAY);

        Mat filteredImage = new Mat();
        int kernelSize = 3;
        Imgproc.medianBlur(grayImage, matImage, kernelSize);

    }

    private void showThresholdedImage() {
        BufferedImage image = matToBufferedImage(matImage);
        imageLabel.setIcon(new ImageIcon(image));
    }


    private BufferedImage matToBufferedImage(Mat mat) {
        MatOfByte matOfByte = new MatOfByte();
        Imgcodecs.imencode(".jpg", mat, matOfByte);
        byte[] byteArray = matOfByte.toArray();
        BufferedImage image = null;
        try {
            image = ImageIO.read(new ByteArrayInputStream(byteArray));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }


    public void setImage(Mat image) {
        this.matImage =  image;
        applyFilter();
        showThresholdedImage();
    }

}
