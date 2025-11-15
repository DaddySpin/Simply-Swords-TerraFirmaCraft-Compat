let weapons = global.simply_swords_weapons
let uniques = global.simply_swords_uniques

ServerEvents.recipes(event => {
    for (let weaponEntry in weapons) {
        let weapon = weapons[weaponEntry]

        for (let uniqueEntry in uniques) {
            let unique = uniques[uniqueEntry]

            let modid = uniqueEntry.split(":")[0]
            let itemid = uniqueEntry.split(":")[1]

            if (weaponEntry == unique.type) {
                if (unique.tablet == "awakening") {
                    event.recipes.tfc.advanced_shaped_crafting(
                        TFC.itemStackProvider.of(uniqueEntry).copyForgingBonus(),
                        [
                            ' A ',
                            'B  ',
                            '   '
                        ], {
                            A: `${weapon.mod}:runic_${weaponEntry}`,
                            B: `kubejs:awakening_tablet_of_${itemid}`
                        },
                        0,
                        1
                    ).id(`kubejs:tfc/crafting/${itemid}`)
                }
                else if (unique.tablet == "upgrade") {
                    event.remove({output: uniqueEntry})
                    event.recipes.tfc.advanced_shaped_crafting(
                        TFC.itemStackProvider.of(uniqueEntry).copyForgingBonus(),
                        [
                            ' A ',
                            'B  ',
                            '   '
                        ], {
                            A: `${unique.previous}`,
                            B: `kubejs:upgrade_tablet_of_${itemid}`
                        },
                        0,
                        1
                    ).id(`kubejs:tfc/crafting/${itemid}`)
                }
            }
        }
    }
})

ServerEvents.tags('item', event => {
	for (let weaponEntry in weapons) {
        for (let uniqueEntry in uniques) {
            let unique = uniques[uniqueEntry]
            if (weaponEntry == unique.type) {
                if (unique.tablet == "awakening") {
                    event.add(`kubejs:craftable_unique`, uniqueEntry)
                }
                else if (unique.tablet == "upgrade") {
                    event.add(`kubejs:upgrade_unique`, uniqueEntry)
                }
            }
        }
    }
})