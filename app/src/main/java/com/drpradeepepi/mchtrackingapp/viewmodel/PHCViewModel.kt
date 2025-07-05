package com.drpradeepepi.mchtrackingapp.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drpradeepepi.mchtrackingapp.data.entity.PHCEntity
import com.drpradeepepi.mchtrackingapp.data.repository.PHCRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.apache.poi.ss.usermodel.*
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.io.InputStream

class PHCViewModel(private val repository: PHCRepository) : ViewModel() {
    private val _phcList = MutableStateFlow<List<PHCEntity>>(emptyList())
    val phcList: StateFlow<List<PHCEntity>> = _phcList.asStateFlow()

    // Add selectedPHC state for edit functionality
    private val _selectedPHC = MutableStateFlow<PHCEntity?>(null)
    val selectedPHC: StateFlow<PHCEntity?> = _selectedPHC.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getAllPHCs().collect {
                _phcList.value = it
            }
        }
    }

    // Method to add PHC with individual parameters (used in AddPHCScreen)
    fun addPHC(name: String, location: String, contactNumber: String, doctorName: String) {
        viewModelScope.launch {
            val phcEntity = PHCEntity(
                name = name,
                location = location,
                contactNumber = contactNumber,
                doctorName = doctorName
            )
            repository.insertPHC(phcEntity)
        }
    }


    // Method to update PHC with individual parameters (used in EditPHCScreen)
    fun updatePHC(originalName: String, name: String, location: String, contactNumber: String, doctorName: String) {
        viewModelScope.launch {
            // Find the existing PHC entity by original name
            val existingPHC = _phcList.value.find { it.name == originalName }
            existingPHC?.let {
                val updatedPHC = it.copy(
                    name = name,
                    location = location,
                    contactNumber = contactNumber,
                    doctorName = doctorName
                )
                repository.updatePHC(updatedPHC)
            }
        }
    }

    // Method to load PHC by name (used in EditPHCScreen)
    fun loadPHCByName(phcName: String) {
        viewModelScope.launch {
            val phc = _phcList.value.find { it.name == phcName }
            _selectedPHC.value = phc
        }
    }

    // Original methods for direct entity manipulation
    fun insertPHC(phc: PHCEntity) {
        viewModelScope.launch {
            repository.insertPHC(phc)
        }
    }

    fun updatePHC(phc: PHCEntity) {
        viewModelScope.launch {
            repository.updatePHC(phc)
        }
    }

    fun deletePHC(phc: PHCEntity) {
        viewModelScope.launch {
            repository.deletePHC(phc)
        }
    }

    // Excel import method - corrected as a regular method, not extension function
    suspend fun importPHCFromExcel(context: Context, uri: Uri): String {
        return withContext(Dispatchers.IO) {
            var inputStream: InputStream? = null
            var workbook: Workbook? = null

            try {
                inputStream = context.contentResolver.openInputStream(uri)
                if (inputStream == null) {
                    return@withContext "Error: Could not open file"
                }

                // Create workbook based on file type
                workbook = try {
                    XSSFWorkbook(inputStream) // For .xlsx files
                } catch (e: Exception) {
                    inputStream.close()
                    inputStream = context.contentResolver.openInputStream(uri)
                    HSSFWorkbook(inputStream) // For .xls files
                }

                val sheet = workbook.getSheetAt(0)

                var successCount = 0
                var errorCount = 0
                val errors = mutableListOf<String>()

                // Skip header row (row 0)
                for (i in 1..sheet.lastRowNum) {
                    val row = sheet.getRow(i) ?: continue

                    try {
                        val name = getCellValueAsString(row.getCell(0))
                        val location = getCellValueAsString(row.getCell(1))
                        val contactNumber = getCellValueAsString(row.getCell(2))
                        val doctorName = getCellValueAsString(row.getCell(3))

                        if (name.isNotBlank()) {
                            addPHC(
                                name = name,
                                location = location,
                                contactNumber = contactNumber,
                                doctorName = doctorName
                            )
                            successCount++
                        } else {
                            errorCount++
                            errors.add("Row ${i + 1}: PHC name is required")
                        }
                    } catch (e: Exception) {
                        errorCount++
                        errors.add("Row ${i + 1}: ${e.message}")
                    }
                }

                when {
                    successCount > 0 && errorCount == 0 ->
                        "Successfully imported $successCount PHC records!"
                    successCount > 0 && errorCount > 0 ->
                        "Imported $successCount PHC records with $errorCount errors"
                    else ->
                        "Import failed: ${errors.joinToString(", ")}"
                }

            } catch (e: Exception) {
                "Error reading Excel file: ${e.message}"
            } finally {
                try {
                    workbook?.close()
                    inputStream?.close()
                } catch (e: Exception) {
                    // Ignore close errors
                }
            }
        }
    }

    private fun getCellValueAsString(cell: Cell?): String {
        return when (cell?.cellType) {
            CellType.STRING -> cell.stringCellValue.trim()
            CellType.NUMERIC -> {
                if (DateUtil.isCellDateFormatted(cell)) {
                    cell.dateCellValue.toString()
                } else {
                    // Handle both integers and decimals
                    val numericValue = cell.numericCellValue
                    if (numericValue == numericValue.toLong().toDouble()) {
                        numericValue.toLong().toString()
                    } else {
                        numericValue.toString()
                    }
                }
            }
            CellType.BOOLEAN -> cell.booleanCellValue.toString()
            CellType.FORMULA -> {
                when (cell.cachedFormulaResultType) {
                    CellType.STRING -> cell.stringCellValue.trim()
                    CellType.NUMERIC -> {
                        val numericValue = cell.numericCellValue
                        if (numericValue == numericValue.toLong().toDouble()) {
                            numericValue.toLong().toString()
                        } else {
                            numericValue.toString()
                        }
                    }
                    else -> ""
                }
            }
            else -> ""
        }
    }
}