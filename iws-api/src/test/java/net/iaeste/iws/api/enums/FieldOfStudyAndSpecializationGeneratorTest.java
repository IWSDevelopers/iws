/*
 * =============================================================================
 * Copyright 1998-2013, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.FieldOfStudyAndSpecializationGeneratorTest
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.api.enums;

import net.iaeste.iws.api.constants.IWSConstants;
import org.junit.Test;

import java.util.regex.Pattern;

/**
 * This little test is written, to convert the provided enum values from their
 * pretty-print version (the PDF variant) into a correct enum constant
 * value.<br />
 *   Please see trac task <a href="https://trac.iaeste.net/ticket/229">#229</a>
 * and <a href="https://trac.iaeste.net/ticket/416">#416</a> for more
 * information. The PDF which is used as source for this file is attached to
 * the second Trac task.
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   1.7
 */
public class FieldOfStudyAndSpecializationGeneratorTest {

    private static final String REGEX = "[ \\-/&]+";
    private static final Pattern PATTERN = Pattern.compile(REGEX);

    private static final String[][] data = {
            // Aeronautic Engineering
            { "Aeronautic Engineering", "Avionics" },
            { "Aeronautic Engineering", "Aerospace engineering" },
            // Agriculture
            { "Agriculture", "Agronomy/Agro ecology" },
            { "Agriculture", "Agribusiness" },
            { "Agriculture", "Animal Husbandry" },
            { "Agriculture", "Animal Science" },
            { "Agriculture", "Brewing" },
            { "Agriculture", "Dairy Farming" },
            { "Agriculture", "Earth sciences" },
            { "Agriculture", "Fisheries" },
            { "Agriculture", "Forest" },
            { "Agriculture", "Engineering" },
            { "Agriculture", "Forestry" },
            { "Agriculture", "Horticulture" },
            { "Agriculture", "Milling" },
            { "Agriculture", "Plant science" },
            { "Agriculture", "Seafood" },
            // Aquaculture
            { "Aquaculture", "Aquaculture/Aquafarming" },
            { "Aquaculture", "Marine Biology" },
            { "Aquaculture", "Marine Geology" },
            { "Aquaculture", "Marine Technology" },
            { "Aquaculture", "Seafood" },
            // Applied Arts
            { "Applied Arts", "Design" },
            { "Applied Arts", "Drawing" },
            { "Applied Arts", "Graphic Design" },
            { "Applied Arts", "Graphics & printing" },
            // Architecture
            { "Architecture", "Industrial Design" },
            { "Architecture", "Interior Design" },
            { "Architecture", "Landscape Architecture" },
            { "Architecture", "Urban Planning" },
            // Biology
            { "Biology", "Animal Behaviour" },
            { "Biology", "Animal Biology" },
            { "Biology", "Animal Husbandry" },
            { "Biology", "Animal Science" },
            { "Biology", "Biochemistry" },
            { "Biology", "Bioinformatics" },
            { "Biology", "Botanics" },
            { "Biology", "Cell Biology" },
            { "Biology", "Ecology" },
            { "Biology", "Entomology" },
            { "Biology", "Ethology" },
            { "Biology", "Genetics" },
            { "Biology", "Immunology" },
            { "Biology", "Marine Biology" },
            { "Biology", "Microbiology" },
            { "Biology", "Molecular biology" },
            { "Biology", "Mycology" },
            { "Biology", "Neuroscience" },
            { "Biology", "Plant Biology" },
            { "Biology", "Seafood" },
            { "Biology", "Toxicology" },
            { "Biology", "Zoology" },
            // Biomedical Science
            { "Biomedical Science", "Bioengineering" },
            { "Biomedical Science", "Bioinstrumentation" },
            { "Biomedical Science", "Biomaterials" },
            { "Biomedical Science", "Biomechanics" },
            { "Biomedical Science", "Bionics" },
            { "Biomedical Science", "Genetics" },
            { "Biomedical Science", "Medical engineering" },
            { "Biomedical Science", "Nanobiotechnology" },
            { "Biomedical Science", "Proteomics" },
            // Biotechnology
            { "Biotechnology", "Biochemistry" },
            { "Biotechnology", "Bioengineering" },
            { "Biotechnology", "Bioinformatics" },
            { "Biotechnology", "Biophysics" },
            { "Biotechnology", "Genetics" },
            { "Biotechnology", "Microbiology" },
            { "Biotechnology", "Molecular Biology" },
            { "Biotechnology", "Pharmacy" },
            { "Biotechnology", "Process Engineering" },
            // Economy and Management
            { "Economy and Management", "Administration" },
            { "Economy and Management", "Agribusiness" },
            { "Economy and Management", "Commerce" },
            { "Economy and Management", "Economics" },
            { "Economy and Management", "Enterprise Engineering" },
            { "Economy and Management", "Development studies" },
            { "Economy and Management", "Hotel Management" },
            { "Economy and Management", "Industrial Economics" },
            { "Economy and Management", "Innovation Management" },
            { "Economy and Management", "Logistics" },
            { "Economy and Management", "Project Management" },
            { "Economy and Management", "Production Management" },
            { "Economy and Management", "Statistics" },
            // Chemistry:
            { "Chemistry", "Analytical Chemistry" },
            { "Chemistry", "Biochemistry" },
            { "Chemistry", "Chemical Engineering" },
            { "Chemistry", "Inorganic Chemistry" },
            { "Chemistry", "Organic Chemistry" },
            { "Chemistry", "Petroleum Engineering" },
            { "Chemistry", "Pharmacy" },
            { "Chemistry", "Phase Transitions" },
            { "Chemistry", "Physical Chemistry" },
            { "Chemistry", "Polymer Science" },
            { "Chemistry", "Process Engineering" },
            // Civil engineering
            { "Civil engineering", "Construction" },
            { "Civil engineering", "Facility Surveying" },
            { "Civil engineering", "Geotechnology" },
            { "Civil engineering", "Land Surveying" },
            { "Civil engineering", "Mineral Processing" },
            { "Civil engineering", "Mining" },
            { "Civil engineering", "Port Logistics" },
            { "Civil engineering", "Process Engineering" },
            { "Civil engineering", "Structural Engineering" },
            { "Civil engineering", "Traffic Engineering" },
            { "Civil engineering", "Water Engineering" },
            { "Civil engineering", "Waste Water Treatment" },
            // Education
            { "Education", "Language" },
            { "Education", "Science" },
            // Electrical engineering
            { "Electrical engineering", "Audio Technology" },
            { "Electrical engineering", "Automation" },
            { "Electrical engineering", "Cybernetics" },
            { "Electrical engineering", "Electric Mobility" },
            { "Electrical engineering", "Electronics" },
            { "Electrical engineering", "Instrumentation" },
            { "Electrical engineering", "Mechatronics" },
            { "Electrical engineering", "Nanoelectronics" },
            { "Electrical engineering", "Photonics" },
            { "Electrical engineering", "Power Engineering" },
            { "Electrical engineering", "Power Generation" },
            // Environmental Science
            { "Environmental Science", "Biodiversity" },
            { "Environmental Science", "Earth Science" },
            { "Environmental Science", "Ecology" },
            { "Environmental Science", "Entomology" },
            { "Environmental Science", "Limnology" },
            { "Environmental Science", "Natural Resource Management" },
            { "Environmental Science", "Mycology" },
            { "Environmental Science", "Sustainability" },
            { "Environmental Science", "Soil and Air Pollution" },
            { "Environmental Science", "Water Waste Management" },
            // Energy Engineering
            { "Energy Engineering", "Energy and Process Engineering" },
            { "Energy Engineering", "Energy Systems Planning" },
            { "Energy Engineering", "Renewable Energy" },
            { "Energy Engineering", "Nuclear Energy Engineering" },
            { "Energy Engineering", "Power Engineering" },
            { "Energy Engineering", "Power Generation" },
            // Food Science
            { "Food Science", "Brewing" },
            { "Food Science", "Dairy Farming" },
            { "Food Science", "Food Chemistry" },
            { "Food Science", "Food Technology" },
            { "Food Science", "Food Quality and Safety" },
            { "Food Science", "Nutritional Science" },
            { "Food Science", "Oecotrophology" },
            { "Food Science", "Winery" },
            // Geoscience
            { "Geoscience", "Earth sciences" },
            { "Geoscience", "Economic Geography" },
            { "Geoscience", "Geochemistry" },
            { "Geoscience", "Geodesy and Cartography" },
            { "Geoscience", "Geoecology" },
            { "Geoscience", "Geography" },
            { "Geoscience", "Geology" },
            { "Geoscience", "Geomatics" },
            { "Geoscience", "Geophysics" },
            { "Geoscience", "Geotechnology" },
            { "Geoscience", "GIS" },
            { "Geoscience", "Marine Geology" },
            { "Geoscience", "Metallurgy" },
            { "Geoscience", "Mineralogy" },
            { "Geoscience", "Mining" },
            { "Geoscience", "Oceanography" },
            { "Geoscience", "Seismology" },
            { "Geoscience", "Topology" },
            // Industrial engineering
            { "Industrial engineering", "Industrial Design" },
            { "Industrial engineering", "Industrial Economics" },
            { "Industrial engineering", "Innovation Strategy" },
            { "Industrial engineering", "Process Engineering" },
            { "Industrial engineering", "Product Development" },
            { "Industrial engineering", "Waste-water Management" },
            // IT
            { "IT", "Automation" },
            { "IT", "Artificial Intelligence" },
            { "IT", "Bioinformatics" },
            { "IT", "Business Informatics" },
            { "IT", "Business Intelligence" },
            { "IT", "Communications Technology" },
            { "IT", "Computer Engineering" },
            { "IT", "Computer Science" },
            { "IT", "Database Administration" },
            { "IT", "Digital Media" },
            { "IT", "Industrial Logistics" },
            { "IT", "Informatics" },
            { "IT", "Information Systems" },
            { "IT", "Information Technology" },
            { "IT", "Networks" },
            { "IT", "Robotics" },
            { "IT", "Simulation" },
            { "IT", "Software Development" },
            { "IT", "Software Engineering" },
            { "IT", "System Administration" },
            { "IT", "System Engineering" },
            { "IT", "System Development" },
            { "IT", "Telecommunications" },
            { "IT", "Testing" },
            { "IT", "Web design" },
            { "IT", "Web development" },
            // Mathematics
            { "Mathematics", "Applied Mathematics" },
            { "Mathematics", "Applied Statistics" },
            { "Mathematics", "Calculus" },
            { "Mathematics", "Discrete Mathematics" },
            { "Mathematics", "Financial Mathematics" },
            { "Mathematics", "Geometry" },
            { "Mathematics", "Optimization" },
            { "Mathematics", "Statistics" },
            // Material science
            { "Material science", "Material Technology" },
            { "Material science", "Metallurgy" },
            { "Material science", "Mineral Processing" },
            { "Material science", "Nanomaterials" },
            { "Material science", "Nanotechnology" },
            { "Material science", "Polymer Engineering" },
            { "Material science", "Process Engineering" },
            { "Material science", "Structural Engineering" },
            { "Material science", "Textile Technology" },
            { "Material science", "Wood and Paper Engineering" },
            // Mechanical engineering
            { "Mechanical engineering", "Automation" },
            { "Mechanical engineering", "Automotive engineering" },
            { "Mechanical engineering", "Biomechanics" },
            { "Mechanical engineering", "Hydraulics" },
            { "Mechanical engineering", "Industrial Design" },
            { "Mechanical engineering", "Machine Technology" },
            { "Mechanical engineering", "Marine and offshore engineering" },
            { "Mechanical engineering", "Mechatronics" },
            { "Mechanical engineering", "Mineral Processing" },
            { "Mechanical engineering", "Mining" },
            { "Mechanical engineering", "Naval Engineering" },
            { "Mechanical engineering", "Shipbuilding" },
            { "Mechanical engineering", "Petroleum Engineering" },
            { "Mechanical engineering", "Process Engineering" },
            { "Mechanical engineering", "Product Design" },
            { "Mechanical engineering", "Shipbuilding" },
            { "Mechanical engineering", "Wood and Paper Engineering" },
            // Media and Marketing
            { "Media and Marketing", "Media Technology" },
            { "Media and Marketing", "Digital Media" },
            { "Media and Marketing", "Marketing" },
            { "Media and Marketing", "Social Media" },
            // Physics
            { "Physics", "Astrophysics" },
            { "Physics", "Biophysics" },
            { "Physics", "Computational Physics" },
            { "Physics", "Cybernetics" },
            { "Physics", "Fluid Mechanics" },
            { "Physics", "Geophysics" },
            { "Physics", "Hydraulics" },
            { "Physics", "Medical Physics" },
            { "Physics", "Nanoelectronics" },
            { "Physics", "Nanomaterials" },
            { "Physics", "Optics" },
            { "Physics", "Quantum Mechanics" },
            { "Physics", "Statistical Physics" },
            { "Physics", "Thermodynamics" },
            // Veterinary Sciences
            { "Veterinary Sciences", "Bioveterinary Science" },
            { "Veterinary Sciences", "Veterinary Medicine" },
            { "Veterinary Sciences", "Veterinary Science" },
    };

    @Test
    public void createSpecializationValues() {
        for (final String[] row : data) {
            final String fieldOfStudy = row[0];
            final String specialization = row[1];

            final String name = PATTERN.matcher(specialization).replaceAll("_").toUpperCase(IWSConstants.DEFAULT_LOCALE);
            System.out.println(name + "(\"" + specialization + "\"),");
        }
    }
}
