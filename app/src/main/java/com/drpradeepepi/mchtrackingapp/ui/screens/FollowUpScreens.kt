// File: app/src/main/java/com/drpradeepepi/mchtrackingapp/ui/screens/FollowUpScreens.kt
package com.drpradeepepi.mchtrackingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.drpradeepepi.mchtrackingapp.viewmodel.FollowUpViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddFollowUpScreen(
    navController: NavController,
    motherName: String,
    viewModel: FollowUpViewModel
) {
    var date by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var bloodPressure by remember { mutableStateOf("") }
    var complaints by remember { mutableStateOf("") }
    var examination by remember { mutableStateOf("") }
    var advice by remember { mutableStateOf("") }
    var nextVisit by remember { mutableStateOf("") }
    var hemoglobin by remember { mutableStateOf("") }
    var gestationalAge by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Follow-up") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = gestationalAge,
                    onValueChange = { gestationalAge = it },
                    label = { Text("Gestational Age (weeks)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = bloodPressure,
                    onValueChange = { bloodPressure = it },
                    label = { Text("Blood Pressure") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = hemoglobin,
                    onValueChange = { hemoglobin = it },
                    label = { Text("Hemoglobin (g/dl)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = complaints,
                    onValueChange = { complaints = it },
                    label = { Text("Complaints") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }

            item {
                OutlinedTextField(
                    value = examination,
                    onValueChange = { examination = it },
                    label = { Text("Examination") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }

            item {
                OutlinedTextField(
                    value = advice,
                    onValueChange = { advice = it },
                    label = { Text("Advice") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }

            item {
                OutlinedTextField(
                    value = nextVisit,
                    onValueChange = { nextVisit = it },
                    label = { Text("Next Visit") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.addFollowUp(
                                motherName = motherName,
                                date = date,
                                gestationalAge = gestationalAge.toIntOrNull() ?: 0,
                                weight = weight,
                                bloodPressure = bloodPressure,
                                hemoglobin = hemoglobin,
                                complaints = complaints,
                                examination = examination,
                                advice = advice,
                                nextVisit = nextVisit
                            )
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = date.isNotBlank()
                ) {
                    Text("Save Follow-up")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditFollowUpScreen(
    navController: NavController,
    motherName: String,
    followUpId: Int,
    viewModel: FollowUpViewModel
) {
    var date by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var bloodPressure by remember { mutableStateOf("") }
    var complaints by remember { mutableStateOf("") }
    var examination by remember { mutableStateOf("") }
    var advice by remember { mutableStateOf("") }
    var nextVisit by remember { mutableStateOf("") }
    var hemoglobin by remember { mutableStateOf("") }
    var gestationalAge by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    LaunchedEffect(followUpId) {
        viewModel.loadFollowUpById(followUpId)
    }

    val selectedFollowUp by viewModel.selectedFollowUp.collectAsState()

    LaunchedEffect(selectedFollowUp) {
        selectedFollowUp?.let { followUp ->
            date = followUp.date
            gestationalAge = followUp.gestationalAge.toString()
            weight = followUp.weight
            bloodPressure = followUp.bloodPressure
            hemoglobin = followUp.hemoglobin
            complaints = followUp.complaints
            examination = followUp.examination
            advice = followUp.advice
            nextVisit = followUp.nextVisit
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Follow-up") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Date") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = gestationalAge,
                    onValueChange = { gestationalAge = it },
                    label = { Text("Gestational Age (weeks)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = weight,
                    onValueChange = { weight = it },
                    label = { Text("Weight (kg)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = bloodPressure,
                    onValueChange = { bloodPressure = it },
                    label = { Text("Blood Pressure") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = hemoglobin,
                    onValueChange = { hemoglobin = it },
                    label = { Text("Hemoglobin (g/dl)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = complaints,
                    onValueChange = { complaints = it },
                    label = { Text("Complaints") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }

            item {
                OutlinedTextField(
                    value = examination,
                    onValueChange = { examination = it },
                    label = { Text("Examination") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }

            item {
                OutlinedTextField(
                    value = advice,
                    onValueChange = { advice = it },
                    label = { Text("Advice") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }

            item {
                OutlinedTextField(
                    value = nextVisit,
                    onValueChange = { nextVisit = it },
                    label = { Text("Next Visit") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.updateFollowUp(
                                id = followUpId,
                                motherName = motherName,
                                date = date,
                                gestationalAge = gestationalAge.toIntOrNull() ?: 0,
                                weight = weight,
                                bloodPressure = bloodPressure,
                                hemoglobin = hemoglobin,
                                complaints = complaints,
                                examination = examination,
                                advice = advice,
                                nextVisit = nextVisit
                            )
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = date.isNotBlank()
                ) {
                    Text("Update Follow-up")
                }
            }
        }
    }
}