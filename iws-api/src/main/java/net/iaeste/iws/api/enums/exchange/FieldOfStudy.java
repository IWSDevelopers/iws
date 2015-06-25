/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.exchange.FieldOfStudy
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums.exchange;

import net.iaeste.iws.api.enums.Descriptable;

import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Defines all Fields of Study together with their Specializations. Note, these
 * values are mapped into the database entities, and thus stored there. If a
 * value is changed. The reverse mapping (valueof) will fail. It is therefore
 * *not* allowed to change the spelling of an existing entry, without
 * verification that it is not used in the database.<br />
 * If it is used in the database, then it can also be that it is used
 * elsewhere, i.e. by third-party systems - and if so, then the entry may no
 * longer be touched.<br />
 * As third-party systems may also use the enum, changes to the list may only
 * be added to the end, since the ordinal values of records may otherwise
 * change, again causing various problems. It is *not* allowed to delete any
 * records from this list.<br />
 * Dealing with deprecation of the records is still a pending topic, but as
 * it very rarely happens, this is more an academic topic than a practical
 * matter.<br />
 * The initial listing is generated from the SID provided Excel Sheet, see
 * Trac ticket #416.
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.0
 */
@XmlType(name = "FieldOfStudy")
public enum FieldOfStudy implements Descriptable<FieldOfStudy> {

    AERONAUTIC_ENGINEERING("Aeronautic Engineering", Arrays.asList(
            Specialization.AVIONICS,
            Specialization.AEROSPACE_ENGINEERING
    )),

    AGRICULTURE("Agriculture", Arrays.asList(
            Specialization.AGRONOMY_AGRO_ECOLOGY,
            Specialization.AGRIBUSINESS,
            Specialization.ANIMAL_HUSBANDRY,
            Specialization.ANIMAL_SCIENCE,
            Specialization.BREWING,
            Specialization.DAIRY_FARMING,
            Specialization.EARTH_SCIENCE,
            Specialization.FISHERIES,
            Specialization.FOREST_ENGINEERING,
            Specialization.FORESTRY,
            Specialization.HORTICULTURE,
            Specialization.MILLING,
            Specialization.PLANT_SCIENCE,
            Specialization.SEAFOOD
    )),

    APPLIED_ARTS("Applied Arts", Arrays.asList(
            Specialization.DESIGN,
            Specialization.DRAWING,
            Specialization.GRAPHIC_DESIGN,
            Specialization.GRAPHICS_AND_PRINTING
    )),

    AQUA_CULTURE("Aquaculture", Arrays.asList(
            Specialization.AQUACULTURE_AQUAFARMING,
            Specialization.MARINE_BIOLOGY,
            Specialization.MARINE_GEOLOGY,
            Specialization.MARINE_TECHNOLOGY,
            Specialization.SEAFOOD
    )),

    ARCHITECTURE("Architecture", Arrays.asList(
            Specialization.INDUSTRIAL_DESIGN,
            Specialization.INTERIOR_DESIGN,
            Specialization.LANDSCAPE_ARCHITECTURE,
            Specialization.URBAN_PLANNING
    )),

    BIOLOGY("Biology", Arrays.asList(
            Specialization.ANIMAL_BEHAVIOUR,
            Specialization.ANIMAL_BIOLOGY,
            Specialization.ANIMAL_HUSBANDRY,
            Specialization.ANIMAL_SCIENCE,
            Specialization.BIOCHEMISTRY,
            Specialization.BIOINFORMATICS,
            Specialization.BOTANY,
            Specialization.CELL_BIOLOGY,
            Specialization.ECOLOGY,
            Specialization.ENTOMOLOGY,
            Specialization.ETHOLOGY,
            Specialization.GENETICS,
            Specialization.IMMUNOLOGY,
            Specialization.MARINE_BIOLOGY,
            Specialization.MICROBIOLOGY,
            Specialization.MOLECULAR_BIOLOGY,
            Specialization.MYCOLOGY,
            Specialization.NEUROSCIENCE,
            Specialization.PLANT_BIOLOGY,
            Specialization.SEAFOOD,
            Specialization.TOXICOLOGY,
            Specialization.ZOOLOGY
    )),

