let array = global.simply_swords_uniques

let awakening_tablet = "awakening_tablet_of"
let upgrade_tablet = "upgrade_tablet_of"

const awakeningRarity = createRarity("AWAKENING", 0xb82c0d)
const upgradeRarity = createRarity("UPGRADE", 0x1eb80d)

StartupEvents.registry('item', event => {
    for (let unique in array) {
        let modid = unique.split(":")[0]
        let itemid = unique.split(":")[1]

        if (array[unique].tablet == "awakening") {
            event.create(`kubejs:${awakening_tablet}_${itemid}`).texture('kubejs:item/stone_tablet_red').unstackable().tag('kubejs:tablets').tag('kubejs:tablets/awakening').rarity(awakeningRarity)
        }
        else if (array[unique].tablet == "upgrade") {
            event.create(`kubejs:${upgrade_tablet}_${itemid}`).texture('kubejs:item/stone_tablet_green').unstackable().tag('kubejs:tablets').tag('kubejs:tablets/upgrade').rarity(upgradeRarity)
        }
    }
})