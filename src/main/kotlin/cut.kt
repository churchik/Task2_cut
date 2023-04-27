import org.kohsuke.args4j.Argument
import org.kohsuke.args4j.CmdLineException
import org.kohsuke.args4j.CmdLineParser
import org.kohsuke.args4j.Option
import java.io.BufferedReader
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class CutMain {
    @Option(name = "-c", usage = "specify character-based range")
    private var characterBased: Boolean = false

    @Option(name = "-w", usage = "specify word-based range")
    private var wordBased: Boolean = false

    @Option(name = "-o", usage = "output to this file", metaVar = "OUTPUT")
    private var outputFile: String? = null

    @Argument(metaVar = "FILE", usage = "input file", index = 0)
    private var inputFile: String? = null

    @Argument(metaVar = "RANGE", usage = "range of characters or words", index = 1)
    private var range: String? = null

    @Throws(IOException::class)
    fun doMain(args: Array<String>) {
        val parser = CmdLineParser(this)
        try {
            parser.parseArgument(*args)

            if (inputFile.isNullOrEmpty()) {
                throw CmdLineException(parser, "No input file specified")
            }

            if (range.isNullOrEmpty()) {
                throw CmdLineException(parser, "No range specified")
            }

            if (wordBased && characterBased){
                throw CmdLineException(parser, "Can't be both args")
            }



        } catch (e: CmdLineException) {
            System.err.println(e.message)
            System.err.println("Usage: cut [-c|-w] [-o ofile] [file] range")
            parser.printUsage(System.err)
            return
        }

        val reader = inputFile?.let { FileReader(it) }?.let { BufferedReader(it) }
        var line: String? = reader?.readLine()
        val rangeArray = line?.let { parseRange(range!!, it.length) }

        val outputWriter = outputFile?.let { FileWriter(it, false) }
        while (line != null) {
            val cutLine = rangeArray?.let { cutLine(line!!, it) }
            if (outputFile.isNullOrEmpty()) {
                print(cutLine)
                if (reader?.ready() == true) { // проверяем, является ли строка последней в файле
                    print("\n") // если нет, добавляем символ новой строки
                }
            } else {
                outputWriter?.write(cutLine)
                if (reader?.ready() == true) {
                    outputWriter?.write("\n")
                }
            }
            line = reader?.readLine()
        }
        outputWriter?.close()
        reader?.close()
    }


    private fun parseRange(range: String, lineLength: Int): IntArray {
        val rangeArray = IntArray(2)
        val parts = range.split("-")
        if (parts.size == 1) {
            val start = parts[0].toInt()
            rangeArray[0] = if (start >= 0) start else lineLength + start
            rangeArray[1] = rangeArray[0]
        } else if (parts.size == 2) {
            rangeArray[0] = if (parts[0].isEmpty()) 0 else {
                val start = parts[0].toInt()
                if (start >= 0) start else lineLength + start
            }
            rangeArray[1] = if (parts[1].isEmpty()) lineLength else {
                val end = parts[1].toInt()
                if (end >= 0) end else lineLength + end
            }
        }
        return rangeArray
    }

    private fun cutLine(line: String, range: IntArray): String {
        val start =
            if (characterBased && range[0] == range[1])
                range[0] - 1
            else if (characterBased && range[0] != range[1])
                range[0]
            else
                wordToCharacterIndex(
                    line,
                    range[0] - 1
                )
        val end =
            if (characterBased)
                range[1]
            else
                wordToCharacterIndex(line, range[1])
        var startIndex = start
        var endIndex = end
        if (!characterBased) {
            while (startIndex < end && line[startIndex].isWhitespace()) {
                startIndex++
            }
            while (endIndex > start && line[endIndex - 1].isWhitespace()) {
                endIndex--
            }
        }
        return line.substring(startIndex, endIndex)
    }

    private fun wordToCharacterIndex(line: String, wordIndex: Int): Int {
        var index = 0
        var wordCount = 0
        while (index < line.length && wordCount < wordIndex) {
            if (line[index].isWhitespace()) {
                wordCount++
            }
            index++
        }
        return index
    }
}
