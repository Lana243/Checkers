abstract class BasePlayer(open val color : Color) {
    abstract fun makeTurn(model : BaseModel) : BaseTurn?
}