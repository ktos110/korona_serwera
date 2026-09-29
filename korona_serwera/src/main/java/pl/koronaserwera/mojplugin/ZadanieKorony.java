package pl.koronaserwera.mojplugin;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class ZadanieKorony extends BukkitRunnable {

    @Override
    public void run() {
        for (Player gracz : Bukkit.getOnlinePlayers()) {
            ItemStack helm = gracz.getInventory().getHelmet();

            if (helm != null && helm.hasItemMeta()) {
                ItemMeta meta = helm.getItemMeta();
                
                if (meta != null) {
                    String tag = meta.getPersistentDataContainer().get(MojPlugin.getIdKlucza(), PersistentDataType.STRING);
                    
                    if ("korona".equals(tag)) {
                        gracz.addPotionEffect(new PotionEffect(PotionEffectType.STRENGTH, 40, 2, true, false, true));
                        gracz.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 40, 0, true, false, true));
                        gracz.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 40, 0, true, false, true));
                        gracz.addPotionEffect(new PotionEffect(PotionEffectType.RESISTANCE, 40, 0, true, false, true));
                        gracz.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 40, 0, true, false, true));
                    }
                }
            }
        }
    }
}
