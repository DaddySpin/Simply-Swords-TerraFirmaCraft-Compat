let weapons = global.simply_swords_weapons

ServerEvents.tags('item', event => {
	for (let weaponEntry in weapons) {
        let weapon = weapons[weaponEntry]

		event.add(`kubejs:colored_steel_${weaponEntry}`, `kubejs:red_steel_${weaponEntry}`)
		event.add(`kubejs:colored_steel_${weaponEntry}`, `kubejs:blue_steel_${weaponEntry}`)
	}
})