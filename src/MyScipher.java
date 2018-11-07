import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class MyScipher {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        String message = sc.nextLine();

        File f = new File("./kitty.jpg");
        BufferedImage new_img = ImageIO.read(f);
        BufferedImage img = new BufferedImage(new_img.getWidth(), new_img.getHeight(), BufferedImage.TYPE_4BYTE_ABGR);
        img.getGraphics().drawImage(img, 0, 0, null);

        coding(message, img);
        decoding(img);


    }

    private static void coding(String message, BufferedImage img) throws IOException {
        byte []array = new byte[message.length() + 1];
        for (int i =0; i < message.length() + 1; i++){
            if (i == 0) array[i] = (byte) message.length();
            else array[i] = (byte) message.charAt(i-1);
        }

    int height = img.getHeight(), len = array.length;

        for(int i = 0; i<height;i++){
                if (i == len) break;
                int p = img.getRGB(0,i);
                int alpha = (p >> 24) & 0xFF;
                int red = (p >> 16) & 0xFF;
                int green = (p >> 8 ) & 0xFF;
                int blue = (p) & 0xFF;
                alpha = ((alpha >> 2) << 2) | (array[i] >> 6) & 3;
                red = ((red >> 2) << 2) | (array[i] >> 4) & 3;
                green = ((green >> 2) << 2) | (array[i] >> 2) & 3;
                blue = ((blue >> 2) << 2) | (array[i]) & 3;
                p = (alpha << 24) | (red << 16) | (green << 8) | blue;
                img.setRGB(0,i,p);
            }

    }
    private static void decoding(BufferedImage img) throws IOException{
        int height = img.getHeight(), len = 3;
        String s = "";

        for (int i = 0; i<height;i++){
            if (i == len ) break;
            int p = img.getRGB(0,i);
            int alpha = (p >> 24) & 0xFF;
            int red = (p >> 16) & 0xFF;
            int green = (p >> 8 ) & 0xFF;
            int blue = (p) & 0xFF;

            int value = ((alpha ) << 6) | ((red ) << 4) | ((green ) << 2) | (blue );
            if (i == 0) len = value + 1;
            else {
                s += (char) value;
            }

        }
        System.out.println(String.valueOf(s));
    }
}