    BIOMEDICAL_SCIENCE("Biomedical Science", Arrays.asList(
            Specialization.BIOENGINEERING,
            Specialization.BIOINSTRUMENTATION,
            Specialization.BIOMATERIALS,
            Specialization.BIOMECHANICS,
            Specialization.BIONICS,
            Specialization.GENETICS,
            Specialization.MEDICAL_ENGINEERING,
            Specialization.NANOBIOTECHNOLOGY,
            Specialization.PROTEOMICS
    )),

    BIOTECHNOLOGY("Biotechnology", Arrays.asList(
            Specialization.BIOCHEMISTRY,
            Specialization.BIOENGINEERING,
            Specialization.BIOINFORMATICS,
            Specialization.BIOPHYSICS,
            Specialization.GENETICS,
            Specialization.MICROBIOLOGY,
            Specialization.MOLECULAR_BIOLOGY,
            Specialization.PHARMACY,
            Specialization.PROCESS_ENGINEERING
    )),

    CHEMISTRY("Chemistry", Arrays.asList(
            Specialization.ANALYTICAL_CHEMISTRY,
            Specialization.BIOCHEMISTRY,
            Specialization.CHEMICAL_ENGINEERING,
            Specialization.INORGANIC_CHEMISTRY,
            Specialization.ORGANIC_CHEMISTRY,
            Specialization.PETROLEUM_ENGINEERING,
            Specialization.PHARMACY,
            Specialization.PHASE_TRANSITIONS,
            Specialization.PHYSICAL_CHEMISTRY,
            Specialization.POLYMER_SCIENCE,
            Specialization.PROCESS_ENGINEERING
    )),

    CIVIL_ENGINEERING("Civil Engineering", Arrays.asList(
            Specialization.CONSTRUCTION,
            Specialization.FACILITY_SURVEYING,
            Specialization.GEOTECHNOLOGY,
            Specialization.LAND_SURVEYING,
            Specialization.MINERAL_PROCESSING,
            Specialization.MINING,
            Specialization.PORT_LOGISTICS,
            Specialization.PROCESS_ENGINEERING,
            Specialization.STRUCTURAL_ENGINEERING,
            Specialization.TRAFFIC_ENGINEERING,
            Specialization.WATER_ENGINEERING,
            Specialization.WASTEWATER_TREATMENT
    )),

    ECONOMY_AND_MANAGEMENT("Economy and Management", Arrays.asList(
            Specialization.ADMINISTRATION,
            Specialization.AGRIBUSINESS,
            Specialization.COMMERCE,
            Specialization.ECONOMICS,
            Specialization.ENTERPRISE_ENGINEERING,
            Specialization.DEVELOPMENT_STUDIES,
            Specialization.HOTEL_MANAGEMENT,
            Specialization.INDUSTRIAL_ECONOMICS,
            Specialization.INNOVATION_MANAGEMENT,
            Specialization.LOGISTICS,
            Specialization.PROJECT_MANAGEMENT,
            Specialization.PRODUCTION_MANAGEMENT,
            Specialization.STATISTICS
    )),

    EDUCATION("Education", Arrays.asList(
            Specialization.LANGUAGE,
            Specialization.SCIENCE
    )),

    ELECTRICAL_ENGINEERING("Electrical Engineering", Arrays.asList(
            Specialization.AUDIO_TECHNOLOGY,
            Specialization.AUTOMATION,
            Specialization.CYBERNETICS,
            Specialization.ELECTRIC_MOBILITY,
            Specialization.ELECTRONICS,
            Specialization.INSTRUMENTATION,
            Specialization.MECHATRONICS,
            Specialization.NANOELECTRONICS,
            Specialization.PHOTONICS,
            Specialization.POWER_ENGINEERING,
            Specialization.POWER_GENERATION
    )),

