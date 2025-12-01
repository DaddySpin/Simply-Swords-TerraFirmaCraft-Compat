# Simply Swords x TerraFirmaCraft Compat

A NeoForge 1.21.1 compatibility mod that integrates [Simply Swords](https://www.curseforge.com/minecraft/mc-mods/simply-swords) weapons with [TerraFirmaCraft](https://www.curseforge.com/minecraft/mc-mods/terrafirmacraft) mechanics.

## Features

- **TFC Metal Weapons**: Adds TFC metal variants (Copper, Bronze, Bismuth Bronze, Black Bronze, Wrought Iron, Steel, Black Steel, Blue Steel, Red Steel) for all Simply Swords weapon types.
- **Crafting Mechanics**:
  - **Anvil**: Forge weapon blades from Ingots, Double Ingots, or Sheets.
  - **Heating**: Melt weapons and blades back into metal fluid.
  - **Assembly**: Craft finished weapons using a Blade + Handle + Extra (e.g. rivet/nail) from [TFC More Items](https://www.curseforge.com/minecraft/mc-mods/tfc-more-items).
- **Metal Tinting**: Weapon blades are dynamically colored based on the TFC metal type.
- **Creative Tab**: Includes a "Simply Swords TFC" creative tab with all weapons.
- **Simply Swords Integration**: Weapons are tagged correctly to work with Simply Swords mechanics.

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.216+
- TerraFirmaCraft (1.21.1)
- Simply Swords (1.21.1)
- TFC More Items (1.21.1) - Required for crafting components like handles/nails

### Transitive Dependencies

These are required by the mods above:
- Patchouli
- Architectury API
- Fzzy Config (and Kotlin For Forge)

## Changelog

### v1.0.0 (Initial Release)
- Added all Simply Swords weapon types for 9 TFC metals
- Implemented TFC anvil recipes for blade forging
- Added heating/melting recipes for weapons and blades
- Created custom tintable greathammer 3D model
- Fixed blade texture transparency (alpha channel in color tinting)
- Fixed greathammer head texture mapping

## Roadmap

### Completed
- [x] Core weapon registration for all TFC metals
- [x] Item model generation with Simply Swords templates
- [x] Blade/handle texture layering with metal tinting
- [x] TFC anvil recipes for blade forging
- [x] Heating recipes for melting weapons
- [x] Creative tab integration

### Planned
- [ ] Weapon attribute balancing based on TFC metal tiers
- [ ] JEI/EMI recipe integration improvements
- [ ] Weapon durability scaling with metal quality

## Development Setup

1. Clone the repository.
2. Create a `libs` folder in the root directory.
3. Download the following mod JARs and place them in `libs/`:
   - `TerraFirmaCraft-NeoForge-1.21.1-4.0.11-beta.jar`
   - `simplyswords-neoforge-1.62.0-1.21.1.jar`
   - `TFC-items-1.21.1-neoforge-1.2.1.jar`
   - `Patchouli-1.21.1-92-NEOFORGE.jar`
   - `architectury-13.0.8-neoforge.jar`
   - `fzzy_config-0.7.3+1.21+neoforge.jar`
   - `kotlinforforge-5.10.0-all.jar`
4. Run `./gradlew runData` to generate resources.
5. Run `./gradlew build` to compile.

## Building

```bash
./gradlew build
```

The compiled JAR will be in `build/libs/`.

## Credits

- Original KubeJS implementation concept inspired by [DaddySpin's Simply-Swords-TerraFirmaCraft-Compat](https://github.com/DaddySpin/Simply-Swords-TerraFirmaCraft-Compat)
- Ported to NeoForge Java mod by alephnull

## License

All Rights Reserved
