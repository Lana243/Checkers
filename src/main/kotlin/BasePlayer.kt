abstract class BasePlayer<ModelT : BaseModel>(val color : Color) {
    abstract fun makeTurn(model : ModelT) : BaseTurn
}