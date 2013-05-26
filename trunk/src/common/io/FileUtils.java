package common.io;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtils {

    public static File getDefaultIconURL() {
        return new File(".\\images\\defaultIcon.png");
    }

    public static byte[] loadFile(File file) {
        byte[] b = new byte[(int) file.length()];

        // try loading
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            fileInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return b;
    }

    public static void saveFile(byte[] b, File file) {
        BufferedOutputStream bs = null;

        try {
            FileOutputStream fs = new FileOutputStream(file);
            bs = new BufferedOutputStream(fs);
            bs.write(b);
            bs.close();
            bs = null;
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (bs != null) {
            try {
                bs.close();
            } catch (Exception e) {
            }
        }
    }
}
