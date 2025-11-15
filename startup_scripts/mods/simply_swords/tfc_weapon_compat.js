let weapons = global.simply_swords_weapons
let metals = global.tfc_weapon_metals

StartupEvents.registry('item', event => {
    for (let weaponEntry in weapons) {
        let weapon = weapons[weaponEntry]
        let JsonAnim = JsonIO.read(`kubejs/data/kubejs/weapon_attributes/${weaponEntry}.json`)
        for (let metalEntry in metals) {
            let metal = metals[metalEntry]
            if (weaponEntry != "greathammer") {
                event.create(`kubejs:${metalEntry}_${weaponEntry}`, 'sword')
                    .modelJson({
                        parent: `${weapon.model}`,
                        textures: {
                            layer0: `kubejs:item/simply_swords_templates/blade_${weaponEntry}`,
                            layer1: `kubejs:item/simply_swords_templates/handle_${weaponEntry}`
                        }
                    })
                    .color(0, metal.hexcode)
                    .tag(`simplymore:weapon_types/${weaponEntry}${weaponEntry.endsWith('ss') ? 'es' : weaponEntry.endsWith('s') ? '' : 's'}`)
                    .speedBaseline(weapon.attack_speed-4)
                    .attackDamageBaseline(weapon.attack_damage+metal.material_attack_damage-3)
                    .maxDamage(metal.durability)
                    .rarity(metal.rarity)
            }
            else if (weaponEntry == "greathammer") {
                event.create(`kubejs:${metalEntry}_${weaponEntry}`, 'sword')
                    .modelJson({
                        parent: `${weapon.model}`,
                        textures: {
                            texture: `kubejs:item/simply_swords_templates/${weaponEntry}`
                        }
                    })
                    .color(0, metal.hexcode)
                    .tag(`simplymore:weapon_types/${weaponEntry}${weaponEntry.endsWith('ss') ? 'es' : weaponEntry.endsWith('s') ? '' : 's'}`)
                    .speedBaseline(weapon.attack_speed-4)
                    .attackDamageBaseline(weapon.attack_damage+metal.material_attack_damage-3)
                    .maxDamage(metal.durability)
                    .rarity(metal.rarity)
            }
            JsonIO.write(`kubejs/data/kubejs/weapon_attributes/${metalEntry}_${weaponEntry}.json`, JsonAnim)
            
            if (weapon.create_blade == true) {
                event.create(`kubejs:${metalEntry}_${weaponEntry}_blade`)
                    .textureJson({
                        layer0: `kubejs:item/simply_swords_blades/blade_${weaponEntry}`,
                        })
                    .color(0, metal.hexcode)
                    .rarity(metal.rarity)
            }
        }
    }
})