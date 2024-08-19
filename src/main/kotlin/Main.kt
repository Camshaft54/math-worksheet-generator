package org.example

import java.io.File
import java.math.BigDecimal
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

fun main() {
    val rationalContext = RationalContext(BigDecimal.ZERO, BigDecimal(100), 1000, 100, decimalPlaces = 2)
    val templateFile = File("templates/worksheet_template.tex")
    val worksheetConfig = WorksheetConfig(
        randomizeOrder = true,
        convertToFrac = 0,
        convertToDec = 0,
        convertToPer = 2,
        fracAdd = 0,
        fracSub = 0,
        fracMul = 1,
        fracDiv = 2,
        fracSimpf = 0,
        decAdd = 0,
        decSub = 0,
        decMul = 1,
        decDiv = 2,
        perOf = 1,
        perIsWhatPerOf = 1,
        pemdas = 3
    )

    val worksheetGen = WorksheetGenerator(templateFile, worksheetConfig, rationalContext)

    if (!Files.exists(Paths.get("out")))
        Files.createDirectory(Paths.get("out"))

    val outputFile = File("out/worksheet.tex")
    worksheetGen.makeWorksheet(outputFile)

    val outputDir = "out"
    generatePDF(outputFile, outputDir, deleteInput = false)
}

fun generatePDF(texFile: File, outputDir: String, deleteInput: Boolean = true) {
    val absoluteOutputDir = "${System.getProperty("user.dir")}\\${outputDir}"
    val pb = ProcessBuilder(
        "C:\\texlive\\2024\\bin\\windows\\pdflatex.exe",
        "-file-line-error",
        "-interaction=nonstopmode",
        "-synctex=1",
        "-output-format=pdf",
        "-output-directory=$absoluteOutputDir",
        "$absoluteOutputDir/${texFile.name}"
    ).directory(File(System.getProperty("user.dir")))
    println(pb.command().joinToString(" "))
    pb.start().waitFor(60, TimeUnit.SECONDS)
    Files.deleteIfExists(Paths.get("$absoluteOutputDir\\worksheet.aux"))
    Files.deleteIfExists(Paths.get("$absoluteOutputDir\\worksheet.synctex.gz"))
    Files.deleteIfExists(Paths.get("$absoluteOutputDir\\worksheet.log"))
    Files.deleteIfExists(Paths.get("$absoluteOutputDir\\texput.log"))
    if (deleteInput) {
        Files.deleteIfExists(texFile.toPath())
    }
}
