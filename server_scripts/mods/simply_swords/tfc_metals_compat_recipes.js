ServerEvents.recipes(event => {
    function fahrenheitToCelsius(fahrenheit) {
        return (fahrenheit - 32) * 5 / 9;
    }
    
    let metal_values = {
        ingot: 100,
        double_ingot: 200,
        sheet: 200,
        double_sheet: 400,
        knife_blade: 100,
        scythe_blade: 100,
        axe_head: 100,
    }

    let recipes_list = {
        warglaive: {
            handle: "hilt",
            input: "double_ingot",
            welding: false,
        },
        backhand_blade: {
            handle: "hilt",
            input: "double_ingot",
            welding: false,
        },
        claymore: {
            handle: "hilt",
            input: "double_sheet",
            welding: false,
        },
        cutlass: {
            handle: "hilt",
            input: "double_ingot",
            welding: false,
        },
        dagger: {
            handle: "hilt",
            input: "ingot",
            welding: false,
        },
        deer_horns: {
            handle: "hilt",
            input: "ingot",
            welding: false,
        },
        glaive: {
            handle: "pole",
            input: "double_ingot",
            welding: false,
        },
        grandsword: {
            handle: "hilt",
            input: "double_sheet",
            welding: false,
        },
        great_katana: {
            handle: "hilt",
            input: "double_sheet",
            welding: false,
        },
        great_spear: {
            handle: "pole",
            input: "double_sheet",
            welding: false,
        },
        greataxe: {
            handle: "pole",
            input: "double_sheet",
            welding: false,
        },
        greathammer: {
            handle: "pole",
            input: "double_sheet",
            welding: false,
        },
        halberd: {
            handle: "pole",
            input: "sheet",
            welding: false,
        },
        katana: {
            handle: "hilt",
            input: "double_ingot",
            welding: false,
        },
        khopesh: {
            handle: "hilt",
            input: "double_ingot",
            welding: false,
        },
        lance: {
            handle: "hilt",
            input: "double_sheet",
            welding: false,
        },
        longsword: {
            handle: "hilt",
            input: "double_sheet",
            welding: false,
        },
        pernach: {
            handle: "hilt",
            input: "double_ingot",
            welding: false,
        },
        quarterstaff: {
            handle: "pole",
            input: "double_ingot",
            welding: false,
        },
        rapier: {
            handle: "hilt",
            input: "sheet",
            welding: false,
        },
        sai: {
            handle: "hilt",
            input: "knife_blade",
            welding: false,
        },
        scythe: {
            handle: "pole",
            input: "double_sheet",
            welding: false,
        },
        spear: {
            handle: "pole",
            input: "double_ingot",
            welding: false,
        },
        twinblade: {
            handle: "hilt",
            input: "sheet",
            welding: false,
        },
    }

    let metals = global.tfc_weapon_metals

    for (let metalEntry in metals) {
        let metal = metals[metalEntry]
        
        for (let weapon in recipes_list) {
             let weaponData = recipes_list[weapon];
            event.recipes.tfc.advanced_shaped_crafting(
                TFC.itemStackProvider.of(`kubejs:${metalEntry}_${weapon}`).copyForgingBonus(),
                [
                    ' A ',
                    'B  ',
                    '   '
                ], {
                    A: `kubejs:${metalEntry}_${weapon}_blade`,
                    B: `magistuarmory:${weaponData.handle}`
                },
                0,
                1
            ).id(`kubejs:tfc/crafting/${metalEntry}_${weapon}`)
            if (weaponData.welding == true) {
                event.recipes.tfc.welding(
                    TFC.itemStackProvider.of(`kubejs:${metalEntry}_${weapon}_blade`).copyForgingBonus(),
                    `tfc:metal/${weaponData.input[0]}/${metalEntry}`,
                    `tfc:metal/${weaponData.input[1]}/${metalEntry}`,
                    metal.anvil_tier
                ).id(`kubejs:tfc/welding/${metalEntry}_${weapon}_blade`)
            }
            else if (weaponData.welding == false) {
                event.recipes.tfc.anvil(
                    TFC.itemStackProvider.of(`kubejs:${metalEntry}_${weapon}_blade`),
                    `tfc:metal/${weaponData.input}/${metalEntry}`,
                    [
                        "punch_last",
                        "hit_not_last",
                        "bend_any"
                    ]
                )
                .tier(metal.anvil_tier)
                .bonus(true)
                .id(`kubejs:tfc/anvil/${metalEntry}_${weapon}_blade`)
            }
            
            // Calculate metal value based on inputs
            let calculated_metal_value;
            if (weaponData.welding == true) {
                // For welding recipes, sum the values of both inputs
                calculated_metal_value = metal_values[weaponData.input[0]] + metal_values[weaponData.input[1]];
            } else {
                // For anvil recipes, use the single input value
                calculated_metal_value = metal_values[weaponData.input];
            }
            
            event.recipes.tfc.heating(`kubejs:${metalEntry}_${weapon}_blade`, fahrenheitToCelsius(metal.melting_point)).resultFluid(Fluid.of(`tfc:metal/${metalEntry}`, calculated_metal_value)).id(`kubejs:tfc/heating/${metalEntry}_${weapon}_blade`)
            event.recipes.tfc.heating(`kubejs:${metalEntry}_${weapon}`, fahrenheitToCelsius(metal.melting_point)).resultFluid(Fluid.of(`tfc:metal/${metalEntry}`, calculated_metal_value)).id(`kubejs:tfc/heating/${metalEntry}_${weapon}`)
        }
        event.recipes.tfc.heating(`kubejs:${metalEntry}_chakram`, fahrenheitToCelsius(metal.melting_point)).resultFluid(Fluid.of(`tfc:metal/${metalEntry}`, 200)).id(`kubejs:tfc/heating/${metalEntry}_chakram`)
        event.recipes.tfc.anvil(
            TFC.itemStackProvider.of(`kubejs:${metalEntry}_chakram`),
            `tfc:metal/double_ingot/${metalEntry}`,
            [
                "punch_last",
                "hit_not_last",
                "bend_any"
            ]
        )
        .tier(metal.anvil_tier)
        .bonus(true)
        .id(`kubejs:tfc/anvil/${metalEntry}_chakram`)
    }
})