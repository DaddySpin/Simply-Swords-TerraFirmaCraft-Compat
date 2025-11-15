const $Rarity = Java.loadClass("net.minecraft.world.item.Rarity")
const $UnaryOperator = Java.loadClass("java.util.function.UnaryOperator");
const $UtilsJS = Java.loadClass("dev.latvian.mods.kubejs.util.UtilsJS");
const $TextColor = Java.loadClass("net.minecraft.network.chat.TextColor");
const $Style = Java.loadClass("net.minecraft.network.chat.Style");
const withColorMethod = $Style.EMPTY.class.declaredMethods.filter(method => method.name.includes("m_131148_"))[0]

function createRarity(/** @type {string} */ name, /** @type {number} */ colorCode){
  return $Rarity["create(java.lang.String,java.util.function.UnaryOperator)"](name, withColor(colorCode))
}

function withColor(/** @type {number} */ colorCode){
  return $UtilsJS.makeFunctionProxy("startup", $UnaryOperator, (style) => {
    return withColorMethod.invoke(style, $TextColor.fromRgb(colorCode));
  })
}