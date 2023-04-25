import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.kohsuke.args4j.CmdLineException
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertFailsWith

class CutMainTest {

    @Test
    fun testCharacterSubstring(){
        val cutMain = CutMain()
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        cutMain.doMain(arrayOf("-c", "src\\main\\kotlin\\input.txt", "3"))
        val expectedOutput = "l"
        assertEquals(expectedOutput, outputStream.toString())
    }

    @Test
    fun testWordSubstring(){
        val cutMain = CutMain()
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        cutMain.doMain(arrayOf("-w", "src\\main\\kotlin\\input.txt", "4"))
        val expectedOutput = "day"
        assertEquals(expectedOutput, outputStream.toString())
    }

    @Test
    fun testCharRange(){
        val cutMain = CutMain()
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        cutMain.doMain(arrayOf("-c", "src\\main\\kotlin\\input.txt", "1-4"))
        val expectedOutput = "ell"
        assertEquals(expectedOutput, outputStream.toString())
    }
    @Test
    fun testWordRange(){
        val cutMain = CutMain()
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        cutMain.doMain(arrayOf("-w", "src\\main\\kotlin\\input.txt", "1-4"))
        val expectedOutput = "Hello World new day"
        assertEquals(expectedOutput, outputStream.toString())
    }

    @Test
    fun testCharRangeOver(){
        val cutMain = CutMain()
        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))
        cutMain.doMain(arrayOf("-c", "src\\main\\kotlin\\input.txt", "6-"))
        val expectedOutput = "World new day have a great day!"
        assertEquals(expectedOutput, outputStream.toString())
    }
}