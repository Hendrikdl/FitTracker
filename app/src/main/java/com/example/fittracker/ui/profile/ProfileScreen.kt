package com.example.fittracker.ui.profile

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.fittracker.data.local.DatabaseProvider
import com.example.fittracker.data.local.entity.ProfileEntity
import com.example.fittracker.data.remote.FirestoreProfileService
import com.example.fittracker.data.repository.ProfileRepository

@Composable
fun ProfileScreen() {

    val context = LocalContext.current

    val db = DatabaseProvider.getDatabase(context)
    val firestore = remember { FirestoreProfileService() }
    val repository = remember {
        ProfileRepository(
            db.profileDao(),
            db.weightLogDao(),
            firestore
        )
    }

    val viewModel: ProfileViewModel = viewModel(
        factory = ProfileViewModelFactory(repository)
    )

    val state = viewModel.uiState.collectAsStateWithLifecycle()
    val profile = state.value.user

    // ================= STATE =================
    var isEditing by remember { mutableStateOf(false) }

    var photoUri by remember { mutableStateOf("") }

    var name by remember { mutableStateOf("") }
    var surname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var sex by remember { mutableStateOf("") }

    var weight by remember { mutableStateOf("") }
    var TargetWeight by remember {mutableStateOf("")}
    var height by remember { mutableStateOf("") }

    var chest by remember { mutableStateOf("") }
    var waist by remember { mutableStateOf("") }
    var hips by remember { mutableStateOf("") }
    var biceps by remember { mutableStateOf("") }
    var thighs by remember { mutableStateOf("") }

    // ================= LOAD DATA =================
    LaunchedEffect(profile?.id) {
        profile?.let {
            photoUri = it.photoUri
            name = it.name
            surname = it.surname
            age = it.age.toString()
            sex = it.sex
            weight = it.weightKg.toString()
            height = it.heightCm.toString()
            chest = it.chestCm.toString()
            waist = it.waistCm.toString()
            hips = it.hipsCm.toString()
            biceps = it.bicepsCm.toString()
            thighs = it.thighsCm.toString()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.syncProfile()
    }

    LaunchedEffect(profile) {
        Log.d("ProfileScreen", "Current profile = $profile")
    }

    // ================= IMAGE PICKER =================
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.OpenDocument()
    ) { uri: Uri? ->

        uri?.let {
            context.contentResolver.takePersistableUriPermission(
                it,
                Intent.FLAG_GRANT_READ_URI_PERMISSION
            )
            photoUri = it.toString()
        }
    }

    // ================= UI =================
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {

        // HEADER
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(240.dp)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary)
            )

            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                AsyncImage(
                    model = photoUri.ifEmpty { null },
                    contentDescription = null,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "$name $surname",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Text(
                    text = "Fitness Profile",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // EDIT + PHOTO BUTTONS
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {

            OutlinedButton(
                onClick = { isEditing = !isEditing }
            ) {
                Text(if (isEditing) "Cancel" else "Edit Profile")
            }

            OutlinedButton(
                onClick = { photoPickerLauncher.launch(arrayOf("image/*")) }
            ) {
                Text("Change Photo")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // ================= CARDS =================
        ProfileCard("Personal Information") {
            ProfileField("Name", name, isEditing) { name = it }
            ProfileField("Surname", surname, isEditing) { surname = it }
            ProfileField("Age", age, isEditing) { age = it }
            ProfileField("Sex", sex, isEditing) { sex = it }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileCard("Body Stats") {
            ProfileField("Weight (kg)", weight, isEditing) { weight = it }
            ProfileField("Height (cm)", height, isEditing) { height = it }
        }

        Spacer(modifier = Modifier.height(16.dp))

        ProfileCard("Measurements") {
            ProfileField("Chest (cm)", chest, isEditing) { chest = it }
            ProfileField("Waist (cm)", waist, isEditing) { waist = it }
            ProfileField("Hips (cm)", hips, isEditing) { hips = it }
            ProfileField("Biceps (cm)", biceps, isEditing) { biceps = it }
            ProfileField("Thighs (cm)", thighs, isEditing) { thighs = it }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // SAVE
        Button(
            onClick = {

                Log.d("SaveButton", "Button pressed")

                val updated = ProfileEntity(
                    id = 1,
                    photoUri = photoUri,

                    name = name,
                    surname = surname,
                    email = profile?.email ?: "",

                    age = age.toIntOrNull() ?: 0,
                    sex = sex,

                    weightKg = weight.toDoubleOrNull() ?: 0.0,
                    heightCm = height.toDoubleOrNull() ?: 0.0,

                    chestCm = chest.toDoubleOrNull() ?: 0.0,
                    waistCm = waist.toDoubleOrNull() ?: 0.0,
                    hipsCm = hips.toDoubleOrNull() ?: 0.0,
                    bicepsCm = biceps.toDoubleOrNull() ?: 0.0,
                    thighsCm = thighs.toDoubleOrNull() ?: 0.0
                )

                Log.d("SaveButton", "Saving profile: $updated")

                viewModel.saveProfile(updated)

                isEditing = false
            },
            enabled = isEditing,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Text("Save Profile")
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

// ================= COMPONENTS =================

@Composable
fun ProfileCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(12.dp))

            content()
        }
    }
}

@Composable
fun ProfileField(
    label: String,
    value: String,
    enabled: Boolean,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    )
}