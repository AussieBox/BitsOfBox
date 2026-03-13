package org.aussiebox.bitsofbox.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.aussiebox.bitsofbox.BOB;
import org.aussiebox.bitsofbox.item.ModItems;

public class JEIPlugin implements IModPlugin {

    @Override
    public Identifier getPluginUid() {
        return BOB.id("jei_plugin");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(
                new ItemStack(ModItems.DRAGONFLAME_CACTUS),
                VanillaTypes.ITEM_STACK,
                Text.translatable("item.bitsofbox.dragonflame_cactus.jei.description")
        );
    }
}
