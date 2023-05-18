package com.gumu.bookwormapp.presentation.ui.userstats

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gumu.bookwormapp.R
import com.gumu.bookwormapp.presentation.component.NavigateBackTopAppBar
import com.gumu.bookwormapp.presentation.component.PillShapedText
import com.gumu.bookwormapp.presentation.theme.BookwormAppTheme
import com.gumu.bookwormapp.presentation.theme.primary
import com.gumu.bookwormapp.presentation.theme.secondary
import com.gumu.bookwormapp.presentation.theme.tertiary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserStatsScreen() {
    Scaffold(
        topBar = { NavigateBackTopAppBar(
            title = { Text(text = "My Stats") },
            onBackClick = { /*TODO*/ }
        ) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            ProfileSection()
            Spacer(modifier = Modifier.height(64.dp))
            StatsSummarySection()
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(text = "Sign out")
            }
        }
    }
}

@Composable
fun ProfileSection() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val borderWidth = 4.dp
        val secondaryBorderWidth = 16.dp

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(150.dp)
                .border(
                    BorderStroke(
                        secondaryBorderWidth,
                        MaterialTheme.colorScheme.primary.copy(alpha = 0.5f)
                    ),
                    CircleShape
                )
                .padding(secondaryBorderWidth)
        ) {
            Image(
                painter = painterResource(id = R.drawable.bookworm),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(150.dp)
                    .border(
                        BorderStroke(
                            borderWidth,
                            Brush.sweepGradient(listOf(primary, secondary, tertiary, primary))
                        ),
                        CircleShape
                    )
                    .padding(borderWidth)
                    .clip(CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "John Doe",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "some@mail.com",
            color = MaterialTheme.colorScheme.outline
        )
    }
}

@Composable
fun StatsSummarySection() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        PillShapedText(
            text = "Reading: 5 books",
            leadingIcon = { Icon(imageVector = Icons.Default.MenuBook, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        PillShapedText(
            text = "On queue: 12 books",
            color = MaterialTheme.colorScheme.surfaceVariant,
            leadingIcon = { Icon(imageVector = Icons.Default.List, contentDescription = null) }
        )
        Spacer(modifier = Modifier.height(8.dp))
        PillShapedText(
            text = "Finished: 25 books",
            color = MaterialTheme.colorScheme.secondaryContainer,
            leadingIcon = { Icon(imageVector = Icons.Default.Done, contentDescription = null) }
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun UserStatsScreenPreview() {
    BookwormAppTheme {
        UserStatsScreen()
    }
}
