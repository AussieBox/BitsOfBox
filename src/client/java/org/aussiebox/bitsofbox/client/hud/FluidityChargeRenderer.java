package org.aussiebox.bitsofbox.client.hud;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.component.ModDataComponentTypes;
import org.aussiebox.bitsofbox.item.custom.FluidityItem;

public class FluidityChargeRenderer {

    public static void render(DrawContext context, RenderTickCounter counter) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        if (!(player.getMainHandStack().getItem() instanceof FluidityItem)) return;
        ItemStack stack = player.getMainHandStack();

        int maxCharges = stack.getOrDefault(ModDataComponentTypes.FLUIDITY_MAX_CHARGES, 5);
        int charges = stack.getOrDefault(ModDataComponentTypes.FLUIDITY_CHARGES, 0);
        int emptySlots = maxCharges - charges;
        int fullSlots = maxCharges - emptySlots;
        int slotsRendered = 0;

        Text bar = Text.empty();

        if (fullSlots > 0) {
            for (int i = 0; i < fullSlots; i++) {
                if (slotsRendered == 0) bar = Text.literal("S-");
                else if (slotsRendered == maxCharges-1) bar = bar.copy().append("E");
                else bar = bar.copy().append("L-");
                slotsRendered++;
            }
        }
        if (emptySlots >= 0) {
            for (int i = 0; i < emptySlots; i++) {
                if (slotsRendered == 0) bar = Text.literal("s-");
                else if (slotsRendered == maxCharges-1) bar = bar.copy().append("e");
                else bar = bar.copy().append("l-");
                slotsRendered++;
            }
        }

        bar = bar.copy().setStyle(Style.EMPTY.withFont(BOB.id("fluidity_bar")));

        int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();

        context.drawText(
                textRenderer,
                bar,
                width/2 - (textRenderer.getWidth(bar)/2),
                height/2 + 5 + textRenderer.fontHeight,
                0xFFFFFF,
                false
        );
    }

}
