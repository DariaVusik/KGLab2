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

public class OtsuMethodPanel extends JPanel {

    private Mat matImage;
    private JLabel imageLabel;

    public OtsuMethodPanel() {
        super();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        imageLabel = new JLabel();
        applyThresholding();
        setLayout(new BorderLayout());

        add(new JLabel("Otsu method Image: "), BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);
    }

    public void applyThresholding() {
        if (matImage == null){
            return;
        }

        Mat grayImage = new Mat();
        Imgproc.cvtColor(matImage, grayImage, Imgproc.COLOR_BGR2GRAY);



        matImage = new Mat();
        Imgproc.threshold(grayImage, matImage, 0, 255, Imgproc.THRESH_BINARY | Imgproc.THRESH_OTSU);

        showThresholdedImage();
    }


    private void showThresholdedImage() {
        BufferedImage image = matToBufferedImage(matImage);
        int targetWidth = 300;
        int targetHeight = 300;
        BufferedImage resizedImage = resizeImage(image, targetWidth, targetHeight);

        imageLabel.setIcon(new ImageIcon(resizedImage));
    }

    private BufferedImage resizeImage(BufferedImage image, int targetWidth, int targetHeight) {
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = resizedImage.createGraphics();
        graphics2D.drawImage(image, 0, 0, targetWidth, targetHeight, null);
        graphics2D.dispose();

        return resizedImage;
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
        applyThresholding();
        showThresholdedImage();
    }
}