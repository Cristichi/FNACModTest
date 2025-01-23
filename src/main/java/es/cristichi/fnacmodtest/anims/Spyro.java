package es.cristichi.fnacmodtest.anims;

import es.cristichi.fnac.anim.Jumpscare;
import es.cristichi.fnac.anim.JumpscareVisualSetting;
import es.cristichi.fnac.anim.PathedMoveAnimatronicDrawing;
import es.cristichi.fnac.exception.ResourceException;
import es.cristichi.fnac.io.Resources;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Spyro extends PathedMoveAnimatronicDrawing {
    private static final Logger LOGGER = LoggerFactory.getLogger(Spyro.class);
    
    public Spyro(String nameId, Map<Integer, Integer> aiDuringNight, List<List<String>> camPaths,
                 Random rng) throws ResourceException, MalformedURLException {
        super(nameId, 3, 5, aiDuringNight, false, false,
                getFromURLorResources(),
                new Jumpscare(Resources.loadGif("anim/spyro/jump.gif"), 0,Resources.loadSound("anim/spyro/jump.wav"),
                        0, JumpscareVisualSetting.CENTERED),
                camPaths, Color.MAGENTA.darker(), rng);
        sounds.put("move", Resources.loadSound("anim/spyro/move.wav"));
    }
    
    private static BufferedImage getFromURLorResources() throws ResourceException {
        URL url = null;
        try {
            url = new URL("https://upload.wikimedia.org/wikipedia/en/5/53/Spyro.png");
            Image img = ImageIO.read(url);
            
            BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB);
            
            Graphics2D bGr = bimage.createGraphics();
            bGr.drawImage(img, 0, 0, null);
            bGr.dispose();
            
            return bimage;
        } catch (Exception e){
            LOGGER.warn("Could not load URL {}. Loading from resource instead.", url==null?"null":url, e);
            return Resources.loadImage("anim/spyro/camImg.png");
        }
    }
}
