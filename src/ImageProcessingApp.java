import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class ImageProcessingApp {
    private JFrame frame;
    private JButton loadImageButton;
    private ImagePanel imagePanel;
    private OtsuMethodPanel otsuMethodPanel;
    private GradientMethodPanel gradientMethodPanel;
    private MedianFilterPanel medianFilterPanel;
    public ImageProcessingApp() {

        frame = new JFrame("Image Processing App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        loadImageButton = new JButton("Load Image");

        imagePanel = new ImagePanel();
        otsuMethodPanel = new OtsuMethodPanel();
        gradientMethodPanel = new GradientMethodPanel();
        medianFilterPanel = new MedianFilterPanel();
        JPanel filterPanel = new JPanel();
        filterPanel.setLayout(new GridLayout(2, 2));
        filterPanel.add(imagePanel);
        filterPanel.add(otsuMethodPanel);
        filterPanel.add(gradientMethodPanel);
        filterPanel.add(medianFilterPanel);
        frame.add(loadImageButton, BorderLayout.NORTH);
        frame.add(filterPanel, BorderLayout.CENTER);


        loadImageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                JFileChooser fileChooser = new JFileChooser(new File("C:\\Projects\\Dasha projects\\Java\\KGLab2\\src\\images"));
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();


                    Mat image = loadImage(filePath);


                    imagePanel.setMatImage(image);
                    otsuMethodPanel.setImage(image);
                    gradientMethodPanel.setImage(image);
                    medianFilterPanel.setImage(image);

                }
            }
        });


        frame.setVisible(true);
    }

    private Mat loadImage(String filePath) {

        Mat image = null;

        try {

            image = Imgcodecs.imread(filePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return image;
    }



    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ImageProcessingApp();
            }
        });
    }
}