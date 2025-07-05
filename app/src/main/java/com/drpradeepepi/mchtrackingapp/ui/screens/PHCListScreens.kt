package com.drpradeepepi.mchtrackingapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.drpradeepepi.mchtrackingapp.viewmodel.PHCViewModel
import com.drpradeepepi.mchtrackingapp.data.extensions.toPHC
import com.drpradeepepi.mchtrackingapp.ui.components.PHCItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PHCListScreen(
    navController: NavController,
    viewModel: PHCViewModel
) {
    val phcList by viewModel.phcList.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("PHC List") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_phc") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add PHC")
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(phcList) { phcEntity ->
                val phc = phcEntity.toPHC()
                PHCItem(
                    phc = phc,
                    onClick = {
                        navController.navigate("hsc_list/${phc.name}")
                    },
                    onEdit = {
                        navController.navigate("edit_phc/${phc.name}")
                    },
                    onDelete = { viewModel.deletePHC(phcEntity) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPHCScreen(
    navController: NavController,
    viewModel: PHCViewModel
) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var doctorName by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add PHC") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("PHC Name") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = location,
                onValueChange = { location = it },
                label = { Text("Location") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = contactNumber,
                onValueChange = { contactNumber = it },
                label = { Text("Contact Number") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = doctorName,
                onValueChange = { doctorName = it },
                label = { Text("Doctor Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    scope.launch {
                        viewModel.addPHC(
                            name = name,
                            location = location,
                            contactNumber = contactNumber,
                            doctorName = doctorName
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && location.isNotBlank()
            ) {
                Text("Save PHC")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPHCScreen(
    navController: NavController,
    phcName: String,
    viewModel: PHCViewModel
) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var doctorName by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(true) }

    val scope = rememberCoroutineScope()

    LaunchedEffect(phcName) {
        viewModel.loadPHCByName(phcName)
    }

    val selectedPHC by viewModel.selectedPHC.collectAsState()

    LaunchedEffect(selectedPHC) {
        selectedPHC?.let { phc ->
            name = phc.name
            location = phc.location
            contactNumber = phc.contactNumber
            doctorName = phc.doctorName
            isLoading = false
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit PHC") },
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
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = androidx.compose.ui.Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("PHC Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Location") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = contactNumber,
                    onValueChange = { contactNumber = it },
                    label = { Text("Contact Number") },
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = doctorName,
                    onValueChange = { doctorName = it },
                    label = { Text("Doctor Name") },
                    modifier = Modifier.fillMaxWidth()
                )

                Button(
                    onClick = {
                        scope.launch {
                            viewModel.updatePHC(
                                originalName = phcName,
                                name = name,
                                location = location,
                                contactNumber = contactNumber,
                                doctorName = doctorName
                            )
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = name.isNotBlank() && location.isNotBlank()
                ) {
                    Text("Update PHC")
                }
            }
        }
    }
}