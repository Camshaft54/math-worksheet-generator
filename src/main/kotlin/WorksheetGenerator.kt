package org.example

import java.io.BufferedReader
import java.io.File
import java.nio.file.Files

class WorksheetGenerator(
    private val template: File, private val config: WorksheetConfig, private val context: RationalContext
) {
    private fun lineTemplate(problem: String) = "\\problem \$${problem}\$ \\\\\\boxed{\\phantom{\\frac{100000000}{1}}} \\vspace{2.6cm} \\\\"

    fun makeWorksheet(outputFile: File) {
        val worksheetLines = BufferedReader(template.reader()).use {
            it.readLines().toMutableList()
        }

        val index = worksheetLines.indexOfFirst { line -> line == "% BODY" }
        if (index < 0) {
            throw IllegalArgumentException("Missing '% BODY' in worksheet template")
        }
        worksheetLines.removeAt(index)

        val problemList = mutableListOf<String>()
        repeat(config.convertToFrac) {
            problemList.add(lineTemplate(ConvertMath.convert(FDP.random(FDP.Fraction), FDP.Fraction, context)))
        }
        repeat(config.convertToDec) {
            problemList.add(lineTemplate(ConvertMath.convert(FDP.random(FDP.Decimal), FDP.Decimal, context)))
        }
        repeat(config.convertToPer) {
            problemList.add(lineTemplate(ConvertMath.convert(FDP.random(FDP.Percent), FDP.Percent, context)))
        }
        repeat(config.fracAdd) {
            problemList.add(lineTemplate(FractionMath.add(context)))
        }
        repeat(config.fracSub) {
            problemList.add(lineTemplate(FractionMath.sub(context)))
        }
        repeat(config.fracMul) {
            problemList.add(lineTemplate(FractionMath.mul(context)))
        }
        repeat(config.fracDiv) {
            problemList.add(lineTemplate(FractionMath.div(context)))
        }
        repeat(config.fracSimpf) {
            problemList.add(lineTemplate(FractionMath.simplify(context)))
        }
        repeat(config.decAdd) {
            problemList.add(lineTemplate(DecimalMath.add(context)))
        }
        repeat(config.decSub) {
            problemList.add(lineTemplate(DecimalMath.sub(context)))
        }
        repeat(config.decMul) {
            problemList.add(lineTemplate(DecimalMath.mul(context)))
        }
        repeat(config.decDiv) {
            problemList.add(lineTemplate(DecimalMath.div(context)))
        }
        repeat(config.perOf) {
            problemList.add(lineTemplate(PercentMath.percentOf(context)))
        }
        repeat(config.perIsWhatPerOf) {
            problemList.add(lineTemplate(PercentMath.xIsWhatPercentOfY(context)))
        }
        repeat(config.pemdas) {
            println(PemdasMath.pemdas())
            problemList.add(lineTemplate(PemdasMath.pemdas()))
        }
        problemList.shuffle()

        worksheetLines.addAll(index, problemList)

        Files.deleteIfExists(outputFile.toPath())
        outputFile.createNewFile()
        outputFile.bufferedWriter().use { out ->
            worksheetLines.forEach {
                out.write(it + "\n")
            }
        }
    }
}

data class WorksheetConfig(
    val randomizeOrder: Boolean,
    val convertToFrac: Int,
    val convertToDec: Int,
    val convertToPer: Int,
    val fracAdd: Int,
    val fracSub: Int,
    val fracMul: Int,
    val fracDiv: Int,
    val fracSimpf: Int,
    val decAdd: Int,
    val decSub: Int,
    val decMul: Int,
    val decDiv: Int,
    val perOf: Int,
    val perIsWhatPerOf: Int,
    val pemdas: Int
)