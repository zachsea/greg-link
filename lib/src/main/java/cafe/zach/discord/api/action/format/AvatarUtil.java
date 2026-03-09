package cafe.zach.discord.api.action.format;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.ConcurrentHashMap;

import javax.imageio.ImageIO;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AvatarUtil {

    private static final float HEAD_SCALE = 0.75f;
    private static final int CANVAS_SIZE = 64;
    private static final OkHttpClient CLIENT = new OkHttpClient();
    private static final ConcurrentHashMap<String, byte[]> CACHE = new ConcurrentHashMap<>();

    private AvatarUtil() {}

    public static byte[] fetchPaddedAvatar(String identifier) {
        byte[] cached = CACHE.get(identifier);
        if (cached != null) return cached;

        byte[] result = fetch(identifier);
        if (result != null) CACHE.put(identifier, result);
        return result;
    }

    public static void invalidate(String identifier) {
        CACHE.remove(identifier);
    }

    public static void clearCache() {
        CACHE.clear();
    }

    private static byte[] fetch(String identifier) {
        int headSize = Math.round(CANVAS_SIZE * HEAD_SCALE);
        String url = String.format("https://mc-heads.net/avatar/%s/%d", identifier, headSize);

        Request request = new Request.Builder().url(url)
            .header("User-Agent", "GregLink-DiscordBot/1.0")
            .build();

        try (Response response = CLIENT.newCall(request)
            .execute()) {
            if (!response.isSuccessful() || response.body() == null) return null;

            InputStream stream = response.body()
                .byteStream();
            BufferedImage head = ImageIO.read(stream);
            if (head == null) return null;

            int offset = (CANVAS_SIZE - headSize) / 2;
            BufferedImage canvas = new BufferedImage(CANVAS_SIZE, CANVAS_SIZE, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g = canvas.createGraphics();
            g.drawImage(head, offset, offset, headSize, headSize, null);
            g.dispose();

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(canvas, "png", out);
            return out.toByteArray();

        } catch (Exception e) {
            return null;
        }
    }
}
