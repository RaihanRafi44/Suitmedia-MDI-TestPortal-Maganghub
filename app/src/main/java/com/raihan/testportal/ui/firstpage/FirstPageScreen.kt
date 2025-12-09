package com.raihan.testportal.ui.firstpage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.raihan.testportal.R

@Composable
fun FirstPageScreen() {

    val context = LocalContext.current

    var name by remember { mutableStateOf("") }
    var palindromeText by remember { mutableStateOf("") }

    var showDialog by remember { mutableStateOf(false) }
    var dialogTitle by remember { mutableStateOf("") }
    var dialogMessage by remember { mutableStateOf("") }

    fun triggerDialog(title: String, message: String) {
        dialogTitle = title
        dialogMessage = message
        showDialog = true
    }

    fun isPalindrome(text: String): Boolean {
        val cleanedText = text.replace(Regex("[^A-Za-z]"), "").lowercase()
        return cleanedText == cleanedText.reversed()
    }

    val scrollState = rememberScrollState()

    // Box digunakan untuk menumpuk Background Image di belakang konten
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        // Background Image
        Image(
            painter = painterResource(id = R.drawable.bg_first_screen),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Konten Utama
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState) // Mengaktifkan scroll
                .padding(horizontal = 30.dp), // Margin horizontal global (30dp)
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(100.dp))

            // User Photo
            Image(
                painter = painterResource(id = R.drawable.ic_photo),
                contentDescription = "User Photo",
                modifier = Modifier.size(140.dp)
            )

            Spacer(modifier = Modifier.height(70.dp))

            // Input Name
            CustomTextField(
                value = name,
                onValueChange = { name = it },
                hint = stringResource(id = R.string.text_name)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Input Palindrome
            CustomTextField(
                value = palindromeText,
                onValueChange = { palindromeText = it },
                hint = stringResource(id = R.string.text_palindrome)
            )

            Spacer(modifier = Modifier.height(60.dp))

            // Button Check
            CustomButton(
                text = stringResource(id = R.string.text_check),
                onClick = {
                    if (palindromeText.isEmpty()) {
                        triggerDialog("Warning", "Please input text")
                    } else {
                        if (isPalindrome(palindromeText)) {
                            triggerDialog("Palindrome", "isPalindrome")
                        } else {
                            triggerDialog("Not Palindrome", "Not Palindrome")
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Button Next
            CustomButton(
                text = stringResource(id = R.string.text_next),
                onClick = { }
            )

            Spacer(modifier = Modifier.height(50.dp))
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = dialogTitle) },
                text = { Text(text = dialogMessage) },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text(text = "OK")
                    }
                }
            )
        }
    }
}

// Komponen Reusable untuk TextField agar kode lebih rapi
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(text = hint, color = Color.Gray) },
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedBorderColor = Color.Gray,
            unfocusedBorderColor = Color.White
        ),
        modifier = Modifier.fillMaxWidth()
    )
}

// Komponen Reusable untuk Button
@Composable
fun CustomButton(
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        // .height(50.dp), // Bisa diaktifkan jika ingin tinggi fix
        shape = RoundedCornerShape(16.dp), // app:cornerRadius="16dp"
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2B637B) // Sesuaikan dengan warna @drawable/btn_background
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White,
            modifier = Modifier.padding(vertical = 8.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun FirstPageScreenPreview() {
    FirstPageScreen()
}