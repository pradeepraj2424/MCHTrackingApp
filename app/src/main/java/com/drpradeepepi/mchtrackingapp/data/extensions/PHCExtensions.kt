package com.drpradeepepi.mchtrackingapp.data.extensions

import com.drpradeepepi.mchtrackingapp.data.entity.PHCEntity
import com.drpradeepepi.mchtrackingapp.domain.model.PHC

// Entity to Domain Model conversion
fun PHCEntity.toPHC() = PHC(
    name = this.name,
    location = this.location,
    contactNumber = this.contactNumber,
    doctorName = this.doctorName
)

// Domain Model to Entity conversion
fun PHC.toPHCEntity() = PHCEntity(
    name = this.name,
    location = this.location,
    contactNumber = this.contactNumber,
    doctorName = this.doctorName
)