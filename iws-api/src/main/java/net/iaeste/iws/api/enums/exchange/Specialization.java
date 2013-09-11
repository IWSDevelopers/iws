/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.exchange.Specialization
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

import net.iaeste.iws.api.constants.IWSConstants;

/**
 * Contains all predefined Specializations. Note, these values are mapped into
 * the database entities, and thus stored there. If a value is changed. The
 * reverse mapping (valueof) will fail. It is therefore *not* allowed to change
 * the spelling of an existing entry, without verification that it is not used
 * in the database.<br />
 *   If it is used in the database, then it can also be that it is used
 * elsewhere, i.e. by third-party systems - and if so, then the entry may no
 * longer be touched.<br />
 *   As third-party systems may also use the enum, changes to the list may only
 * be added to the end, since the ordinal values of records may otherwise
 * change, again causing various problems. It is *not* allowed to delete any
 * records from this list.<br />
 *   Dealing with deprecation of the records is still a pending topic, but as
 * it very rarely happens, this is more an academic topic than a practical
 * matter.
 *
 * @author  Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public enum Specialization {

    AGRONOMY_AGROECOLOGY,
    FISHERIES,
    FOREST_ENGINEERING,
    FORESTRY,
    HORTICULTURE,
    MILLING,
    PLANT_SCIENCE,
    SEA_FOOD_QUALITY,
    MARINE_BIOLOGY,
    MARINE_TECHNOLOGY,
    INTERIOR_DESIGN,
    LANDSCAPE_ARCHITECTURE,
    LANDSCAPE_PLANNING,
    URBAN_PLANNING,
    AQUACULTURE_AQUAFARMING,
    ANIMAL_BIOLOGY,
    ANIMAL_HUSBANDRY,
    ANIMAL_SCIENCE,
    BOTANIC,
    CELL_BIOLOGY,
    ECOLOGY,
    EPIDEMIOLOGY,
    ETHOLOGY,
    IMMUNOLOGY,
    PLANT_BIOLOGY,
    MICROBIOLOGY,
    MYCOLOGY,
    TOXICOLOGY,
    ZOOLOGY,
    BIOENGINEERING,
    GENETICS,
    MICRO_BIOLOGY,
    MOLECULAR_BIOLOGY,
    ADMINISTRATION,
    COMMERCE,
    ECONOMICS,
    ENTERPRISE_ENGINEERING,
    INDUSTRIAL_ECONOMICS,
    INNOVATION_MANAGEMENT,
    LOGISTICS,
    MANAGEMENT,
    PRODUCTION_MANAGEMENT,
    ANALYTICAL_CHEMISTRY,
    BIOCHEMISTRY,
    CHEMICAL_ENGINEERING,
    INORGANIC_CHEMISTRY,
    ORGANIC_CHEMISTRY,
    PHARMACEUTICAL_STUDIES,
    PHARMACY,
    PHYSICAL_CHEMISTRY,
    POLYMER_SCIENCE,
    AQUATIC_ENVIRONMENTAL_ENGINEERING,
    CONSTRUCTION,
    FLUID_MECHANICS,
    PORT_LOGISTICS,
    TRAFFIC_ENGINEERING,
    WASTE_WATER_TREATMENT,
    LANGUAGE,
    LIFE_SCIENCE_TEACHERS,
    ELECTRONICS,
    INSTRUMENTATION,
    POWER_ENGINEERING,
    SANITARY_ENGINEERING,
    SOIL_AND_AIR_POLLUTION,
    WATER_MANAGEMENT,
    BREWING,
    DIARY_FARMING,
    FOOD_CHEMISTRY,
    FOOD_TECHNOLOGY,
    NUTRITIONAL_SCIENCE,
    OECOTROPHOLOGY,
    WINERY,
    EARTH_SCIENCES,
    GEOCHEMISTRY,
    GEODESY_AND_CARTOGRAPHY,
    GENECOLOGY,
    GEOGRAPHY,
    GEOLOGY,
    GIS,
    GEOTECHNOLOGY,
    MARINE_GEOLOGY,
    MINERALOGY,
    BIOINFORMATICS,
    BUSINESS_INFORMATICS,
    BUSINESS_INTELLIGENCE,
    COMPUTER_ENGINEERING,
    COMMUNICATIONS_TECHNOLOGY,
    DATABASE_ADMINISTRATION,
    INFORMATICS,
    INFORMATION_SYSTEMS,
    INFORMATION_TECHNOLOGY,
    NETWORKS,
    PROGRAMMING,
    ROBOTICS,
    SECURITY,
    SIMULATION,
    SOFTWARE_DEVELOPMENT,
    SOFTWARE_ENGINEERING,
    SYSTEM_ADMINISTRATION,
    SYSTEM_ENGINEERING,
    SYSTEM_DEVELOPMENT,
    TELECOMMUNICATIONS,
    TESTING,
    WEB_DESIGN,
    WEB_DEVELOPMENT,
    APPLIED_MATHEMATICS,
    CALCULUS,
    DISCRETE_MATHEMATICS,
    GEOMETRY,
    OPTIMIZATION,
    STATISTICS,
    MATERIALS_TECHNOLOGY,
    METALLURGY,
    MINERAL_PROCESSING,
    POLYMER_ENGINEERING,
    STRUCTURAL_ENGINEERING,
    TEXTILE_TECHNOLOGY,
    WOOD_SCIENCE,
    AUTOMATION,
    BIOMECHANICS,
    INDUSTRIAL_DESIGN,
    INDUSTRIAL_ENGINEERING,
    MACHINE_TECHNOLOGY,
    MECHATRONICS,
    MINING,
    PROCESS_ENGINEERING,
    PROCESS_TECHNOLOGY,
    PRODUCT_DESIGN,
    WOOD_AND_PAPER_ENGINEERING,
    DESIGN,
    DRAWING,
    GRAPHICS_AND_PRINTING,
    MARINE_ENGINEERING,
    NAVAL_ENGINEERING,
    NAVAL_SHIPBUILDING,
    OIL_TECHNOLOGY,
    PETROLEUM_ENGINEERING,
    ASTROPHYSICS,
    ASTRONOMY,
    APPLIED_PHYSICS,
    BIOPHYSICS,
    COMPUTATIONAL_PHYSICS,
    COSMOLOGY,
    CYBERNETICS,
    GEOPHYSICS,
    LASER_TECHNOLOGY,
    MEDICAL_PHYSICS,
    NANOELECTRONICS,
    NANOMATERIALS,
    OPTICS,
    PHOTONICS,
    QUANTUM_MECHANICS,
    STATISTICAL_PHYSICS,
    THERMODYNAMICS;

    public String stringCSV() {
        return name().charAt(0) + name().toLowerCase(IWSConstants.DEFAULT_LOCALE).replace('_', ' ').substring(1);
    }
}
