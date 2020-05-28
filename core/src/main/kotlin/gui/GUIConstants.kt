package gui

data class ButtonSettings(val upPath: String,
                          val downPath: String,
                          val x: Float,
                          val y: Float,
                          val width: Float,
                          val height: Float,
                          val xS: Float = 0.0F,
                          val yS: Float = 0.0F,
                          val widthS: Float = 0.0F,
                          val heightS: Float = 0.0F,
                          val xE: Float = 0.0F,
                          val yE: Float = 0.0F,
                          val widthSE: Float = 0.0F,
                          val heightSE: Float = 0.0F)

object GUIConstants {

    /* ***Menu constants*** */


    /* ***Background constants*** */

    const val checkerImagePath: String = "menu_checker.png"
    const val checkerTextPath: String = "checkers_text.png"
    const val backWaitImagePath: String = "Wait.png"

    val starPath = listOf("Star.png", "Star2.png", "Star3.png", "Star4.png", "Star5.png")
    const val checkerImageX: Float = 150f
    const val checkerImageY: Float = 150f
    const val checkerImageSize: Float = 1.3f
    const val checkerImageRotation: Float = 15f
    const val checkerTextX: Float = 300f
    const val checkerTextY: Float = 480f
    const val checkerTextSize: Float = 0.2f

    val starX = listOf(1100f, 70f, 1350f, 1450f, 450f)
    val starY = listOf(350f, 640f, 100f, 650f, 80f)
    val starSize = listOf(500f, 300f, 350f, 300f, 270f)
    val starSmooth = listOf(0.8f, 1.3f, 0.85f, 1.5f, 0.55f)
    const val scaleToStarW: Float = 1.04f
    const val scaleToStarH: Float = 1.04f
    const val scaleFromStarW: Float = 1f
    const val scaleFromStarH: Float = 1f

    /* ***Download constants*** */

    const val downloadRotation: Float = 1000f
    const val downloadPosX: Float = 770f
    const val downloadPosY: Float = 450f
    const val downloadPosWidth: Float = 360f
    const val downloadPosHeight: Float = 360f
    const val slower: Float = 15f
    const val downloadSize: Int = 436
    const val downloadPath: String = "Wait.png"

    /* ***Invalid constants *** */

    const val invalidPath = "invalid.png"
    const val invalidPosX: Float = 380f
    const val invalidPosY: Float = 300f
    const val invalidPosWidth: Float = 1000f
    const val invalidPosHeight: Float = 500f
    const val invalidZIndex: Float = 10000f

    /* ***Button constants*** */

    val newGameButton = ButtonSettings("NewGameUp.png", "NewGameDown.png",
            600f, 500f, 600f, 300f,
            1500f, 850f, 300f,150f)

    val colorButton = ButtonSettings("ChooseUp.png", "ChooseDown.png",
            1000f, 275f, 400f, 200f)

    val exitButton = ButtonSettings(upPath = "ExitUp.png", downPath = "ExitDown.png",
            x = 800f, y = 110f, width = 260f, height = 130f,
            xS = 1350f, yS = 700f, widthS = 300f, heightS = 150f,
            xE = 100f, yE = 100f, widthSE = 300f, heightSE = 150f)

    val menuButton = ButtonSettings(upPath = "MenuUp.png", downPath = "MenuDown.png",
            x = 1300f, y = 700f, width = 400f, height = 200f,
            xS = 1500f, yS = 550f, widthS = 300f, heightS = 150f)

    const val red: Float = 0.90f
    const val green: Float = 0.96f
    const val blue: Float = 1f
    const val alpha: Float = 0.10f //background color


    /* ***Game constants*** */


    /* ***Init board constants*** */

    const val boardHeight: Int = 8
    const val boardWidth: Int = 8

    const val squareZ: Int = 1000
    const val checkerZ: Int = 2000
    const val moveZ: Int = 3000
    const val eatBoardZ: Int = 500 //Z-indexes of players

