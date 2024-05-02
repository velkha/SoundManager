package org.velka.soundmanager.model

public data class SoundProfile(
    val name: String,
    val startTime: String,
    val endTime: String,
    val daysOfWeek: List<String>,
    val startDate: String?,
    val endDate: String?
)
