package com.opencrumb.shared.ui.calculator

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.math.max
import kotlin.math.roundToInt
import kotlin.math.round

private fun Double.format(decimals: Int): String {
    val multiplier = when (decimals) {
        0 -> 1.0
        1 -> 10.0
        2 -> 100.0
        3 -> 1000.0
        else -> 1.0
    }
    val rounded = round(this * multiplier) / multiplier
    return when (decimals) {
        0 -> rounded.toInt().toString()
        1 -> {
            val int = rounded.toInt()
            val dec = ((rounded - int) * 10).toInt()
            "$int.$dec"
        }
        2 -> {
            val int = rounded.toInt()
            val dec = ((rounded - int) * 100).toInt()
            "$int.${dec.toString().padStart(2, '0')}"
        }
        3 -> {
            val int = rounded.toInt()
            val dec = ((rounded - int) * 1000).toInt()
            "$int.${dec.toString().padStart(3, '0')}"
        }
        else -> rounded.toString()
    }
}

data class DoughTotals(
    val flour: Double = 0.0,
    val water: Double = 0.0,
    val salt: Double = 0.0,
    val yeast: Double = 0.0,
    val waterPercent: Double = 0.0,
    val saltPercent: Double = 0.0,
    val yeastPercent: Double = 0.0,
)

enum class YeastType(
    val displayName: String,
    val sameDayPercentage: Double,
    val nextDayPercentage: Double,
) {
    InstantDry("Instant Dry", 0.005, 0.0011),
    ActiveDry("Active Dry", 0.006, 0.0015),
    Fresh("Fresh", 0.0145, 0.0038),
}

enum class ProofingTime(val displayName: String) {
    SameDay("Same Day (3-8h)"),
    NextDay("Next Day (24h)"),
}

enum class DoughType(val displayName: String) {
    Neapolitan("Neapolitan"),
    Focaccia("Focaccia"),
}

private data class DoughDefaults(
    val doughBalls: Int,
    val ballWeight: Int,
    val hydration: Double,
    val saltPercent: Double,
    val yeastType: YeastType,
    val proofingTime: ProofingTime,
    val isYeastManual: Boolean,
    val manualYeastPercent: Double,
)

private val neapolitanDefaults = DoughDefaults(
    doughBalls = 3,
    ballWeight = 240,
    hydration = 70.0,
    saltPercent = 3.0,
    yeastType = YeastType.InstantDry,
    proofingTime = ProofingTime.SameDay,
    isYeastManual = false,
    manualYeastPercent = 0.11,
)

private val focacciaDefaults = DoughDefaults(
    doughBalls = 2,
    ballWeight = 476,
    hydration = 70.0,
    saltPercent = 2.5,
    yeastType = YeastType.InstantDry,
    proofingTime = ProofingTime.SameDay,
    isYeastManual = false,
    manualYeastPercent = 0.5,
)

@Composable
fun NumberStepper(
    label: String,
    value: Int,
    onValueChange: (Int) -> Unit,
    modifier: Modifier = Modifier,
    step: Int = 1,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            IconButton(onClick = { onValueChange(max(1, value - step)) }) {
                Text("-", style = MaterialTheme.typography.headlineMedium)
            }
            Text(text = value.toString(), style = MaterialTheme.typography.bodyLarge)
            IconButton(onClick = { onValueChange(value + step) }) {
                Icon(Icons.Default.Add, contentDescription = "Increase")
            }
        }
    }
}