    ENERGY_ENGINEERING("Energy Engineering", Arrays.asList(
            Specialization.ENERGY_AND_PROCESS_ENGINEERING,
            Specialization.ENERGY_SYSTEMS_PLANNING,
            Specialization.RENEWABLE_ENERGY,
            Specialization.NUCLEAR_ENERGY_ENGINEERING,
            Specialization.POWER_ENGINEERING,
            Specialization.POWER_GENERATION
    )),

    ENVIRONMENTAL_ENGINEERING("Environmental Science", Arrays.asList(
            Specialization.BIODIVERSITY,
            Specialization.EARTH_SCIENCE,
            Specialization.ECOLOGY,
            Specialization.ENTOMOLOGY,
            Specialization.LIMNOLOGY,
            Specialization.NATURAL_RESOURCE_MANAGEMENT,
            Specialization.MYCOLOGY,
            Specialization.SUSTAINABILITY,
            Specialization.SOIL_AND_AIR_POLLUTION,
            Specialization.WASTEWATER_TREATMENT
    )),

    FOOD_SCIENCE("Food Science", Arrays.asList(
            Specialization.BREWING,
            Specialization.DAIRY_FARMING,
            Specialization.FOOD_CHEMISTRY,
            Specialization.FOOD_TECHNOLOGY,
            Specialization.FOOD_QUALITY_AND_SAFETY,
            Specialization.NUTRITIONAL_SCIENCE,
            Specialization.OECOTROPHOLOGY,
            Specialization.WINERY
    )),

    GEOSCIENCE("Geoscience", Arrays.asList(
            Specialization.EARTH_SCIENCE,
            Specialization.ECONOMIC_GEOGRAPHY,
            Specialization.GEOCHEMISTRY,
            Specialization.GEODESY_AND_CARTOGRAPHY,
            Specialization.GEOECOLOGY,
            Specialization.GEOGRAPHY,
            Specialization.GEOLOGY,
            Specialization.GEOMATICS,
            Specialization.GEOPHYSICS,
            Specialization.GEOTECHNOLOGY,
            Specialization.GIS,
            Specialization.MARINE_GEOLOGY,
            Specialization.METALLURGY,
            Specialization.MINERALOGY,
            Specialization.MINING,
            Specialization.OCEANOGRAPHY,
            Specialization.SEISMOLOGY,
            Specialization.TOPOLOGY
    )),

    INDUSTRIAL_ENGINEERING("Industrial Engineering", Arrays.asList(
            Specialization.INDUSTRIAL_DESIGN,
            Specialization.INDUSTRIAL_ECONOMICS,
            Specialization.INNOVATION_STRATEGY,
            Specialization.PROCESS_ENGINEERING,
            Specialization.PRODUCT_DEVELOPMENT,
            Specialization.WASTEWATER_TREATMENT
    )),

    IT("IT", Arrays.asList(
            Specialization.AUTOMATION,
            Specialization.ARTIFICIAL_INTELLIGENCE,
            Specialization.BIOINFORMATICS,
            Specialization.BUSINESS_INFORMATICS,
            Specialization.BUSINESS_INTELLIGENCE,
            Specialization.COMMUNICATIONS_TECHNOLOGY,
            Specialization.COMPUTER_ENGINEERING,
            Specialization.COMPUTER_SCIENCE,
            Specialization.DATABASE_ADMINISTRATION,
            Specialization.DIGITAL_MEDIA,
            Specialization.INDUSTRIAL_LOGISTICS,
            Specialization.INFORMATICS,
            Specialization.INFORMATION_SYSTEMS,
            Specialization.INFORMATION_TECHNOLOGY,
            Specialization.NETWORKS,
            Specialization.ROBOTICS,
            Specialization.SECURITY,
            Specialization.SIMULATION,
            Specialization.SOFTWARE_DEVELOPMENT,
            Specialization.SOFTWARE_ENGINEERING,
            Specialization.SYSTEM_ADMINISTRATION,
            Specialization.SYSTEM_ENGINEERING,
            Specialization.SYSTEM_DEVELOPMENT,
            Specialization.TELECOMMUNICATIONS,
            Specialization.TESTING,
            Specialization.WEB_DESIGN,
            Specialization.WEB_DEVELOPMENT
    )),

