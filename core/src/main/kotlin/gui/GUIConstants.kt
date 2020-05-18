package gui

object GUIConstants {


    /* ***Menu constants*** */


    /* ***Background constants*** */

    const val checkerImagePath: String = "menu_checker.png"
    const val checkerTextPath: String = "checkers_text.png"
    const val starPath: String = "Star.png"
    const val starPath2: String = "Star2.png"
    const val starPath3: String = "Star3.png"
    const val starPath4: String = "Star4.png"
    const val starPath5: String = "Star5.png"
    const val checkerImageX: Float = 150f
    const val checkerImageY: Float = 150f
    const val checkerImageSize: Float = 1.3f
    const val checkerImageRotation: Float = 15f
    const val checkerTextX: Float = 250f
    const val checkerTextY: Float = 420f
    const val checkerTextSize: Float = 0.2f
    const val starX: Float = 1070f
    const val starY: Float = 350f
    const val starSize: Float = 320f
    const val starSmooth: Float = 0.8f
    const val starX2: Float = 70f
    const val starY2: Float = 640f
    const val starSize2: Float = 280f
    const val starSmooth2: Float = 1.3f
    const val starX3: Float = 1310f
    const val starY3: Float = 130f
    const val starSize3: Float = 270f
    const val starSmooth3: Float = 0.85f
    const val starX4: Float = 1370f
    const val starY4: Float = 670f
    const val starSize4: Float = 200f
    const val starSmooth4: Float = 1.5f
    const val starX5: Float = 450f
    const val starY5: Float = 80f
    const val starSize5: Float = 230f
    const val starSmooth5: Float = 0.55f
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
    const val newGameButtonX: Float = 600f
    const val newGameButtonY: Float = 450f
    const val newGameButtonWidth: Float = 400f
    const val newGameButtonHeight: Float = 200f
    const val newGameButtonXS: Float = 1280f
    const val newGameButtonYS: Float = 760f
    const val newGameButtonWidthS: Float = 200f
    const val newGameButtonHeightS: Float = 100f

    const val exitButtonUpPath: String = "ExitUp.png"
    const val exitButtonDownPath: String = "ExitDown.png"
    const val exitButtonX: Float = 700f
    const val exitButtonY: Float = 200f
    const val exitButtonXE: Float = 100f
    const val exitButtonYE: Float = 100f
    const val exitButtonWidth: Float = 400f
    const val exitButtonHeight: Float = 200f
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
    const val boardWeight: Int = 8

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

}