public class Main {
    private static final String TEXT_PATH = "E:\\Documents\\Projects\\InfProt\\Steganography\\res\\text";
    private static final String IMAGE_PATH = "E:\\Documents\\Projects\\InfProt\\Steganography\\res\\";
    private static final String IMAGE_NAME = "cat.png";
    private static final String IMAGE_SPY_NAME = "spycat.png";
    private static final String IMAGE_BIT_NAME = "bitcat.png";

    public static void main(String[] args) {
        var fileManager = new FileManager();
        var imageManager = new ImageManager();

        var byteText = fileManager.readFileToByteArray(TEXT_PATH);

        var image = imageManager.loadImage(IMAGE_PATH + IMAGE_NAME);
        var newImage = imageManager.hideTextInImage(image, byteText);
        imageManager.saveImage(newImage, IMAGE_PATH + IMAGE_SPY_NAME, "png");

        var spyImage = imageManager.loadImage(IMAGE_PATH + IMAGE_SPY_NAME);
        var bitImage = imageManager.getBitImage(spyImage);
        imageManager.saveImage(bitImage, IMAGE_PATH + IMAGE_BIT_NAME, "png");
        var spyText = imageManager.getTextFromImage(spyImage);
        System.out.println(new String(spyText));
    }
}