    MATERIAL_SCIENCE("Material Science", Arrays.asList(
            Specialization.MATERIAL_TECHNOLOGY,
            Specialization.METALLURGY,
            Specialization.MINERAL_PROCESSING,
            Specialization.NANOMATERIALS,
            Specialization.NANOTECHNOLOGY,
            Specialization.POLYMER_ENGINEERING,
            Specialization.PROCESS_ENGINEERING,
            Specialization.STRUCTURAL_ENGINEERING,
            Specialization.TEXTILE_TECHNOLOGY,
            Specialization.WOOD_AND_PAPER_ENGINEERING,
            Specialization.WOOD_SCIENCE
    )),

    MATHEMATICS("Mathematics", Arrays.asList(
            Specialization.APPLIED_MATHEMATICS,
            Specialization.APPLIED_STATISTICS,
            Specialization.CALCULUS,
            Specialization.DISCRETE_MATHEMATICS,
            Specialization.FINANCIAL_MATHEMATICS,
            Specialization.GEOMETRY,
            Specialization.OPTIMIZATION,
            Specialization.STATISTICS
    )),

    MECHANICAL_ENGINEERING("Mechanical Engineering", Arrays.asList(
            Specialization.AUTOMATION,
            Specialization.AUTOMOTIVE_ENGINEERING,
            Specialization.BIOMECHANICS,
            Specialization.HYDRAULICS,
            Specialization.INDUSTRIAL_DESIGN,
            Specialization.MACHINE_TECHNOLOGY,
            Specialization.MARINE_AND_OFFSHORE_ENGINEERING,
            Specialization.MECHATRONICS,
            Specialization.MINERAL_PROCESSING,
            Specialization.MINING,
            Specialization.NAVAL_ENGINEERING,
            Specialization.SHIPBUILDING,
            Specialization.PETROLEUM_ENGINEERING,
            Specialization.PROCESS_ENGINEERING,
            Specialization.PRODUCT_DESIGN,
            Specialization.WOOD_AND_PAPER_ENGINEERING
    )),

    MEDIA_AND_MARKETING("Media and Marketing", Arrays.asList(
            Specialization.MEDIA_TECHNOLOGY,
            Specialization.DIGITAL_MEDIA,
            Specialization.MARKETING,
            Specialization.SOCIAL_MEDIA
    )),

    PHYSICS("Physics", Arrays.asList(
            Specialization.ASTROPHYSICS,
            Specialization.BIOPHYSICS,
            Specialization.COMPUTATIONAL_PHYSICS,
            Specialization.CYBERNETICS,
            Specialization.FLUID_MECHANICS,
            Specialization.GEOPHYSICS,
            Specialization.HYDRAULICS,
            Specialization.MEDICAL_PHYSICS,
            Specialization.NANOELECTRONICS,
            Specialization.NANOMATERIALS,
            Specialization.OPTICS,
            Specialization.QUANTUM_MECHANICS,
            Specialization.STATISTICAL_PHYSICS,
            Specialization.THERMODYNAMICS
    )),

    VETERINARY_SCIENCE("Veterinary Sciences", Arrays.asList(
            Specialization.BIOVETERINARY_SCIENCE,
            Specialization.VETERINARY_MEDICINE,
            Specialization.VETERINARY_SCIENCE
    )),

    OTHER("Other", new ArrayList<Specialization>(0)),

    ANY("Any", new ArrayList<Specialization>(0));

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;
    private final List<Specialization> specializations;

    FieldOfStudy(final String description, final List<Specialization> specializations) {
        this.description = description;
        this.specializations = specializations;
    }

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }
}
