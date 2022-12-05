package com.example.submissioncompose.component.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme.typography
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.submissioncompose.component.image.AccountImage
import com.example.submissioncompose.model.Account2
import com.example.submissioncompose.ui.theme.graySurface

@Composable
fun TheListAccount(account: Account2, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = 2.dp,
        backgroundColor = graySurface,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Row {
            AccountImage(account)
            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth()
                    .align(Alignment.CenterVertically)
            ) {
                Text(text = account.name, style = typography.h6)
                Text(text = account.email, style = typography.caption)
            }
        }
    }
}