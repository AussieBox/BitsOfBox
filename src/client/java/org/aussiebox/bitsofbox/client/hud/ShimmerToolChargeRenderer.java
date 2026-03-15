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
import org.aussiebox.bitsofbox.BOBConstants;
import org.aussiebox.bitsofbox.cca.ShimmerComponent;
import org.aussiebox.bitsofbox.cca.TrinketComponent;
import org.aussiebox.bitsofbox.component.ModDataComponentTypes;
import org.aussiebox.bitsofbox.item.ModItems;
import org.aussiebox.bitsofbox.item.custom.PyrrhianBeltItem;
import org.aussiebox.bitsofbox.item.custom.ShimmerToolItem;
import org.aussiebox.bitsofbox.util.BOBUtil;

import java.util.Objects;

public class ShimmerToolChargeRenderer {

    public static void render(DrawContext context, RenderTickCounter counter) {
        PlayerEntity player = MinecraftClient.getInstance().player;
        if (player == null) return;

        TextRenderer textRenderer = MinecraftClient.getInstance().textRenderer;

        if (!(player.getMainHandStack().getItem() instanceof ShimmerToolItem)) return;
        ItemStack stack = player.getMainHandStack();

        int maxCharges = stack.getOrDefault(ModDataComponentTypes.SHIMMER_TOOL_MAX_CHARGES, 5);
        int charges = stack.getOrDefault(ModDataComponentTypes.SHIMMER_TOOL_CHARGES, 0);
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

        bar = bar.copy().setStyle(Style.EMPTY.withFont(BOB.id("shimmer_tool_bar")));

        int width = MinecraftClient.getInstance().getWindow().getScaledWidth();
        int height = MinecraftClient.getInstance().getWindow().getScaledHeight();
        int y = height/2+5+textRenderer.fontHeight;

        TrinketComponent trinketComponent = TrinketComponent.KEY.get(player);
        double flightTime = trinketComponent.getPyrrhianBeltFlightTime();
        double flightTimeMaximum = PyrrhianBeltItem.getBeltFlyTime(player);
        if (BOBUtil.playerHasTrinket(player, ModItems.PYRRHIAN_BELT) && flightTime < flightTimeMaximum) {
            y += 10;
            if (Objects.equals(player.getUuidAsString(), "fdf5edf6-f202-47fe-98f0-68a60d68b0d5"))
                y += 2 + textRenderer.fontHeight;
        }

        context.drawText(
                textRenderer,
                bar,
                width/2 - (textRenderer.getWidth(bar)/2),
                y,
                0xFFFFFF,
                false
        );

        if (BOBUtil.stackHasEnchantment(player.getWorld(), stack, BOBConstants.SHIMMERSEEP_ENCHANT)) {
            int totalSeconds = ShimmerComponent.KEY.get(player).shimmerseepTicks/20;

            int minutes = totalSeconds / 60;
            int seconds = totalSeconds % 60;

            Text text = Text.literal(minutes + ":" + (seconds < 10 ? "0" : "") + seconds);
            context.drawText(
                    textRenderer,
                    text,
                    width/2 - (textRenderer.getWidth(text)/2),
                    y+10,
                    0xA024FF,
                    true
            );
        }
    }

}
