package common.protocol;

public class PngCapability implements ICapability {

    private static final long serialVersionUID = 1163798384410910100L;
    private byte[] pngImage;

    public PngCapability(byte[] pngImage) {
        this.pngImage = pngImage;
    }

    public byte[] getPngImage() {
        return pngImage;
    }
}
