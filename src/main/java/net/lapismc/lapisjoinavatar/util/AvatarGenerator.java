package net.lapismc.lapisjoinavatar.util;

import net.lapismc.lapisjoinavatar.LapisJoinAvatar;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class AvatarGenerator {

    private final LapisJoinAvatar plugin;
    @SuppressWarnings("UnnecessaryUnicodeEscape")
    private final char block = '\u2588';

    public AvatarGenerator(LapisJoinAvatar plugin) {
        this.plugin = plugin;
    }

    public String[] getAvatar(UUID uuid) {
        String[] avatarAsText = new String[8];
        //Get image
        BufferedImage avatar = null;
        try {
            avatar = getAvatarImage(uuid);
        } catch (IOException e) {
            //TODO: Send error to the console
            return avatarAsText;
        }
        //Loop over each pixel and get the hex RGB code for it
        for (int h = 0; h < avatar.getHeight(); h++) {
            //Store each pixel from the line
            StringBuilder builder = new StringBuilder();
            for (int w = 0; w < avatar.getWidth(); w++) {
                //Append the color code and then the block char
                builder.append(getHexCode(avatar.getRGB(w, h))).append(block);
            }
            //Convert the hex codes to MC codes plus putting them all together from the builder
            avatarAsText[h] = plugin.config.colorMessage(builder.toString());
        }
        return avatarAsText;
    }

    private BufferedImage getAvatarImage(UUID uuid) throws IOException {
        //File name format is as follows
        //UUID-TimeCreated.png
        File cacheDir = new File(plugin.getDataFolder(), "AvatarCache");
        if (!cacheDir.exists())
            cacheDir.mkdir();
        File[] cachedImages = cacheDir.listFiles();
        if (cachedImages != null) {
            for (File img : cachedImages) {
                if (img.getName().startsWith(uuid.toString())) {
                    //We have a cached image, now we check the age
                    String name = img.getName();
                    long age = Long.parseLong(name.substring(name.lastIndexOf("-"), name.lastIndexOf(".")));
                    long limit = System.currentTimeMillis() + (long) plugin.getConfig().getInt("CacheTimeout") * 60 * 60 * 1000;
                    if (age > limit)
                        img.delete();
                    else
                        return ImageIO.read(img);
                }
            }
        }
        //Get new image
        String urlString = plugin.getConfig().getString("AvatarURL", "https://crafatar.com/avatars/%UUID%?size=8&default=MHF_Steve");
        urlString = urlString.replace("%UUID%", uuid.toString());
        BufferedImage avatar;
        try {
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.95 Safari/537.11");
            avatar = ImageIO.read(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: Maybe print blank image
            return null;
        }
        //Resize image to 8x8 if it isn't already
        if (avatar.getHeight() != 8 || avatar.getWidth() != 8) {
            avatar = resizeImage(avatar);
        }
        //Save image to cache
        ImageIO.write(avatar, "png", new File(cacheDir, uuid + "-" + System.currentTimeMillis() + ".png"));
        return avatar;
    }

    private String getHexCode(int color) {
        Color c = new Color(color);
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }

    BufferedImage resizeImage(BufferedImage originalImage) {
        Image resultingImage = originalImage.getScaledInstance(8, 8, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(8, 8, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }

}
