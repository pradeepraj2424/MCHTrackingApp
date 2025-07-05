package com.drpradeepepi.mchtrackingapp.data.extensions

import com.drpradeepepi.mchtrackingapp.data.entity.HSCEntity
import com.drpradeepepi.mchtrackingapp.domain.model.HSC

// Entity to Domain Model conversion
fun HSCEntity.toHSC() = HSC(
    name = this.name,
    location = this.location,
    contactNumber = this.contactNumber,
    anmName = this.anmName
)

// Domain Model to Entity conversion
fun HSC.toHSCEntity(phcName: String) = HSCEntity(
    name = this.name,
    phcName = phcName,
    location = this.location,
    contactNumber = this.contactNumber,
    anmName = this.anmName
)