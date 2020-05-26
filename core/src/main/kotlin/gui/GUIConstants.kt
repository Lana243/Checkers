package gui

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
    const val checkerTextX: Float = 250f
    const val checkerTextY: Float = 420f
    const val checkerTextSize: Float = 0.2f

    val starX = listOf(1070f, 70f, 1310f, 1370f, 450f)
    val starY = listOf(350f, 640f, 130f, 670f, 80f)
    val starSize = listOf(320f, 280f, 270f, 200f, 230f)
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

    /* ***Button constants*** */

    const val newGameButtonUpPath: String = "NewGameUp.png"
    const val newGameButtonDownPath: String = "NewGameDown.png"
    const val newGameButtonX: Float = 560f
    const val newGameButtonY: Float = 430f
    const val newGameButtonWidth: Float = 400f
    const val newGameButtonHeight: Float = 200f
    const val newGameButtonXS: Float = 1280f
    const val newGameButtonYS: Float = 760f
    const val newGameButtonWidthS: Float = 200f
    const val newGameButtonHeightS: Float = 100f

    const val colorButtonUpPath: String = "ChooseUp.png"
    const val colorButtonDownPath: String = "ChooseDown.png"
    const val colorButtonX: Float = 1000f
    const val colorButtonY: Float = 275f
    const val colorButtonWidth: Float = 320f
    const val colorButtonHeight: Float = 160f

    const val exitButtonUpPath: String = "ExitUp.png"
    const val exitButtonDownPath: String = "ExitDown.png"
    const val exitButtonX: Float = 800f
    const val exitButtonY: Float = 110f
    const val exitButtonXE: Float = 100f
    const val exitButtonYE: Float = 100f
    const val exitButtonWidth: Float = 260f
    const val exitButtonHeight: Float = 130f
    const val exitButtonXS: Float = 1220f
    const val exitButtonYS: Float = 625f
    const val exitButtonWidthS: Float = 200f
    const val exitButtonHeightS: Float = 100f
    const val exitButtonWidthSE: Float = 300f
    const val exitButtonHeightSE: Float = 150f

    const val menuButtonUpPath: String = "MenuUp.png"
    const val menuButtonDownPath: String = "MenuDown.png"
    const val menuButtonX: Float = 1150f
    const val menuButtonY: Float = 700f
    const val menuButtonWidth: Float = 300f
    const val menuButtonHeight: Float = 150f
    const val menuButtonXS: Float = 1330f
    const val menuButtonYS: Float = 500f
    const val menuButtonWidthS: Float = 200f
    const val menuButtonHeightS: Float = 100f

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

    const val eatBoardWidth: Float = 300f
    const val eatBoardHeight: Float = 300f
    const val eatBoardDeltaX: Float = 25f
    const val eatBoardDeltaY: Float = 20f
    const val blackEatPath: String = "BlackEat.png"
    const val whiteEatPath: String = "WhiteEat.png"
    const val whiteEatX: Float = 50f
    const val whiteEatY: Float = 550f
    const val blackEatX: Float = 1150f
    const val blackEatY: Float = 100f


    const val blackCheckerPath: String = "blackChecker.png"
    const val whiteCheckerPath: String = "whiteChecker.png"
    const val spriteCheckersInRow: Int = 3
    const val spriteCheckersInCol: Int = 2
    const val checkerWidth: Float = 90f
    const val checkerHeight: Float = 90f
    const val whiteCheckerSizeX: Int = 626
    const val whiteCheckerSizeY: Int = 626
    const val blackCheckerSizeX: Int = 644
    const val blackCheckerSizeY: Int = 646


    const val blackSquarePath: String = "black_square.png"
    const val whiteSquarePath: String = "white_square.png"
    const val squareWidth: Float = 90f
    const val squareHeight: Float = 90f
    const val squareSpaceX: Float = 85f
    const val squareSpaceY: Float = 85f
    const val squareLeftCornerX: Float = 420f
    const val squareLeftCornerY: Float = 150f

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
    const val backWidth: Float = 1600f
    const val backHeight: Float = 900f
    const val backPosX: Float = 0f
    const val backPosY: Float = 0f
    const val whiteWins: String = "Won.png"
    const val blackWins: String = "Lose.png"
    const val stateX: Float = 440f
    const val stateY: Float = 370f
    const val stateWidth: Float = 800f
    const val stateHeight: Float = 250f
    const val stateSmooth: Float = 1.2f

    /* *** Choose color constants *** */

    const val whiteCheckerChoose: String = "white_checker.png"
    const val blackCheckerChoose: String = "black_checker.png"
    const val whiteCheckerChooseX: Float = 100f
    const val whiteCheckerChooseY: Float = 280f
    const val whiteCheckerChooseSize: Float = 600f
    const val whiteCheckerChooseSmooth: Float = 0.8f
    const val blackCheckerChooseX: Float = 900f
    const val blackCheckerChooseY: Float = 80f
    const val blackCheckerChooseSize: Float = 600f
    const val blackCheckerChooseSmooth: Float = 0.9f
    const val whiteCheckerChooseState: Int = 1
    const val blackCheckerChooseState: Int = 0
    const val checkerChooseX: Float = 100f
    const val checkerChooseY: Float = 100f
    const val checkerChooseWF: Float = 719.7f
    const val checkerChooseHF: Float = 128.55f
    const val checkerChooseW: Int = 4798
    const val checkerChooseH: Int = 857
    const val checkerChoosePath: String = "textPlayer.png"
    const val checkersColorsCount: Int = 2

}