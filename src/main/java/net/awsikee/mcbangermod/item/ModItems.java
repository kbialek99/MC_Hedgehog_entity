package net.awsikee.mcbangermod.item;

import com.google.common.util.concurrent.ClosingFuture;
import net.awsikee.mcbangermod.McBangerMod;
import net.awsikee.mcbangermod.entity.ModEntityTypes;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.ForgeSpawnEggItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public  static  final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, McBangerMod.MOD_ID);
    public static final RegistryObject<Item> ROUGH_RUBY = ITEMS.register("rough_ruby",
            () -> new Item(new Item.Properties()));
    public  static final RegistryObject<Item> RUBY = ITEMS.register("ruby",
            () -> new Item(new Item.Properties()));
    public  static final RegistryObject<Item> HEDGEHOG_SPAWN_EGG = ITEMS.register("hedgehog_spawn_egg",
            () -> new ForgeSpawnEggItem(ModEntityTypes.HEDGEHOG, 0x57e35, 0x100000, new Item.Properties()));
    public  static void  register(IEventBus eventBus){
        ITEMS.register(eventBus);
    }



}
