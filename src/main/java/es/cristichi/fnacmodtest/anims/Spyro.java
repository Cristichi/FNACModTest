package es.cristichi.fnacmodtest.anims;

import es.cristichi.fnac.exception.ResourceException;
import es.cristichi.fnac.io.Resources;
import es.cristichi.fnac.obj.Jumpscare;
import es.cristichi.fnac.obj.JumpscareVisualSetting;
import es.cristichi.fnac.obj.anim.PathedMoveAnimatronicDrawing;
import es.cristichi.fnac.obj.cnight.CustomNightAnimatronic;
import es.cristichi.fnac.obj.cnight.CustomNightAnimatronicData;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Random;

@CustomNightAnimatronic(name = "Spyro", portraitPath = "anim/spyro/portrait.png",
                        tutDesc = "Nope",
                        restDesc = "Spyro goes to right door from bathrooms.")
public class Spyro extends PathedMoveAnimatronicDrawing {
    public Spyro(CustomNightAnimatronicData data) throws ResourceException {
        this("%s (%s)".formatted(data.name(), data.variant()), Map.of(0, data.ai()),
                List.of(
                        List.of("kitchen", "dining area", "corridor 2", "bathrooms", "rightDoor"),
                        List.of("storage", "dining area", "main stage", "corridor 3", "leftDoor")
                ), data.rng());
    }
    public Spyro(String nameId, Map<Integer, Integer> aiDuringNight, List<List<String>> camPaths,
                 Random rng) throws ResourceException {
        super(nameId, 3, 5, aiDuringNight, GENERIC_MAX_AI, false, false,
                "anim/spyro/camImg.png", new Jumpscare("anim/spyro/jump.gif", 0,
                        Resources.loadSound("anim/spyro/jump.wav", "spyroJump.wav"), 0,
                        JumpscareVisualSetting.CENTERED), camPaths, Color.MAGENTA.darker(), rng);
        sounds.put("move", Resources.loadSound("anim/spyro/move.wav", "spyroMove.wav"));
    }
}
