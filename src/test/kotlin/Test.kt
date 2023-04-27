import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.PrintStream

class CutMainTest {

    @Test
    fun testCharacterSubstring() {
        val cutMain = CutMain()
        val outputFilePath = "src/test/resources/output.txt"
        cutMain.doMain(arrayOf("-c", "-o", outputFilePath, "src/main/kotlin/input.txt", "4"))

        val expectedOutput = "l\no\nl"
        val outputFile = File(outputFilePath)
        assertTrue(outputFile.exists())
        assertEquals(expectedOutput, outputFile.readText().trim())
    }

    @Test
    fun testWordSubstring() {
        val cutMain = CutMain()
        val outputFilePath = "src/test/resources/output.txt"
        cutMain.doMain(arrayOf("-w", "-o", outputFilePath, "src/main/kotlin/input.txt", "4"))

        val expectedOutput = "day\ncut\nday"
        val outputFile = File(outputFilePath)
        assertTrue(outputFile.exists())
        assertEquals(expectedOutput, outputFile.readText().trim())
    }

    @Test
    fun testWordRange() {
        val cutMain = CutMain()
        val outputFilePath = "src/test/resources/output.txt"
        cutMain.doMain(arrayOf("-w", "-o", outputFilePath, "src/main/kotlin/input.txt", "1-4"))

        val expectedOutput = "Hello World new day\nSecond line for cut\nHello World new day"
        val outputFile = File(outputFilePath)
        assertTrue(outputFile.exists())
        assertEquals(expectedOutput, outputFile.readText().trim())
    }

    @Test
    fun testCharRangeOver() {
        val cutMain = CutMain()
        val outputFilePath = "src/test/resources/output.txt"
        cutMain.doMain(arrayOf("-c", "-o", outputFilePath, "src/main/kotlin/input.txt", "6-"))

        val expectedOutput = "World new day have a great day!\n line for cut program polytechn\nWorld new day have a great day!"
        val outputFile = File(outputFilePath)
        assertTrue(outputFile.exists())
        assertEquals(expectedOutput, outputFile.readText().trim())
    }

    @Test
    fun testWordRangeOver() {
        val cutMain = CutMain()
        val outputFilePath = "src/test/resources/output.txt"
        cutMain.doMain(arrayOf("-w", "-o", outputFilePath, "src/main/kotlin/input.txt", "4-"))

        val expectedOutput = "day have a great day!\ncut program polytechn\nday have a great day!"
        val outputFile = File(outputFilePath)
        assertTrue(outputFile.exists())
        assertEquals(expectedOutput, outputFile.readText().trim())
    }
}
