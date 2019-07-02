package com.ranefare.plancatalogservice.db.domains


import org.hibernate.annotations.GenericGenerator
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "insuranceCoverageItems")
data class InsuranceCoverageItemEntity(
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    val id: String?,

    @Column
    @NotNull
    val name: String,

    @Column
    @NotNull
    val description: String,

    @ManyToMany(mappedBy = "coverageItems")
    val insurancePlans: List<InsurancePlanEntity>
)