    const val eatBoardWidth: Float = 404f
    const val eatBoardHeight: Float = 400f
    const val eatBoardDeltaX: Float = 25f
    const val eatBoardDeltaY: Float = 20f
    const val blackEatPath: String = "BlackEat.png"
    const val whiteEatPath: String = "WhiteEat.png"
    const val whiteEatX: Float = 20f
    const val whiteEatY: Float = 550f
    const val blackEatX: Float = 1330f
    const val blackEatY: Float = 100f


    const val blackCheckerPath: String = "blackChecker.png"
    const val whiteCheckerPath: String = "whiteChecker.png"
    const val spriteCheckersInRow: Int = 3
    const val spriteCheckersInCol: Int = 2
    const val checkerWidth: Float = 120f
    const val checkerHeight: Float = 120f
    const val whiteCheckerSizeX: Int = 626
    const val whiteCheckerSizeY: Int = 626
    const val blackCheckerSizeX: Int = 644
    const val blackCheckerSizeY: Int = 646


    const val blackSquarePath: String = "black_square.png"
    const val whiteSquarePath: String = "white_square.png"
    const val squareWidth: Float = 120f
    const val squareHeight: Float = 120f
    const val squareSpaceX: Float = 110f
    const val squareSpaceY: Float = 110f
    const val squareLeftCornerX: Float = 420f
    const val squareLeftCornerY: Float = 70f

    const val myLastRow: Int = 2
    const val oppositeFirstRow: Int = 5
    const val evenSquare: Int = 2


    /* ***Checker constants *** */

    const val initTime: Int = 0
    const val initStage: Int = 0
    const val maxTime: Int = 8
    const val lastStage: Int = 6
    const val simpleChecker: Int = 0
    const val queenChecker: Int = 5
    val defaultColor: core.Color = core.Color.WHITE


    /* ***Eat board constants *** */

    const val initEatInRow: Int = 0
    const val initEatInCol: Int = 0
    const val eatDeltaX: Int = 50
    const val eatDeltaY: Int = 60
    const val eatMaxInRow: Int = 4


    /* ***Become queen constants*** */

    const val toCenterX: Float = 600f
    const val toCenterY: Float = 300f
    const val scaleToCenterWidth: Float = 4f
    const val scaleToCenterHeight: Float = 4f
    const val scaleBackWidth: Float = 1f
    const val scaleBackHeight: Float = 1f
    const val smooth: Float = 0.8f

    /* *** End game constants *** */

    const val backImagePath: String = "Back.png"
    const val backWidth: Float = 2000f
    const val backHeight: Float = 1125f
    const val backPosX: Float = 0f
    const val backPosY: Float = 0f
    const val playerWins: String = "Won.png"
    const val computerWins: String = "Lose.png"
    const val stateX: Float = 400f
    const val stateY: Float = 300f
    const val stateWidth: Float = 1300f
    const val stateHeight: Float = 400f
    const val stateSmooth: Float = 1.2f

    /* *** Choose color constants *** */

    const val whiteCheckerChoose: String = "white_checker.png"
    const val blackCheckerChoose: String = "black_checker.png"
    const val whiteCheckerChooseX: Float = 100f
    const val whiteCheckerChooseY: Float = 280f
    const val whiteCheckerChooseSize: Float = 800f
    const val whiteCheckerChooseSmooth: Float = 0.8f
    const val blackCheckerChooseX: Float = 900f
    const val blackCheckerChooseY: Float = -100f
    const val blackCheckerChooseSize: Float = 800f
    const val blackCheckerChooseSmooth: Float = 0.9f
    const val whiteCheckerChooseState: Int = 1
    const val blackCheckerChooseState: Int = 0
    const val checkerChooseX: Float = 100f
    const val checkerChooseY: Float = 100f
    const val checkerChooseXS: Float = 50f
    const val checkerChooseYS: Float = 200f
    const val checkerChooseWF: Float = 1079f
    const val checkerChooseHF: Float = 193f
    const val checkerChooseW: Int = 4798
    const val checkerChooseH: Int = 857
    const val checkerChooseWS: Float = 809f
    const val checkerChooseHS: Float = 144f
    const val checkerChoosePath: String = "textPlayer.png"
    const val checkersColorsCount: Int = 2

}