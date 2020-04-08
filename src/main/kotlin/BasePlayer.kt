abstract class BasePlayer(open val color : Int) {
    abstract fun makeTurn(model : BaseModel) : BaseTurn?
}