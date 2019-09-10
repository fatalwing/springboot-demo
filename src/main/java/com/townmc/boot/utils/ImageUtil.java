package com.townmc.boot.utils;

import com.townmc.utils.NumberUtil;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.util.Base64;
import java.util.Iterator;


/**
 * 图片工具类
 */
public class ImageUtil {
    /**
     * 图片格式：JPG
     */
    private static final String PICTRUE_FORMATE_JPG = "jpg";

    /**
     * jpg文件格式
     */
    public static String JPG = "jpg";

    /**
     * png文件格式
     */
    public static String PNG = "png";

    /**
     * 图片裁剪工具
     *
     * @param in              ******************输入流
     * @param readImageFormat *****图片格式
     * @param x               *******************起始点x坐标
     * @param y               *******************起始点y坐标
     * @param w               *******************裁剪宽度
     * @param h               *******************裁剪高度
     * @return is*****************输出流
     * @throws IOException
     * @author Jieve
     */
    public static InputStream cutImage(InputStream in, String readImageFormat, int x, int y, int w, int h) throws IOException {

        Iterator<ImageReader> iterator = ImageIO.getImageReadersByFormatName(readImageFormat);
        ImageReader reader = (ImageReader) iterator.next();
        ImageInputStream iis = ImageIO.createImageInputStream(in);
        reader.setInput(iis, true);
        ImageReadParam param = reader.getDefaultReadParam();
        Rectangle rect = new Rectangle(x, y, w, h);
        param.setSourceRegion(rect);
        BufferedImage bi = reader.read(0, param);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(bi, readImageFormat, os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        in.close();
        os.close();
        return is;
    }

    /**
     * 根据图片对象获取对应InputStream
     *
     * @param image
     * @param readImageFormat
     * @return
     * @throws IOException
     */
    public static InputStream getInputStream(BufferedImage image, String readImageFormat) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(image, readImageFormat, os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        os.close();
        return is;
    }

    /**
     * 生成缩略图
     *
     * @param in
     * @param size
     * @param imageFormat
     * @return
     * @throws IOException
     */
    public static InputStream createThumbnails(InputStream in, int size, String imageFormat) throws IOException {
        if (imageFormat.equalsIgnoreCase("jpg")) {

            return createThumbnails(in, size);
        } else if (imageFormat.equalsIgnoreCase("png")) {
            BufferedImage image = Thumbnails.of(in).scale(1f).outputFormat(JPG).asBufferedImage();
            InputStream is = getInputStream(image, JPG);
            return createThumbnails(is, size);
        }
        return null;
    }

    /**
     * 转化为jpg格式
     *
     * @return
     * @throws IOException
     */
    public static BufferedImage toJPG(BufferedImage image) throws IOException {

        int width = image.getWidth();
        int height = image.getHeight();
        BufferedImage image_ = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphic = image_.createGraphics();
        graphic.setColor(Color.WHITE);
        graphic.fillRect(0, 0, width, height);
        graphic.drawRenderedImage(image, null);
        graphic.dispose();
        return image_;
    }

    /**
     * 生成缩略图
     *
     * @param in
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static InputStream createThumbnails(InputStream in, int width, int height) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Thumbnailator.createThumbnail(in, os, width, height);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        in.close();
        os.close();
        return is;
    }

    /**
     * 生成缩略图
     *
     * @param in
     * @param size
     * @return
     * @throws IOException
     */
    public static InputStream createThumbnails(InputStream in, int size) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Thumbnailator.createThumbnail(in, os, size, size);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        in.close();
        os.close();
        return is;
    }

    /**
     * 添加水印
     *
     * @param in
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static InputStream addWaterMark(InputStream in, InputStream waterMark, int width, int height) throws IOException {

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Thumbnails.of(in).size(width, height).watermark(Positions.BOTTOM_RIGHT, ImageIO.read(waterMark), 0.4f).outputQuality(0.8f).toOutputStream(os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        in.close();
        waterMark.close();
        os.close();
        return is;
    }

    /**
     * 居中裁剪
     *
     * @param image
     * @return
     */
    public static BufferedImage clipCenter(BufferedImage image) {

        int height = image.getHeight();
        int width = image.getWidth();
        int size = height >= width ? width : height;
        int temp = 0;
        if (height >= width) {
            temp = (height - width) / 2;
            image = image.getSubimage(0, temp, size, size);
        } else {
            temp = (width - height) / 2;
            image = image.getSubimage(temp, 0, size, size);
        }

        return image;
    }

