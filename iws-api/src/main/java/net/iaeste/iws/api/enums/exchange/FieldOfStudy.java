/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Defines all Fields of Study together with their Specializations
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 * @noinspection FieldCanBeLocal, UnusedDeclaration
 */
public enum FieldOfStudy {

    AERONAUTIC_ENGINEERING(Collections.<Specialization>emptyList()),

    AGRICULTURE(Arrays.asList(
            Specialization.AGRONOMY_AGROECOLOGY,
            Specialization.ANIMAL_HUSBANDRY,
            Specialization.ANIMAL_SCIENCE,
            Specialization.DIARY_FARMING,
            Specialization.EARTH_SCIENCES,
            Specialization.ECOLOGY,
            Specialization.FISHERIES,
            Specialization.FOREST_ENGINEERING,
            Specialization.FORESTRY,
            Specialization.HORTICULTURE,
            Specialization.MILLING,
            Specialization.PLANT_SCIENCE,
            Specialization.SEA_FOOD_QUALITY
    )),

    AQUA_CULTURE(Arrays.asList(
            Specialization.MARINE_BIOLOGY,
            Specialization.MARINE_GEOLOGY,
            Specialization.MARINE_TECHNOLOGY,
            Specialization.SEA_FOOD_QUALITY
    )),

    ARCHITECTURE(Arrays.asList(
            Specialization.INDUSTRIAL_DESIGN,
            Specialization.INTERIOR_DESIGN,
            Specialization.LANDSCAPE_ARCHITECTURE,
            Specialization.LANDSCAPE_PLANNING,
            Specialization.URBAN_PLANNING
    )),

    BIOLOGY(Arrays.asList(
            Specialization.AQUACULTURE_AQUAFARMING,
            Specialization.ANIMAL_BIOLOGY,
            Specialization.ANIMAL_HUSBANDRY,
            Specialization.ANIMAL_SCIENCE,
            Specialization.BIOCHEMISTRY,
            Specialization.BIOINFORMATICS,
            Specialization.BOTANIC,
            Specialization.CELL_BIOLOGY,
            Specialization.ECOLOGY,
            Specialization.EPIDEMIOLOGY,
            Specialization.ETHOLOGY,
            Specialization.GENETICS,
            Specialization.IMMUNOLOGY,
            Specialization.PLANT_BIOLOGY,
            Specialization.MARINE_BIOLOGY,
            Specialization.MICROBIOLOGY,
            Specialization.MOLECULAR_BIOLOGY,
            Specialization.MYCOLOGY,
            Specialization.SEA_FOOD_QUALITY,
            Specialization.TOXICOLOGY,
            Specialization.ZOOLOGY
    )),

    BIOMEDICAL_ENGINEERING(Collections.<Specialization>emptyList()),

    BIOTECHNOLOGY(Arrays.asList(
            Specialization.BIOCHEMISTRY,
            Specialization.BIOENGINEERING,
            Specialization.BIOINFORMATICS,
            Specialization.BIOPHYSICS,
            Specialization.GENETICS,
            Specialization.MICRO_BIOLOGY,
            Specialization.MOLECULAR_BIOLOGY,
            Specialization.PROCESS_ENGINEERING,
            Specialization.PROCESS_TECHNOLOGY,
            Specialization.PHARMACY
    )),

    BUSINESS_STUDIES(Arrays.asList(
            Specialization.ADMINISTRATION,
            Specialization.COMMERCE,
            Specialization.ECONOMICS,
            Specialization.ENTERPRISE_ENGINEERING,
            Specialization.INDUSTRIAL_ECONOMICS,
            Specialization.INNOVATION_MANAGEMENT,
            Specialization.LOGISTICS,
            Specialization.MANAGEMENT,
            Specialization.PRODUCTION_MANAGEMENT,
            Specialization.STATISTICS
    )),

    CHEMISTRY(Arrays.asList(
            Specialization.ANALYTICAL_CHEMISTRY,
            Specialization.BIOCHEMISTRY,
            Specialization.CHEMICAL_ENGINEERING,
            Specialization.INORGANIC_CHEMISTRY,
            Specialization.ORGANIC_CHEMISTRY,
            Specialization.PHARMACEUTICAL_STUDIES,
            Specialization.PHARMACY,
            Specialization.PHYSICAL_CHEMISTRY,
            Specialization.POLYMER_SCIENCE,
            Specialization.PROCESS_ENGINEERING,
            Specialization.PROCESS_TECHNOLOGY
    )),

    CIVIL_ENGINEERING(Arrays.asList(
            Specialization.AQUATIC_ENVIRONMENTAL_ENGINEERING,
            Specialization.CONSTRUCTION,
            Specialization.FLUID_MECHANICS,
            Specialization.GEOTECHNOLOGY,
            Specialization.MINING,
            Specialization.PORT_LOGISTICS,
            Specialization.PROCESS_ENGINEERING,
            Specialization.PROCESS_TECHNOLOGY,
            Specialization.SANITARY_ENGINEERING,
            Specialization.TRAFFIC_ENGINEERING,
            Specialization.WASTE_WATER_TREATMENT
    )),

    EDUCATION(Arrays.asList(
            Specialization.LANGUAGE,
            Specialization.LIFE_SCIENCE_TEACHERS
    )),

    ELECTRICAL_ENGINEERING(Arrays.asList(
            Specialization.AUTOMATION,
            Specialization.CYBERNETICS,
            Specialization.ELECTRONICS,
            Specialization.INSTRUMENTATION,
            Specialization.MECHATRONICS,
            Specialization.NANOELECTRONICS,
            Specialization.PHOTONICS,
            Specialization.POWER_ENGINEERING
    )),

