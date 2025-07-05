// File: app/src/main/java/com/drpradeepepi/mchtrackingapp/ui/screens/HSCScreens.kt
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
import com.drpradeepepi.mchtrackingapp.viewmodel.HSCViewModel
import com.drpradeepepi.mchtrackingapp.data.extensions.toHSC
import com.drpradeepepi.mchtrackingapp.ui.components.HSCItem
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HSCListScreen(
    navController: NavController,
    phcName: String,
    viewModel: HSCViewModel
) {
    val hscList by viewModel.hscList.collectAsState()

    LaunchedEffect(phcName) {
        viewModel.loadHSCsByPHC(phcName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HSC List(${hscList.size})- $phcName") },
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
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate("add_hsc/$phcName") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add HSC")
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
            items(hscList) { hscEntity ->
                val hsc = hscEntity.toHSC()
                HSCItem(
                    hsc = hsc,
                    onClick = {
                        navController.navigate("mothers_list/${hsc.name}")
                    },
                    onEdit = {
                        navController.navigate("edit_hsc/$phcName/${hsc.name}")
                    },
                    onDelete = { viewModel.deleteHSC(hscEntity) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddHSCScreen(
    navController: NavController,
    phcName: String,
    viewModel: HSCViewModel
) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var anmName by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add HSC") },
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
                label = { Text("HSC Name") },
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
                value = anmName,
                onValueChange = { anmName = it },
                label = { Text("ANM Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    scope.launch {
                        viewModel.addHSC(
                            name = name,
                            location = location,
                            contactNumber = contactNumber,
                            anmName = anmName,
                            phcName = phcName
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() //&& location.isNotBlank()
            ) {
                Text("Save HSC")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditHSCScreen(
    navController: NavController,
    phcName: String,
    hscName: String,
    viewModel: HSCViewModel
) {
    var name by remember { mutableStateOf("") }
    var location by remember { mutableStateOf("") }
    var contactNumber by remember { mutableStateOf("") }
    var anmName by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    LaunchedEffect(hscName) {
        viewModel.loadHSCByName(hscName)
    }

    val selectedHSC by viewModel.selectedHSC.collectAsState()

    LaunchedEffect(selectedHSC) {
        selectedHSC?.let { hsc ->
            name = hsc.name
            location = hsc.location
            contactNumber = hsc.contactNumber
            anmName = hsc.anmName
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit HSC") },
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
                label = { Text("HSC Name") },
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
                value = anmName,
                onValueChange = { anmName = it },
                label = { Text("ANM Name") },
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    scope.launch {
                        viewModel.updateHSC(
                            originalName = hscName,
                            name = name,
                            location = location,
                            contactNumber = contactNumber,
                            anmName = anmName,
                            phcName = phcName
                        )
                        navController.popBackStack()
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = name.isNotBlank() && location.isNotBlank()
            ) {
                Text("Update HSC")
            }
        }
    }
}