package net.maximerubrum.simplyswords_tfc;

/**
 * Defines all Simply Swords weapon types with their stats and model configurations.
 */
public enum SimplySwordsWeapons {
    // Weapon format: (name, modelParent, attackSpeed, attackDamage, createBlade, isGreathammer)
    WARGLAIVE("warglaive", "simplyswords:item/template_warglaive", 1.8f, 4.0f, true, false),
    BACKHAND_BLADE("backhand_blade", "simplyswords:item/template_longdagger", 2.3f, 2.0f, true, false),
    CHAKRAM("chakram", "simplyswords:item/template_chakram", 1.0f, 3.0f, false, false),
    CLAYMORE("claymore", "simplyswords:item/template_claymore", 1.2f, 6.0f, true, false),
    CUTLASS("cutlass", "minecraft:item/handheld", 2.0f, 4.0f, true, false),
    DAGGER("dagger", "minecraft:item/handheld", 2.2f, 2.0f, true, false),
    DEER_HORNS("deer_horns", "minecraft:item/handheld", 2.1f, 3.0f, true, false),
    GLAIVE("glaive", "simplyswords:item/big_handheld", 1.4f, 4.0f, true, false),
    GRANDSWORD("grandsword", "simplyswords:item/template_claymore", 0.6f, 10.0f, true, false),
    GREAT_KATANA("great_katana", "simplyswords:item/template_katana", 1.4f, 5.0f, true, false),
    GREAT_SPEAR("great_spear", "simplyswords:item/long_handheld", 1.0f, 7.0f, true, false),
    GREATAXE("greataxe", "simplyswords:item/big_handheld", 0.9f, 7.0f, true, false),
    GREATHAMMER("greathammer", "simplyswords_tfc:item/template_reworked_greathammer_tintable", 0.8f, 8.0f, true, true),
    HALBERD("halberd", "simplyswords:item/long_handheld", 1.2f, 7.0f, true, false),
    KATANA("katana", "simplyswords:item/template_katana", 2.0f, 4.0f, true, false),
    KHOPESH("khopesh", "minecraft:item/handheld", 1.9f, 3.0f, true, false),
    LANCE("lance", "simplyswords:item/template_claymore", 1.6f, 4.0f, true, false),
    LONGSWORD("longsword", "simplyswords:item/template_longsword", 1.6f, 4.0f, true, false),
    PERNACH("pernach", "minecraft:item/handheld", 1.3f, 5.0f, true, false),
    QUARTERSTAFF("quarterstaff", "simplyswords:item/template_twinblade", 2.0f, 2.0f, true, false),
    RAPIER("rapier", "simplyswords:item/template_longsword", 2.2f, 3.0f, true, false),
    SAI("sai", "minecraft:item/handheld", 2.5f, 1.0f, true, false),
    SCYTHE("scythe", "simplyswords:item/big_handheld", 1.3f, 5.0f, true, false),
    SPEAR("spear", "simplyswords:item/big_handheld", 1.3f, 4.0f, true, false),
    TWINBLADE("twinblade", "simplyswords:item/template_twinblade", 2.0f, 4.0f, true, false);

    private final String name;
    private final String modelParent;
    private final float attackSpeed;
    private final float attackDamage;
    private final boolean createBlade;
    private final boolean isGreathammer;

    SimplySwordsWeapons(String name, String modelParent, float attackSpeed, float attackDamage, boolean createBlade, boolean isGreathammer) {
        this.name = name;
        this.modelParent = modelParent;
        this.attackSpeed = attackSpeed;
        this.attackDamage = attackDamage;
        this.createBlade = createBlade;
        this.isGreathammer = isGreathammer;
    }

    public String getName() { return name; }
    public String getModelParent() { return modelParent; }
    public float getAttackSpeed() { return attackSpeed; }
    public float getAttackDamage() { return attackDamage; }
    public boolean shouldCreateBlade() { return createBlade; }
    public boolean isGreathammer() { return isGreathammer; }
}