@Composable
fun DecimalStepper(
    label: String,
    value: Double,
    onValueChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    step: Double = 1.0,
    decimals: Int = 1,
    enabled: Boolean = true,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyLarge)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            IconButton(
                onClick = { onValueChange(max(0.0, value - step)) },
                enabled = enabled,
            ) {
                Text("-", style = MaterialTheme.typography.headlineMedium)
            }
            val formatted = when (decimals) {
                0 -> "${value.toInt()}%"
                else -> "${value.format(decimals)}%"
            }
            Text(text = formatted, style = MaterialTheme.typography.bodyLarge)
            IconButton(
                onClick = { onValueChange(value + step) },
                enabled = enabled,
            ) {
                Icon(Icons.Default.Add, contentDescription = "Increase")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculatorScreen(modifier: Modifier = Modifier) {
    var selectedDoughType by remember { mutableStateOf(DoughType.Neapolitan) }
    var doughBalls by remember { mutableIntStateOf(neapolitanDefaults.doughBalls) }
    var ballWeight by remember { mutableIntStateOf(neapolitanDefaults.ballWeight) }
    var hydration by remember { mutableDoubleStateOf(neapolitanDefaults.hydration) }
    var saltPercent by remember { mutableDoubleStateOf(neapolitanDefaults.saltPercent) }
    var selectedYeastType by remember { mutableStateOf(neapolitanDefaults.yeastType) }
    var selectedProofingTime by remember { mutableStateOf(neapolitanDefaults.proofingTime) }
    var isYeastManual by remember { mutableStateOf(neapolitanDefaults.isYeastManual) }
    var manualYeastPercent by remember { mutableDoubleStateOf(neapolitanDefaults.manualYeastPercent) }
    var yeastExpanded by remember { mutableStateOf(false) }
    var proofingTimeExpanded by remember { mutableStateOf(false) }

    val applyDefaults: (DoughType) -> Unit = { doughType ->
        val defaults = when (doughType) {
            DoughType.Neapolitan -> neapolitanDefaults
            DoughType.Focaccia -> focacciaDefaults
        }
        doughBalls = defaults.doughBalls
        ballWeight = defaults.ballWeight
        hydration = defaults.hydration
        saltPercent = defaults.saltPercent
        selectedYeastType = defaults.yeastType
        selectedProofingTime = defaults.proofingTime
        isYeastManual = defaults.isYeastManual
        manualYeastPercent = defaults.manualYeastPercent
    }

    val totals by remember(
        doughBalls, ballWeight, hydration, saltPercent,
        selectedYeastType, selectedProofingTime, isYeastManual, manualYeastPercent,
    ) {
        derivedStateOf {
            val totalDoughWeight = doughBalls * ballWeight
            val finalYeastPercentValue = if (isYeastManual) {
                manualYeastPercent
            } else {
                val yeastFactor = when (selectedProofingTime) {
                    ProofingTime.SameDay -> selectedYeastType.sameDayPercentage
                    ProofingTime.NextDay -> selectedYeastType.nextDayPercentage
                }
                yeastFactor * 100.0
            }

            val hydrationFactor = hydration / 100.0
            val saltFactor = saltPercent / 100.0
            val yeastFactor = finalYeastPercentValue / 100.0
            val totalFactor = 1.0 + hydrationFactor + saltFactor + yeastFactor

            if (totalDoughWeight > 0 && totalFactor > 0) {
                val flour = totalDoughWeight / totalFactor
                DoughTotals(
                    flour = flour,
                    water = flour * hydrationFactor,
                    salt = flour * saltFactor,
                    yeast = flour * yeastFactor,
                    waterPercent = hydration,
                    saltPercent = saltPercent,
                    yeastPercent = finalYeastPercentValue,
                )
            } else {
                DoughTotals()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Dough Calculator") },
                actions = {
                    IconButton(onClick = { applyDefaults(selectedDoughType) }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Reset to Defaults")
                    }
                },
            )
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TabRow(selectedTabIndex = selectedDoughType.ordinal) {
                DoughType.entries.forEach { doughType ->
                    Tab(
                        selected = selectedDoughType == doughType,
                        onClick = {
                            selectedDoughType = doughType
                            applyDefaults(doughType)
                        },
                        text = { Text(doughType.displayName) },
                    )
                }
            }

            Column(
                modifier = Modifier.padding(16.dp).verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        Text("Flour: ${totals.flour.roundToInt()}g (100%)", style = MaterialTheme.typography.bodyLarge)
                        Text("Water: ${totals.water.roundToInt()}g (${totals.waterPercent.toInt()}%)", style = MaterialTheme.typography.bodyLarge)
                        Text("Salt: ${totals.salt.format(1)}g (${totals.saltPercent.toInt()}%)", style = MaterialTheme.typography.bodyLarge)
                        Text("Yeast: ${totals.yeast.format(2)}g (${totals.yeastPercent.format(3)}%)", style = MaterialTheme.typography.bodyLarge)
                    }
                }

                NumberStepper("Dough balls", doughBalls, { doughBalls = it })
                NumberStepper("Ball weight (g)", ballWeight, { ballWeight = it }, step = 5)
                DecimalStepper("Hydration", hydration, { hydration = it }, step = 1.0, decimals = 0)
                DecimalStepper("Salt", saltPercent, { saltPercent = it }, step = 0.1, decimals = 0)

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text("Manual Yeast Control", style = MaterialTheme.typography.bodyLarge)
                    Switch(checked = isYeastManual, onCheckedChange = { isYeastManual = it })
                }

                ExposedDropdownMenuBox(
                    expanded = yeastExpanded,
                    onExpandedChange = { if (!isYeastManual) yeastExpanded = !yeastExpanded },
                ) {
                    OutlinedTextField(
                        value = selectedYeastType.displayName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Yeast type") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = yeastExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        enabled = !isYeastManual,
                    )
                    ExposedDropdownMenu(
                        expanded = yeastExpanded && !isYeastManual,
                        onDismissRequest = { yeastExpanded = false },
                    ) {
                        YeastType.entries.forEach { yeastType ->
                            DropdownMenuItem(
                                text = { Text(yeastType.displayName) },
                                onClick = {
                                    selectedYeastType = yeastType
                                    yeastExpanded = false
                                },
                            )
                        }
                    }
                }

                ExposedDropdownMenuBox(
                    expanded = proofingTimeExpanded,
                    onExpandedChange = { if (!isYeastManual) proofingTimeExpanded = !proofingTimeExpanded },
                ) {
                    OutlinedTextField(
                        value = selectedProofingTime.displayName,
                        onValueChange = {},
                        readOnly = true,
                        label = { Text("Proofing time") },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = proofingTimeExpanded) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        enabled = !isYeastManual,
                    )
                    ExposedDropdownMenu(
                        expanded = proofingTimeExpanded && !isYeastManual,
                        onDismissRequest = { proofingTimeExpanded = false },
                    ) {
                        ProofingTime.entries.forEach { proofingTime ->
                            DropdownMenuItem(
                                text = { Text(proofingTime.displayName) },
                                onClick = {
                                    selectedProofingTime = proofingTime
                                    proofingTimeExpanded = false
                                },
                            )
                        }
                    }
                }

                val calculatedYeastPercent = (when (selectedProofingTime) {
                    ProofingTime.SameDay -> selectedYeastType.sameDayPercentage
                    ProofingTime.NextDay -> selectedYeastType.nextDayPercentage
                }) * 100.0

                DecimalStepper(
                    "Yeast",
                    if (isYeastManual) manualYeastPercent else calculatedYeastPercent,
                    { if (isYeastManual) manualYeastPercent = it },
                    step = 0.01,
                    decimals = 3,
                    enabled = isYeastManual,
                )
            }
        }
    }
}
