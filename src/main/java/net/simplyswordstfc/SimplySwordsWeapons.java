package net.simplyswordstfc;

/**
 * Defines all Simply Swords weapon types with their stats, model configurations, and damage types.
 * Damage types follow TFC conventions: slashing, piercing, crushing.
 */
public enum SimplySwordsWeapons {
    // Format: (name, modelParent, attackSpeed, attackDamage, createBlade, isGreathammer, damageType)
    WARGLAIVE("warglaive", "simplyswords:item/template_warglaive", 1.8f, 4.0f, true, false, DamageType.SLASHING),
    BACKHAND_BLADE("backhand_blade", "simplyswords:item/template_longdagger", 2.3f, 2.0f, true, false, DamageType.SLASHING),
    CHAKRAM("chakram", "simplyswords:item/template_chakram", 1.0f, 3.0f, false, false, DamageType.SLASHING),
    CLAYMORE("claymore", "simplyswords:item/template_claymore", 1.2f, 6.0f, true, false, DamageType.SLASHING),
    CUTLASS("cutlass", "minecraft:item/handheld", 2.0f, 4.0f, true, false, DamageType.SLASHING),
    DAGGER("dagger", "minecraft:item/handheld", 2.2f, 2.0f, true, false, DamageType.PIERCING),
    DEER_HORNS("deer_horns", "minecraft:item/handheld", 2.1f, 3.0f, true, false, DamageType.PIERCING),
    GLAIVE("glaive", "simplyswords:item/big_handheld", 1.4f, 4.0f, true, false, DamageType.SLASHING),
    GRANDSWORD("grandsword", "simplyswords:item/template_claymore", 0.6f, 10.0f, true, false, DamageType.SLASHING),
    GREAT_KATANA("great_katana", "simplyswords:item/template_katana", 1.4f, 5.0f, true, false, DamageType.SLASHING),
    GREAT_SPEAR("great_spear", "simplyswords:item/long_handheld", 1.0f, 7.0f, true, false, DamageType.PIERCING),
    GREATAXE("greataxe", "simplyswords:item/big_handheld", 0.9f, 7.0f, true, false, DamageType.SLASHING),
    GREATHAMMER("greathammer", "simplyswords_tfc:item/template_reworked_greathammer_tintable", 0.8f, 8.0f, true, true, DamageType.CRUSHING),
    HALBERD("halberd", "simplyswords:item/long_handheld", 1.2f, 7.0f, true, false, DamageType.SLASHING),
    KATANA("katana", "simplyswords:item/template_katana", 2.0f, 4.0f, true, false, DamageType.SLASHING),
    KHOPESH("khopesh", "minecraft:item/handheld", 1.9f, 3.0f, true, false, DamageType.SLASHING),
    LANCE("lance", "simplyswords:item/template_claymore", 1.6f, 4.0f, true, false, DamageType.PIERCING),
    LONGSWORD("longsword", "simplyswords:item/template_longsword", 1.6f, 4.0f, true, false, DamageType.SLASHING),
    PERNACH("pernach", "minecraft:item/handheld", 1.3f, 5.0f, true, false, DamageType.CRUSHING),
    QUARTERSTAFF("quarterstaff", "simplyswords:item/template_twinblade", 2.0f, 2.0f, true, false, DamageType.CRUSHING),
    RAPIER("rapier", "simplyswords:item/template_longsword", 2.2f, 3.0f, true, false, DamageType.PIERCING),
    SAI("sai", "minecraft:item/handheld", 2.5f, 1.0f, true, false, DamageType.PIERCING),
    SCYTHE("scythe", "simplyswords:item/big_handheld", 1.3f, 5.0f, true, false, DamageType.SLASHING),
    SPEAR("spear", "simplyswords:item/big_handheld", 1.3f, 4.0f, true, false, DamageType.PIERCING),
    TWINBLADE("twinblade", "simplyswords:item/template_twinblade", 2.0f, 4.0f, true, false, DamageType.SLASHING);

    public enum DamageType {
        SLASHING("deals_slashing_damage"),
        PIERCING("deals_piercing_damage"),
        CRUSHING("deals_crushing_damage");

        private final String tfcTagName;

        DamageType(String tfcTagName) {
            this.tfcTagName = tfcTagName;
        }

        public String getTfcTagName() {
            return tfcTagName;
        }
    }

    private final String name;
    private final String modelParent;
    private final float attackSpeed;
    private final float attackDamage;
    private final boolean createBlade;
    private final boolean isGreathammer;
    private final DamageType damageType;

    SimplySwordsWeapons(String name, String modelParent, float attackSpeed, float attackDamage, 
                        boolean createBlade, boolean isGreathammer, DamageType damageType) {
        this.name = name;
        this.modelParent = modelParent;
        this.attackSpeed = attackSpeed;
        this.attackDamage = attackDamage;
        this.createBlade = createBlade;
        this.isGreathammer = isGreathammer;
        this.damageType = damageType;
    }

    public String getName() { return name; }
    public String getModelParent() { return modelParent; }
    public float getAttackSpeed() { return attackSpeed; }
    public float getAttackDamage() { return attackDamage; }
    public boolean shouldCreateBlade() { return createBlade; }
    public boolean isGreathammer() { return isGreathammer; }
    public DamageType getDamageType() { return damageType; }
}
