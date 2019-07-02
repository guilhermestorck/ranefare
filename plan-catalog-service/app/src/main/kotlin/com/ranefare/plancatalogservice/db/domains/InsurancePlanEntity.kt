package com.ranefare.plancatalogservice.db.domains


import org.hibernate.annotations.GenericGenerator
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = "insurancePlans")
data class InsurancePlanEntity(
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    val id: String?,

    @Column
    @NotNull
    val name: String,

    @Column
    @NotNull
    val costRate: Double,

    @ManyToMany(cascade = [CascadeType.MERGE])
    @JoinTable(
        name = "insurancePlans_insuranceCoverageItems",
        joinColumns = [JoinColumn(name = "insurancePlanId", referencedColumnName = "id")],
        inverseJoinColumns = [JoinColumn(name = "insuranceCoverageItemId", referencedColumnName = "id")]
    )
    val coverageItems: List<InsuranceCoverageItemEntity>
)