    /**
     * 裁剪图片
     *
     * @param image
     * @param x
     * @param y
     * @param size
     * @return
     */
    public static BufferedImage cutImage(BufferedImage image, int x, int y, int size) {

        int height = image.getHeight();
        int width = image.getWidth();
        if ((width >= (x + size)) && (height >= (y + size))) {
            image = image.getSubimage(x, y, size, size);
        } else {
            int temp = ((height - y) >= (width - x)) ? (width - x) : (height - y);
            image = image.getSubimage(x, y, temp, temp);
        }

        return image;
    }

    /**
     * 检查格式是否合法
     *
     * @param imageType
     * @return
     */
    public static boolean checkType(String imageType) {

        boolean flag = false;
        if (JPG.equalsIgnoreCase(imageType) || PNG.equalsIgnoreCase(imageType)) {
            flag = true;
        }
        return flag;
    }

    /**
     * 压缩图片
     * 默认输出50%质量图片
     *
     * @param image
     * @return
     * @throws IOException
     */
    public static InputStream compress(BufferedImage image, String readImageFormat) throws IOException {

        InputStream in = getInputStream(image, readImageFormat);
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        Thumbnails.of(in).size(image.getWidth(), image.getHeight()).outputQuality(0.5f).toOutputStream(os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());
        in.close();
        os.close();
        return is;
    }


    private ImageUtil() {

    }

    public static String getFileSuffix(byte[] bytes) {
        return bytes != null && bytes.length >= 10 ? (bytes[0] == 71 && bytes[1] == 73 && bytes[2] == 70 ? "GIF" : (bytes[1] == 80 && bytes[2] == 78 && bytes[3] == 71 ? "PNG" : (bytes[6] == 74 && bytes[7] == 70 && bytes[8] == 73 && bytes[9] == 70 ? "JPG" : (bytes[0] == 66 && bytes[1] == 77 ? "BMP" : null)))) : null;
    }

    public static void region(String sourceImagePath, String toImage, int width, int height) throws IOException {
        // 获得图片分辨率
        BufferedImage image = ImageIO.read(new File(sourceImagePath));
        int xwidth = image.getWidth();
        int yheight = image.getHeight();
        int keepWidth = xwidth;
        int keepHeight = yheight;
        if (NumberUtil.divide(xwidth, yheight, 6).doubleValue() > NumberUtil.divide(width, height, 6).doubleValue()) {
            keepWidth = NumberUtil.divide(NumberUtil.multiplay(yheight, width, 6), height, 6).intValue();
            keepHeight = yheight;
        } else if (NumberUtil.divide(xwidth, yheight, 6).doubleValue() < NumberUtil.divide(width, height, 6).doubleValue()) {
            keepWidth = xwidth;
            keepHeight = NumberUtil.divide(NumberUtil.multiplay(xwidth, height, 6), width, 6).intValue();
        }
        Thumbnails.of(sourceImagePath).sourceRegion(Positions.CENTER, keepWidth, keepHeight)
                .size(width, height).keepAspectRatio(false).outputQuality(1.0f).toFile(toImage);
    }

