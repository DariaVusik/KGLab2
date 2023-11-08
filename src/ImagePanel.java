import org.opencv.core.Core;
import org.opencv.core.Mat;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ImagePanel extends JPanel {
    private JLabel imageLabel;
    private Mat matImage;


    public ImagePanel() {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


        imageLabel = new JLabel();



        setLayout(new BorderLayout());



        add(new JLabel("Original Image: "), BorderLayout.NORTH);
        add(imageLabel, BorderLayout.CENTER);


    }

    private void showImage() {
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

    public void setMatImage(Mat matImage) {
        this.matImage = matImage.clone();
        showImage();
    }
}