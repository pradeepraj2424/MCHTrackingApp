package com.drpradeepepi.mchtrackingapp.data.extensions

import com.drpradeepepi.mchtrackingapp.data.entity.MotherEntity
import com.drpradeepepi.mchtrackingapp.domain.model.Mother

// Entity to Domain Model conversion
fun MotherEntity.toMother() = Mother(
    name = this.name,
    age = this.age,
    phone = this.phone,
    address = this.address,
    husbandName = this.husbandName,
    lmp = this.lmp,
    edd = this.edd,
    gravida = this.gravida,
    para = this.para,
    riskFactor = this.riskFactor,
    anc1 = this.anc1,
    anc2 = this.anc2,
    anc3 = this.anc3
)

// Domain Model to Entity conversion
fun Mother.toMotherEntity(hscName: String) = MotherEntity(
    name = this.name,
    hscName = hscName,
    age = this.age,
    phone = this.phone,
    address = this.address,
    husbandName = this.husbandName,
    lmp = this.lmp,
    edd = this.edd,
    gravida = this.gravida,
    para = this.para,
    riskFactor = this.riskFactor,
    anc1 = this.anc1,
    anc2 = this.anc2,
    anc3 = this.anc3
)