    public static int getTextLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            int wordLength = String.valueOf(text.charAt(i)).getBytes().length;
            if (wordLength > 1) {
                length += (wordLength - 1);
            }
        }

        return length % 2 == 0 ? length / 2 : length / 2 + 1;
    }


    /**
     * 添加图片水印
     *
     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param waterImg  水印图片路径，如：C://myPictrue//logo.png
     * @param x         水印图片距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y         水印图片距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha     透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public final static void pressImage(String targetImg, String waterImg, int x, int y, float alpha) {
        try {
            File file = new File(targetImg);
            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);

            Image waterImage = ImageIO.read(new File(waterImg));    // 水印文件
            int width_1 = waterImage.getWidth(null);
            int height_1 = waterImage.getHeight(null);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            if (x < 0) {
                x = widthDiff / 2;
            } else if (x > widthDiff) {
                x = widthDiff;
            }
            if (y < 0) {
                y = heightDiff / 2;
            } else if (y > heightDiff) {
                y = heightDiff;
            }
            g.drawImage(waterImage, x, y, width_1, height_1, null); // 水印文件结束
            g.dispose();
            ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 合成图片
     *
     * @param targetImg
     * @param waterImg
     * @param x
     * @param y
     * @param alpha
     * @return
     */
    public final static BufferedImage pressImg(Image targetImg, Image waterImg, int x, int y, float alpha) {
        int width = targetImg.getWidth(null);
        int height = targetImg.getHeight(null);

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();

        g.drawImage(targetImg, 0, 0, width, height, null);

        int width_1 = waterImg.getWidth(null);
        int height_1 = waterImg.getHeight(null);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

        int widthDiff = width - width_1;
        int heightDiff = height - height_1;
        if (x < 0) {
            x = widthDiff / 2;
        } else if (x > widthDiff) {
            x = widthDiff;
        }
        if (y < 0) {
            y = heightDiff / 2;
        } else if (y > heightDiff) {
            y = heightDiff;
        }
        g.drawImage(waterImg, x, y, width_1, height_1, null); // 水印文件结束
        g.dispose();
        return bufferedImage;
    }

    /**
     * 添加文字水印
     *
     * @param targetImg 目标图片路径，如：C://myPictrue//1.jpg
     * @param pressText 水印文字， 如：中国证券网
     * @param fontName  字体名称，    如：宋体
     * @param fontStyle 字体样式，如：粗体和斜体(Font.BOLD|Font.ITALIC)
     * @param fontSize  字体大小，单位为像素
     * @param color     字体颜色
     * @param x         水印文字距离目标图片左侧的偏移量，如果x<0, 则在正中间
     * @param y         水印文字距离目标图片上侧的偏移量，如果y<0, 则在正中间
     * @param alpha     透明度(0.0 -- 1.0, 0.0为完全透明，1.0为完全不透明)
     */
    public static BufferedImage pressText(String targetImg, String pressText, String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
        try {
            File file = new File(targetImg);

            Image image = ImageIO.read(file);
            int width = image.getWidth(null);
            int height = image.getHeight(null);
            BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufferedImage.createGraphics();
            g.drawImage(image, 0, 0, width, height, null);
            g.setFont(new Font(fontName, fontStyle, fontSize));
            g.setColor(color);
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

            int width_1 = fontSize * getLength(pressText);
            int height_1 = fontSize;
            int widthDiff = width - width_1;
            int heightDiff = height - height_1;
            if (x < 0) {
                x = widthDiff / 2;
            } else if (x > widthDiff) {
                x = widthDiff;
            }
            if (y < 0) {
                y = heightDiff / 2;
            } else if (y > heightDiff) {
                y = heightDiff;
            }

            g.drawString(pressText, x, y + height_1);
            g.dispose();
            return bufferedImage;
//            ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 从输入流中读取数据 打印水印
     *
     * @param inputStream
     * @param pressText
     * @param fontName
     * @param fontStyle
     * @param fontSize
     * @param color
     * @param x
     * @param y
     * @param alpha
     * @return
     */
    public static BufferedImage pressTextByInputStream(InputStream inputStream, String pressText, String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha) {
        try {
            Image image = ImageIO.read(inputStream);
            return pressTextByImage(pressText, fontName, fontStyle, fontSize, color, x, y, alpha, image);
//            ImageIO.write(bufferedImage, PICTRUE_FORMATE_JPG, file);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static BufferedImage pressTextByImage(String pressText, String fontName, int fontStyle, int fontSize, Color color, int x, int y, float alpha, Image image) {
        int width = image.getWidth(null);
        int height = image.getHeight(null);
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.setFont(new Font(fontName, fontStyle, fontSize));
        g.setColor(color);
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));

        int width_1 = fontSize * getLength(pressText);
        int height_1 = fontSize;
        int widthDiff = width - width_1;
        int heightDiff = height - height_1;
        if (x < 0) {
            x = widthDiff / 2;
        } else if (x > widthDiff) {
            x = widthDiff;
        }
        if (y < 0) {
            y = heightDiff / 2;
        } else if (y > heightDiff) {
            y = heightDiff;
        }

        g.drawString(pressText, x, y + height_1);
        g.dispose();
        return bufferedImage;
    }

    /**
     * 获取字符长度，一个汉字作为 1 个字符, 一个英文字母作为 0.5 个字符
     *
     * @param text
     * @return 字符长度，如：text="中国",返回 2；text="test",返回 2；text="中国ABC",返回 4.
     */
    public static int getLength(String text) {
        int textLength = text.length();
        int length = textLength;
        for (int i = 0; i < textLength; i++) {
            if (String.valueOf(text.charAt(i)).getBytes().length > 1) {
                length++;
            }
        }
        return (length % 2 == 0) ? length / 2 : length / 2 + 1;
    }

    /**
     * 图片缩放
     *
     * @param filePath 图片路径
     * @param height   高度
     * @param width    宽度
     * @param bb       比例不对时是否需要补白
     */
    public static void resize(String filePath, int height, int width, boolean bb) {
        try {
            double ratio = 0; //缩放比例
            File f = new File(filePath);
            BufferedImage bi = ImageIO.read(f);
            Image itemp = bi.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);
            //计算比例
            if ((bi.getHeight() > height) || (bi.getWidth() > width)) {
                if (bi.getHeight() > bi.getWidth()) {
                    ratio = (new Integer(height)).doubleValue() / bi.getHeight();
                } else {
                    ratio = (new Integer(width)).doubleValue() / bi.getWidth();
                }
                AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);
                itemp = op.filter(bi, null);
            }
            if (bb) {
                BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
                Graphics2D g = image.createGraphics();
                g.setColor(Color.white);
                g.fillRect(0, 0, width, height);
                if (width == itemp.getWidth(null)) {
                    g.drawImage(itemp, 0, (height - itemp.getHeight(null)) / 2, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                } else {
                    g.drawImage(itemp, (width - itemp.getWidth(null)) / 2, 0, itemp.getWidth(null), itemp.getHeight(null), Color.white, null);
                }
                g.dispose();
                itemp = image;
            }
            ImageIO.write((BufferedImage) itemp, "jpg", f);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
//        URL bjurl= ImageUtil.class.getClassLoader().getResource("qrbj.png");

//        pressText("/Users/dongwujing/Downloads/qrbj.png", "支付给:桔牛商家", "宋体", Font.BOLD, 24, Color.black, -1, 600, 1.0f);
        String url = "https://oss.juniuo.com/juniuo-pic/picture/juniuo/2a1ea853-20e1-4b1c-87aa-aeddf834e5b1/resize_800_0";
//        String url="http://n.sinaimg.cn/translate/339/w1183h756/20180528/bfN4-hcaquev3133387.jpg";
        String base64 = getBase64ByImgUrl(url);
        System.out.println("base64:" + base64);
    }


    /**
     * url转码为base64
     *
     * @param url
     * @return
     */
    public static String getBase64ByImgUrl(String url) {
        String suffix = "jpg";
        if (url.contains(".jpg")) {
            suffix = url.substring(url.lastIndexOf(".") + 1);
        }
        System.out.println("后缀:" + suffix);
        try {
            URL urls = new URL(url);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            Image image = Toolkit.getDefaultToolkit().getImage(urls);
            BufferedImage biOut = toBufferedImage(image);
            ImageIO.write(biOut, suffix, baos);

            String base64Str = Base64.getEncoder().encodeToString(baos.toByteArray());
            System.out.println("base64Str:" + base64Str);
            return base64Str;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }

    }

    /**
     * image 转 BufferedImage
     *
     * @param image
     * @return
     */
    public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        // This code ensures that all the pixels in the image are loaded
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment
                .getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(image.getWidth(null),
                    image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            // The system does not have a screen
        }
        if (bimage == null) {
            // Create a buffered image using the default color model
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null),
                    image.getHeight(null), type);
        }
        // Copy image to buffered image
        Graphics g = bimage.createGraphics();
        // Paint the image onto the buffered image
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return bimage;
    }
}
