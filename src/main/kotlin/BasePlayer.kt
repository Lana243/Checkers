abstract class BasePlayer(val color : Color) {
    abstract fun makeTurn(model : BaseModel) : BaseTurn
}