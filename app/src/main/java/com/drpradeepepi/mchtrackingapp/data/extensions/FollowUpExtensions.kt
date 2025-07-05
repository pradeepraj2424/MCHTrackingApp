package com.drpradeepepi.mchtrackingapp.data.extensions

import com.drpradeepepi.mchtrackingapp.data.entity.FollowUpEntity
import com.drpradeepepi.mchtrackingapp.domain.model.FollowUp

// Entity to Domain Model conversion
fun FollowUpEntity.toFollowUp() = FollowUp(
    id = this.id,
    motherName = this.motherName,
    date = this.date,
    gestationalAge = this.gestationalAge,
    weight = this.weight,
    bloodPressure = this.bloodPressure,
    hemoglobin = this.hemoglobin,
    complaints = this.complaints,
    examination = this.examination,
    advice = this.advice,
    nextVisit = this.nextVisit,
    remarks = this.remarks
)

// Domain Model to Entity conversion
fun FollowUp.toFollowUpEntity() = FollowUpEntity(
    id = this.id,
    motherName = this.motherName,
    date = this.date,
    gestationalAge = this.gestationalAge,
    weight = this.weight,
    bloodPressure = this.bloodPressure,
    hemoglobin = this.hemoglobin,
    complaints = this.complaints,
    examination = this.examination,
    advice = this.advice,
    nextVisit = this.nextVisit,
    remarks = this.remarks
)