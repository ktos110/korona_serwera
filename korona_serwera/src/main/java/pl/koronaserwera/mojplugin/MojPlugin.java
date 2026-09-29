package pl.koronaserwera.mojplugin;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class MojPlugin extends JavaPlugin implements Listener {

    private static NamespacedKey identyfikatorPrzedmiotu;

    @Override
    public void onEnable() {
        identyfikatorPrzedmiotu = new NamespacedKey(this, "korona_serwera_id");
        
        stworzReceptury();
        getServer().getPluginManager().registerEvents(this, this);
        new ZadanieKorony().runTaskTimer(this, 0L, 20L);

        getLogger().info("Wielostopniowy plugin z powiadomieniami zostal wlaczony!");
    }

    private void stworzReceptury() {
        // --- ETAP 1: Esencja Mocy ---
        ItemStack esencja = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta esencjaMeta = esencja.getItemMeta();
        if (esencjaMeta != null) {
            esencjaMeta.displayName(Component.text("Esencja Mocy")
                    .color(NamedTextColor.LIGHT_PURPLE).decoration(TextDecoration.ITALIC, false));
            esencjaMeta.lore(List.of(Component.text("Etap 1: Czysta energia zamknieta w krysztale.").color(NamedTextColor.GRAY)));
            esencjaMeta.setCustomModelData(1002);
            esencjaMeta.getPersistentDataContainer().set(identyfikatorPrzedmiotu, PersistentDataType.STRING, "esencja");
            esencja.setItemMeta(esencjaMeta);
        }
        NamespacedKey kluczEsencja = new NamespacedKey(this, "esencja_mocy");
        ShapedRecipe recepturaEsencja = new ShapedRecipe(kluczEsencja, esencja);
        recepturaEsencja.shape("DDD", "DND", "DDD");
        recepturaEsencja.setIngredient('D', Material.DIAMOND);
        recepturaEsencja.setIngredient('N', Material.NETHER_STAR);
        Bukkit.addRecipe(recepturaEsencja);

        // --- ETAP 2: Królewski Szlif ---
        ItemStack szlif = new ItemStack(Material.HEART_OF_THE_SEA);
        ItemMeta szlifMeta = szlif.getItemMeta();
        if (szlifMeta != null) {
            szlifMeta.displayName(Component.text("Krolewski Szlif")
                    .color(NamedTextColor.AQUA).decoration(TextDecoration.ITALIC, false));
            szlifMeta.lore(List.of(Component.text("Etap 2: Esencja stopiona w idealny ksztalt.").color(NamedTextColor.GRAY)));
            szlifMeta.setCustomModelData(1003);
            szlifMeta.getPersistentDataContainer().set(identyfikatorPrzedmiotu, PersistentDataType.STRING, "szlif");
            szlif.setItemMeta(szlifMeta);
        }
        NamespacedKey kluczSzlif = new NamespacedKey(this, "krolewski_szlif");
        ShapedRecipe recepturaSzlif = new ShapedRecipe(kluczSzlif, szlif);
        recepturaSzlif.shape("BBB", "BEB", "BBB");
        recepturaSzlif.setIngredient('B', Material.EMERALD_BLOCK);
        recepturaSzlif.setIngredient('E', new RecipeChoice.ExactChoice(esencja));
        Bukkit.addRecipe(recepturaSzlif);

        // --- ETAP 3: Klejnot Królewski ---
        ItemStack klejnot = new ItemStack(Material.EMERALD);
        ItemMeta klejnotMeta = klejnot.getItemMeta();
        if (klejnotMeta != null) {
            klejnotMeta.displayName(Component.text("Klejnot Królewski")
                    .color(NamedTextColor.GREEN).decoration(TextDecoration.ITALIC, false));
            klejnotMeta.lore(List.of(Component.text("Etap 3: Gotowy do osadzenia w koronie.").color(NamedTextColor.GRAY)));
            klejnotMeta.setCustomModelData(1004);
            klejnotMeta.getPersistentDataContainer().set(identyfikatorPrzedmiotu, PersistentDataType.STRING, "klejnot");
            klejnot.setItemMeta(klejnotMeta);
        }
        NamespacedKey kluczKlejnot = new NamespacedKey(this, "klejnot_krolewski");
        ShapedRecipe recepturaKlejnot = new ShapedRecipe(kluczKlejnot, klejnot);
        recepturaKlejnot.shape("NNN", "NSN", "NNN");
        recepturaKlejnot.setIngredient('N', Material.NETHERITE_INGOT);
        recepturaKlejnot.setIngredient('S', new RecipeChoice.ExactChoice(szlif));
        Bukkit.addRecipe(recepturaKlejnot);

        // --- ETAP 4: Korona Serwera ---
        ItemStack korona = new ItemStack(Material.GOLDEN_HELMET);
        ItemMeta koronaMeta = korona.getItemMeta();
        if (koronaMeta != null) {
            koronaMeta.displayName(Component.text("Korona Serwera")
                    .color(NamedTextColor.GOLD).bold(true).decoration(TextDecoration.ITALIC, false));
            koronaMeta.lore(List.of(
                    Component.text("Final: Potezne insygnium wladzy.").color(NamedTextColor.GRAY),
                    Component.text("Daje stale efekty po zalozeniu na glowe.").color(NamedTextColor.DARK_PURPLE)
            ));
            
            koronaMeta.setCustomModelData(1001);
            koronaMeta.getPersistentDataContainer().set(identyfikatorPrzedmiotu, PersistentDataType.STRING, "korona");
            korona.setItemMeta(koronaMeta);
        }
        NamespacedKey kluczKorona = new NamespacedKey(this, "korona_serwera");
        ShapedRecipe recepturaKorona = new ShapedRecipe(kluczKorona, korona);
        recepturaKorona.shape("NCN", "EKE", "GHG");
        recepturaKorona.setIngredient('G', Material.ENCHANTED_GOLDEN_APPLE);
        recepturaKorona.setIngredient('K', new RecipeChoice.ExactChoice(klejnot));
        recepturaKorona.setIngredient('E', new RecipeChoice.ExactChoice(esencja_mocy));
        recepturaKorona.setIngredient('N', Material.NETHER_STAR);
        recepturaKorona.setIngredient('H', Material.NETHERITE_HELMET);
        recepturaKorona.setIngredient('C', Material.HEAVY_CORE);
        Bukkit.addRecipe(recepturaKorona);
    }

    public static NamespacedKey getIdKlucza() {
        return identyfikatorPrzedmiotu;
    }

    @EventHandler
    public void przyCraftowaniu(CraftItemEvent event) {
        ItemStack wynik = event.getRecipe().getResult();
        if (wynik.hasItemMeta()) {
            String tag = wynik.getItemMeta().getPersistentDataContainer().get(identyfikatorPrzedmiotu, PersistentDataType.STRING);
            
            if ("korona".equals(tag)) {
                // Sprawdzamy limit przedmiotu
                if (czyKoronaIstniejeNaSerwerze()) {
                    event.setCancelled(true);
                    event.getWhoClicked().sendMessage(Component.text("Na serwerze moze istniec tylko jedna Korona Serwera! Zdobadz ja od obecnego wlasciciela.")
                            .color(NamedTextColor.RED));
                } else {
                    // Pobieramy nick gracza, który wytworzył przedmiot
                    String nazwaGracza = event.getWhoClicked().getName();
                    
                    // Przygotowanie globalnej wiadomości na czat
                    Component ogloszenie = Component.text("\n[!] ")
                            .color(NamedTextColor.RED).bold(true)
                            .append(Component.text("Legenda sie dopełniła! Gracz ")
                                    .color(NamedTextColor.YELLOW).bold(false))
                            .append(Component.text(nazwaGracza)
                                    .color(NamedTextColor.WHITE).bold(true))
                            .append(Component.text(" wytworzył jedyną i niepowtarzalną ")
                                    .color(NamedTextColor.YELLOW).bold(false))
                            .append(Component.text("Korone Serwera")
                                    .color(NamedTextColor.GOLD).bold(true))
                            .append(Component.text("!\n").color(NamedTextColor.YELLOW).bold(false));
                    
                    // Wysłanie komunikatu do wszystkich graczy online
                    Bukkit.broadcast(ogloszenie);
                }
            }
        }
    }

    @EventHandler
    public void przyPodnoszeniu(EntityPickupItemEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        ItemStack przedmiot = event.getItem().getItemStack();
        if (przedmiot.hasItemMeta()) {
            String tag = przedmiot.getItemMeta().getPersistentDataContainer().get(identyfikatorPrzedmiotu, PersistentDataType.STRING);
            if ("korona".equals(tag)) {
                if (czyKtosInnyMaKorona((Player) event.getEntity())) {
                    event.setCancelled(true);
                    event.getItem().remove();
                    event.getEntity().sendMessage(Component.text("Wykryto nielegalny duplikat Korony! Przedmiot zostal zniszczony.")
                            .color(NamedTextColor.DARK_RED).bold(true));
                }
            }
        }
    }

    public static boolean czyKoronaIstniejeNaSerwerze() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            for (ItemStack item : p.getInventory().getContents()) {
                if (item != null && item.hasItemMeta()) {
                    String tag = item.getItemMeta().getPersistentDataContainer().get(identyfikatorPrzedmiotu, PersistentDataType.STRING);
                    if ("korona".equals(tag)) return true;
                }
            }
        }
        for (World world : Bukkit.getWorlds()) {
            for (Item itemEntity : world.getEntitiesByClass(Item.class)) {
                ItemStack item = itemEntity.getItemStack();
                if (item.hasItemMeta()) {
                    String tag = item.getItemMeta().getPersistentDataContainer().get(identyfikatorPrzedmiotu, PersistentDataType.STRING);
                    if ("korona".equals(tag)) return true;
                }
            }
        }
        return false;
    }

    private boolean czyKtosInnyMaKorona(Player sprawdzanyGracz) {}
