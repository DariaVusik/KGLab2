import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private JLabel imageLabel;
    private Mat image;


    public ImagePanel() {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


        imageLabel = new JLabel();



        setLayout(new BorderLayout());



        add(new JLabel("Original Image: "), BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);


    }

   private void showImage() {
        BufferedImage image = matToBufferedImage(this.image);
        imageLabel.setIcon(new ImageIcon(image));
    }

    private BufferedImage matToBufferedImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage image = new BufferedImage(mat.cols(), mat.rows(), type);
        byte[] data = new byte[mat.cols() * mat.rows() * (int) mat.elemSize()];
        mat.get(0, 0, data);
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);
        return image;
    }

    public void setImage(Mat image) {
        this.image = image.clone();
        showImage();
    }
}