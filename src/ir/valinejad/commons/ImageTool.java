package ir.valinejad.commons;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by arash on 4/9/17.
 * a.valinejad@gmail.com
 */
public class ImageTool {

    public static void rotate(String inputFile, String outputFile, int degree) throws Exception{
        BufferedImage oldImage = ImageIO.read(new FileInputStream(inputFile));
        BufferedImage newImage = new BufferedImage(oldImage.getHeight(), oldImage.getWidth(), oldImage.getType());
        Graphics2D graphics = (Graphics2D) newImage.getGraphics();
        graphics.rotate(Math.toRadians(degree), newImage.getWidth() / 2, newImage.getHeight() / 2);
        graphics.translate((newImage.getWidth() - oldImage.getWidth()) / 2, (newImage.getHeight() - oldImage.getHeight()) / 2);
        graphics.drawImage(oldImage, 0, 0, oldImage.getWidth(), oldImage.getHeight(), null);
        ImageIO.write(newImage, "JPG", new FileOutputStream(outputFile));
    }

    public static void main(String[] args) {
        try {
            String inputFile = "/Users/sib/Desktop/1.jpg";
            for (int i=0; i<20; i++) {
                String outputFile = "/Users/sib/Desktop/sdfsdf"+String.valueOf(i)+".jpg";
                rotate(inputFile, outputFile, 90);
                inputFile = new String(outputFile);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
