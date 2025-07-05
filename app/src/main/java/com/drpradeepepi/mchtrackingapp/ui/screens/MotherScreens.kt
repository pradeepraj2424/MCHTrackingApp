// File: app/src/main/java/com/drpradeepepi/mchtrackingapp/ui/screens/MotherScreens.kt
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
import com.drpradeepepi.mchtrackingapp.viewmodel.MotherViewModel
import com.drpradeepepi.mchtrackingapp.ui.components.MotherItem
import com.drpradeepepi.mchtrackingapp.data.extensions.toMother
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MothersListScreen(
    navController: NavController,
    hscName: String,
    viewModel: MotherViewModel
) {
    val mothersList by viewModel.motherList.collectAsState()

    LaunchedEffect(hscName) {
        viewModel.loadMothersByHSC(hscName)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mothers - $hscName") },
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
                onClick = { navController.navigate("add_mother/$hscName") }
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Mother")
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
            items(mothersList) { motherEntity ->
                val mother = motherEntity.toMother()
                MotherItem(
                    mother = mother,
                    onClick = {
                        navController.navigate("mother_detail/${mother.name}")
                    },
                    onEdit = {
                        navController.navigate("edit_mother/$hscName/${mother.name}")
                    },
                    onDelete = { viewModel.deleteMother(motherEntity) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMotherScreen(
    navController: NavController,
    hscName: String,
    viewModel: MotherViewModel
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var lmp by remember { mutableStateOf("") }
    var edd by remember { mutableStateOf("") }
    var husbandName by remember { mutableStateOf("") }
    var gravida by remember { mutableStateOf("") }
    var para by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Mother") },
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
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Mother Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }

            item {
                OutlinedTextField(
                    value = husbandName,
                    onValueChange = { husbandName = it },
                    label = { Text("Husband Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = lmp,
                    onValueChange = { lmp = it },
                    label = { Text("LMP (Last Menstrual Period)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = edd,
                    onValueChange = { edd = it },
                    label = { Text("EDD (Expected Date of Delivery)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = gravida,
                    onValueChange = { gravida = it },
                    label = { Text("Gravida") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = para,
                    onValueChange = { para = it },
                    label = { Text("Para") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.addMother(
                                name = name,
                                age = age.toIntOrNull() ?: 0,
                                phone = phone,
                                address = address,
                                husbandName = husbandName,
                                lmp = lmp,
                                edd = edd,
                                gravida = gravida.toIntOrNull() ?: 0,
                                para = para.toIntOrNull() ?: 0,
                                hscName = hscName
                            )
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = name.isNotBlank() && age.isNotBlank()
                ) {
                    Text("Save Mother")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMotherScreen(
    navController: NavController,
    hscName: String,
    motherName: String,
    viewModel: MotherViewModel
) {
    var name by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var lmp by remember { mutableStateOf("") }
    var edd by remember { mutableStateOf("") }
    var husbandName by remember { mutableStateOf("") }
    var gravida by remember { mutableStateOf("") }
    var para by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    LaunchedEffect(motherName) {
        viewModel.loadMotherByName(motherName)
    }

    val selectedMother by viewModel.selectedMother.collectAsState()

    LaunchedEffect(selectedMother) {
        selectedMother?.let { mother ->
            name = mother.name
            age = mother.age.toString()
            phone = mother.phone
            address = mother.address
            husbandName = mother.husbandName
            lmp = mother.lmp
            edd = mother.edd
            gravida = mother.gravida.toString()
            para = mother.para.toString()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Mother") },
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
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Mother Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = age,
                    onValueChange = { age = it },
                    label = { Text("Age") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    label = { Text("Phone Number") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3
                )
            }

            item {
                OutlinedTextField(
                    value = husbandName,
                    onValueChange = { husbandName = it },
                    label = { Text("Husband Name") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = lmp,
                    onValueChange = { lmp = it },
                    label = { Text("LMP (Last Menstrual Period)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = edd,
                    onValueChange = { edd = it },
                    label = { Text("EDD (Expected Date of Delivery)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = gravida,
                    onValueChange = { gravida = it },
                    label = { Text("Gravida") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                OutlinedTextField(
                    value = para,
                    onValueChange = { para = it },
                    label = { Text("Para") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item {
                Button(
                    onClick = {
                        scope.launch {
                            viewModel.updateMother(
                                originalName = motherName,
                                name = name,
                                age = age.toIntOrNull() ?: 0,
                                phone = phone,
                                address = address,
                                husbandName = husbandName,
                                lmp = lmp,
                                edd = edd,
                                gravida = gravida.toIntOrNull() ?: 0,
                                para = para.toIntOrNull() ?: 0,
                                hscName = hscName
                            )
                            navController.popBackStack()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    enabled = name.isNotBlank() && age.isNotBlank()
                ) {
                    Text("Update Mother")
                }
            }
        }
    }
}