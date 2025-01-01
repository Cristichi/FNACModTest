package es.cristichi.fnacmodtest.anims;

import es.cristichi.fnac.exception.ResourceException;
import es.cristichi.fnac.io.Resources;
import es.cristichi.fnac.obj.Jumpscare;
import es.cristichi.fnac.obj.JumpscareVisualSetting;
import es.cristichi.fnac.obj.anim.PathedMoveAnimatronicDrawing;
import es.cristichi.fnac.obj.cnight.CustomNightAnimatronic;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

@CustomNightAnimatronic(name = "Spyro", portraitPath = "anim/spyro/portrait.png",
                        tutDesc = "Nope",
                        restDesc = "Spyro goes to right door from bathrooms.")
public class Spyro extends PathedMoveAnimatronicDrawing {
    public Spyro(String nameId, Map<Integer, Integer> aiDuringNight, List<List<String>> camPaths, Color debugColor,
                 Random rng) throws ResourceException {
        super(nameId, 3, 5, aiDuringNight, GENERIC_MAX_AI, false, false,
                "anim/spyro/camImg.png", new Jumpscare("anim/spyro/jump.gif", 0,
                        Resources.loadSound("anim/spyro/jump.wav", "spyroJump.wav"), 0,
                        JumpscareVisualSetting.CENTERED), camPaths, debugColor, rng);
        sounds.put("move", Resources.loadSound("anim/spyro/move.wav", "spyroMove.wav"));
    }
}
