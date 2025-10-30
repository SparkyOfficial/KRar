package org.sparkystudio.krar

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun App() {
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                "KRar Archive Utility",
                style = MaterialTheme.typography.headlineMedium
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                "A modern cross-platform archive utility",
                style = MaterialTheme.typography.bodyLarge
            )
            
            Spacer(modifier = Modifier.height(32.dp))
            
            Button(
                onClick = { /* TODO: Implement functionality */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Create Archive")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = { /* TODO: Implement functionality */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Extract Archive")
            }
            
            Spacer(modifier = Modifier.height(8.dp))
            
            Button(
                onClick = { /* TODO: Implement functionality */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("List Archive Contents")
            }
        }
    }
}