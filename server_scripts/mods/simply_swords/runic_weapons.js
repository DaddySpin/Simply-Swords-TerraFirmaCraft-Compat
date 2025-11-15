let weapons = global.simply_swords_weapons

ServerEvents.recipes(event => {
    for (let weaponEntry in weapons) {
        let weapon = weapons[weaponEntry]

        event.replaceInput(
            { input: `simplyswords:netherite_${weaponEntry}` },
            `simplyswords:netherite_${weaponEntry}`,
            `#kubejs:colored_steel_${weaponEntry}`
        )
        event.replaceInput(
            { input: `simplymore:netherite_${weaponEntry}` },
            `simplymore:netherite_${weaponEntry}`,
            `#kubejs:colored_steel_${weaponEntry}`
        )
	}
})