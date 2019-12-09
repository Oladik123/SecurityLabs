import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

class ImageManager {
    private int length;

    ImageManager() {
    }

    BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path));
        } catch (IOException e) {
            throw new RuntimeException("Error while opening image");
        }
    }

    void saveImage(BufferedImage image, String name, String format) {
        try {
            ImageIO.write(image, format, new File(name));
        } catch (Exception ex) {
            System.err.println("Error in ImageManager " + ex.toString());
        }
    }

    BufferedImage hideTextInImage(BufferedImage image, byte[] text) {
        var imageHeight = image.getHeight();
        var imageWidth = image.getWidth();
        if (text.length * 8 > imageHeight * imageWidth) {
            throw new RuntimeException("Image size not enough for hiding text");
        }
        length = text.length;
        var curX = 0;
        var curY = 0;
        for (byte curByte : text) {
            for (var offset = 7; offset >= 0; offset--) {
                var color = new Color(image.getRGB(curX, curY));
                var lsdVal = (curByte >>> offset) & 1;
                var byteRed = (byte) (color.getRed() & 0xFE);
                var newByteRed =  (byteRed | lsdVal);
                image.setRGB(curX, curY, new Color(newByteRed, color.getGreen(), color.getBlue()).getRGB());
                curX++;
                if (curX == imageWidth) {
                    curX = 0;
                    curY++;
                }
            }
        }
        return image;
    }

    BufferedImage getBitImage(BufferedImage image) {
        var imageHeight = image.getHeight();
        var imageWidth = image.getWidth();
        for (var y = 0; y < imageHeight; y++) {
            for (var x = 0; x < imageWidth; x++) {
                var color = new Color(image.getRGB(x, y));
                var greenBit = color.getGreen() & 1;
                var redBit = color.getRed() & 1;
                var blueBit = color.getBlue() & 1;
                image.setRGB(x, y, new Color(redBit == 1 ? 255 : 0,
                        greenBit == 1 ? 255 : 0,
                        blueBit == 1 ? 255 : 0).getRGB());
            }
        }
        return image;
    }

    byte[] getTextFromImage(BufferedImage image) {
        var imageWidth = image.getWidth();
        var text = new byte[length];
        var curX = 0;
        var curY = 0;
        for (var curByte = 0; curByte < length; curByte++) {
            for (var offset = 0; offset < 8; offset++) {
                var color = new Color(image.getRGB(curX, curY));
                var red = (byte) color.getRed();
                text[curByte] = (byte) ((text[curByte] << 1) | (red & 1));
                curX++;
                if (curX == imageWidth) {
                    curX = 0;
                    curY++;
                }
            }
        }
        return text;
    }
}
