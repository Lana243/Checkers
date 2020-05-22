package core

abstract class BasePlayer<ModelT : BaseModel>(val color : Color) {
    abstract suspend fun makeTurn(model: ModelT): BaseTurn
}