    ENVIRONMENTAL_ENGINEERING(Arrays.asList(
            Specialization.SANITARY_ENGINEERING,
            Specialization.SOIL_AND_AIR_POLLUTION,
            Specialization.WATER_MANAGEMENT
    )),

    FOOD_SCIENCE(Arrays.asList(
            Specialization.BREWING,
            Specialization.DIARY_FARMING,
            Specialization.FOOD_CHEMISTRY,
            Specialization.FOOD_TECHNOLOGY,
            Specialization.NUTRITIONAL_SCIENCE,
            Specialization.OECOTROPHOLOGY,
            Specialization.WINERY
    )),

    GEOSCIENCES(Arrays.asList(
            Specialization.EARTH_SCIENCES,
            Specialization.GEOCHEMISTRY,
            Specialization.GEODESY_AND_CARTOGRAPHY,
            Specialization.GENECOLOGY,
            Specialization.GEOGRAPHY,
            Specialization.GEOLOGY,
            Specialization.GIS,
            Specialization.GEOTECHNOLOGY,
            Specialization.MARINE_GEOLOGY,
            Specialization.METALLURGY,
            Specialization.MINERALOGY,
            Specialization.MINING,
            Specialization.GEOPHYSICS
    )),

    INDUSTRIAL_ENGINEERING(Arrays.asList(
            Specialization.PRODUCT_DESIGN
    )),

    IT(Arrays.asList(
            Specialization.AUTOMATION,
            Specialization.BIOINFORMATICS,
            Specialization.BUSINESS_INFORMATICS,
            Specialization.BUSINESS_INTELLIGENCE,
            Specialization.COMPUTER_ENGINEERING,
            Specialization.COMMUNICATIONS_TECHNOLOGY,
            Specialization.DATABASE_ADMINISTRATION,
            Specialization.INFORMATICS,
            Specialization.INFORMATION_SYSTEMS,
            Specialization.INFORMATION_TECHNOLOGY,
            Specialization.NETWORKS,
            Specialization.PROGRAMMING,
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

    MATHEMATICS(Arrays.asList(
            Specialization.APPLIED_MATHEMATICS,
            Specialization.CALCULUS,
            Specialization.DISCRETE_MATHEMATICS,
            Specialization.GEOMETRY,
            Specialization.OPTIMIZATION,
            Specialization.STATISTICS
    )),

    MATERIALS_SCIENCE(Arrays.asList(
            Specialization.MATERIALS_TECHNOLOGY,
            Specialization.METALLURGY,
            Specialization.MINING,
            Specialization.MINERAL_PROCESSING,
            Specialization.NANOMATERIALS,
            Specialization.POLYMER_ENGINEERING,
            Specialization.STRUCTURAL_ENGINEERING,
            Specialization.TEXTILE_TECHNOLOGY,
            Specialization.WOOD_AND_PAPER_ENGINEERING,
            Specialization.WOOD_SCIENCE
    )),

    MECHANICAL_ENGINEERING(Arrays.asList(
            Specialization.AUTOMATION,
            Specialization.BIOMECHANICS,
            Specialization.INDUSTRIAL_DESIGN,
            Specialization.INDUSTRIAL_ENGINEERING,
            Specialization.MACHINE_TECHNOLOGY,
            Specialization.MECHATRONICS,
            Specialization.MINING,
            Specialization.PETROLEUM_ENGINEERING,
            Specialization.PROCESS_ENGINEERING,
            Specialization.PROCESS_TECHNOLOGY,
            Specialization.PRODUCT_DESIGN,
            Specialization.WOOD_AND_PAPER_ENGINEERING
    )),

    MEDIA_AND_ART(Arrays.asList(
            Specialization.DESIGN,
            Specialization.DRAWING,
            Specialization.GRAPHICS_AND_PRINTING
    )),

    OFFSHORE(Arrays.asList(
            Specialization.MARINE_ENGINEERING,
            Specialization.NAVAL_ENGINEERING,
            Specialization.NAVAL_SHIPBUILDING,
            Specialization.OIL_TECHNOLOGY,
            Specialization.PETROLEUM_ENGINEERING
    )),

    PHYSICS(Arrays.asList(
            Specialization.ASTROPHYSICS,
            Specialization.ASTRONOMY,
            Specialization.APPLIED_PHYSICS,
            Specialization.BIOPHYSICS,
            Specialization.COMPUTATIONAL_PHYSICS,
            Specialization.COSMOLOGY,
            Specialization.CYBERNETICS,
            Specialization.GEOPHYSICS,
            Specialization.LASER_TECHNOLOGY,
            Specialization.MEDICAL_PHYSICS,
            Specialization.NANOELECTRONICS,
            Specialization.NANOMATERIALS,
            Specialization.OPTICS,
            Specialization.PHOTONICS,
            Specialization.QUANTUM_MECHANICS,
            Specialization.STATISTICAL_PHYSICS,
            Specialization.THERMODYNAMICS
    )),

    VETERINARY_SCIENCE(Collections.<Specialization>emptyList());

    private final List<Specialization> specializations;

    FieldOfStudy(final List<Specialization> specializations) {
        this.specializations = specializations;
    }

    public List<Specialization> getSpecializations() {
        return specializations;
    }

    public String toString(){
        String toReturn = this.name();
        toReturn.toLowerCase();
        toReturn.replace('_', ' ');
        return toReturn;
    }
}
