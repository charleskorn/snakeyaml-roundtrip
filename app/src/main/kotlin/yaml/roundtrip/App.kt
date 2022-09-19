package yaml.roundtrip

import org.snakeyaml.engine.v2.api.DumpSettings
import org.snakeyaml.engine.v2.api.LoadSettings
import org.snakeyaml.engine.v2.api.YamlOutputStreamWriter
import org.snakeyaml.engine.v2.emitter.Emitter
import org.snakeyaml.engine.v2.parser.ParserImpl
import org.snakeyaml.engine.v2.scanner.StreamReader
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths

fun main() {
    val inputPath = Paths.get("..", "input.yaml").toAbsolutePath()
    val inputText = Files.readString(inputPath)

    val loadSettings = LoadSettings.builder()
        .setParseComments(true)
        .build()

    val streamReader = StreamReader(loadSettings, inputText)
    val parser = ParserImpl(loadSettings, streamReader)

    val dumpSettings = DumpSettings.builder()
        .setDumpComments(true)
        .build()

    val output = ByteArrayOutputStream()
    val writer = object : YamlOutputStreamWriter(output, Charsets.UTF_8) {
        override fun processIOException(e: IOException?) {
            if (e != null) {
                throw e
            }
        }
    }

    val emitter = Emitter(dumpSettings, writer)

    parser.forEach {
        emitter.emit(it)
    }

    val outputText = output.toString()
    println(outputText)

    val outputPath = Paths.get("..", "output.yaml").toAbsolutePath()
    Files.writeString(outputPath, outputText, Charsets.UTF_8)
}
