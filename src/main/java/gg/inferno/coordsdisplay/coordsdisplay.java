package gg.inferno.coordsdisplay;

import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.fml.common.Mod;
import com.mojang.blaze3d.vertex.PoseStack;
import org.apache.logging.log4j.LogManager;
import net.minecraft.client.Minecraft;
import net.minecraft.world.phys.Vec3;


@Mod(value=coordsdisplay.MOD_ID)
public class coordsdisplay {

    public static final String MOD_ID = "coordsdisplay";
    public static final IIngameOverlay OVERLAY = OverlayRegistry.registerOverlayAbove(ForgeIngameGui.HUD_TEXT_ELEMENT, MOD_ID + ":overlay", coordsdisplay::RenderLocation);    
    
    public coordsdisplay(){
        // MinecraftForge.EVENT_BUS.addListener(this::onEvent);
        LogManager.getLogger().info("Event Handler Registered");
    }


    public static void RenderLocation(ForgeIngameGui gui, PoseStack matrix, float partialTicks, int width, int height){
        Minecraft mc = Minecraft.getInstance();
        
        Vec3 pos = mc.player.position();
        String data = "[Position]   X: " + Math.round(pos.x * 10)/10 + "   Y: " + Math.round(pos.y * 10)/10 + "   Z: " + Math.round(pos.z * 10)/10;
        mc.font.draw(matrix, data, 2, 1, 16777215);
        LogManager.getLogger().info("Event Called");
    }
}
