/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-leargas) - net.iaeste.iws.leargas.persistence.OfferDao
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.leargas.persistence;

import net.iaeste.iws.leargas.exceptions.LeargasException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a DAO (Data Access Object), which will take Offers and either read
 * or write them to the database. The Class handles all mapping between the
 * Database Objects & Offer DTO.
 *
 * @author  Kim Jensen <kim@dawn.dk>
 * @version Leargas 1.0
 * @since   Java 1.8
 */
public final class OfferDao {

    private final Connection connection;

    public OfferDao(final Connection connnection) {
        this.connection = connnection;
    }

    public List<OfferEntity> findAllOffers() {
        try (PreparedStatement statement = connection.prepareStatement(Queries.OFFER_FIND)) {

            return findOffer(statement);
        } catch (SQLException e) {
            throw new LeargasException(e.getMessage(), e);
        }
    }

    private List<OfferEntity> findOffer(final PreparedStatement statement) {
        try (ResultSet resultSet = statement.executeQuery()) {
            List<OfferEntity> entities = new ArrayList<>();
            while (resultSet.next()) {
                OfferEntity entity = new OfferEntity();

                entity.setRefNo(resultSet.getString(1));
                entity.setDeadline(resultSet.getString(2));
                entity.setComment(resultSet.getString(3));
                entity.setEmployer(resultSet.getString(4));
                entity.setAddress1(resultSet.getString(5));
                entity.setAddress2(resultSet.getString(6));
                entity.setPostbox(resultSet.getString(7));
                entity.setPostalcode(resultSet.getString(8));
                entity.setCity(resultSet.getString(9));
                entity.setState(resultSet.getString(10));
                entity.setCountry(resultSet.getString(11));
                entity.setWebsite(resultSet.getString(12));
                entity.setWorkplace(resultSet.getString(13));
                entity.setBusiness(resultSet.getString(14));
                entity.setResponsible(resultSet.getString(15));
                entity.setAirport(resultSet.getString(16));
                entity.setTransport(resultSet.getString(17));
                entity.setEmployees(resultSet.getString(18));
                entity.setHoursweekly(resultSet.getString(19));
                entity.setHoursdaily(resultSet.getString(20));
                entity.setCanteen(resultSet.getString(21));
                entity.setFaculty(resultSet.getString(22));
                entity.setSpecialization(resultSet.getString(23));
                entity.setTrainingrequired(resultSet.getString(24));
                entity.setOtherrequirements(resultSet.getString(25));
                entity.setWorkkind(resultSet.getString(26));
                entity.setWeeksmin(resultSet.getString(27));
                entity.setWeeksmax(resultSet.getString(28));
                entity.setFrom(resultSet.getString(29));
                entity.setTo(resultSet.getString(30));
                entity.setStudycompletedBeginning(resultSet.getString(31));
                entity.setStudycompletedMiddle(resultSet.getString(32));
                entity.setStudycompletedEnd(resultSet.getString(33));
                entity.setWorktypeP(resultSet.getString(34));
                entity.setWorktypeR(resultSet.getString(35));
                entity.setWorktypeW(resultSet.getString(36));
                entity.setWorktypeN(resultSet.getString(37));
                entity.setLanguage1(resultSet.getString(38));
                entity.setLanguage1level(resultSet.getString(39));
                entity.setLanguage1or(resultSet.getString(40));
                entity.setLanguage2(resultSet.getString(41));
                entity.setLanguage2level(resultSet.getString(42));
                entity.setLanguage2or(resultSet.getString(43));
                entity.setLanguage3(resultSet.getString(44));
                entity.setLanguage3level(resultSet.getString(45));
                entity.setCurrency(resultSet.getString(46));
                entity.setPayment(resultSet.getString(47));
                entity.setPaymentfrequency(resultSet.getString(48));
                entity.setDeduction(resultSet.getString(49));
                entity.setLodging(resultSet.getString(50));
                entity.setLodgingcost(resultSet.getString(51));
                entity.setLodgingcostfrequency(resultSet.getString(52));
                entity.setLivingcost(resultSet.getString(53));
                entity.setLivingcostfrequency(resultSet.getString(54));
                entity.setNohardcopies(resultSet.getString(55));
                entity.setStatus(resultSet.getString(56));
                entity.setPeriod2From(resultSet.getString(57));
                entity.setPeriod2To(resultSet.getString(58));
                entity.setHolidaysFrom(resultSet.getString(59));
                entity.setHolidaysTo(resultSet.getString(60));
                entity.setAdditionalInfo(resultSet.getString(61));
                entity.setShared(resultSet.getString(62));
                entity.setLastModified(resultSet.getString(63));
                entity.setNsFirstName(resultSet.getString(64));
                entity.setNsLastName(resultSet.getString(65));

                entities.add(entity);
            }

            return entities;
        } catch (SQLException e) {
            throw new LeargasException(e.getMessage(), e);
        }
    }

    public boolean insertOffer(final OfferEntity entity) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.OFFER_INSERT)) {
            statement.setString( 1, entity.getRefNo());
            statement.setString( 2, entity.getDeadline());
            statement.setString( 3, entity.getComment());
            statement.setString( 4, entity.getEmployer());
            statement.setString( 5, entity.getAddress1());
            statement.setString( 6, entity.getAddress2());
            statement.setString( 7, entity.getPostbox());
            statement.setString( 8, entity.getPostalcode());
            statement.setString( 9, entity.getCity());
            statement.setString(10, entity.getState());
            statement.setString(11, entity.getCountry());
            statement.setString(12, entity.getWebsite());
            statement.setString(13, entity.getWorkplace());
            statement.setString(14, entity.getBusiness());
            statement.setString(15, entity.getResponsible());
            statement.setString(16, entity.getAirport());
            statement.setString(17, entity.getTransport());
            statement.setString(18, entity.getEmployees());
            statement.setString(19, entity.getHoursweekly());
            statement.setString(20, entity.getHoursdaily());
            statement.setString(21, entity.getCanteen());
            statement.setString(22, entity.getFaculty());
            statement.setString(23, entity.getSpecialization());
            statement.setString(24, entity.getTrainingrequired());
            statement.setString(25, entity.getOtherrequirements());
            statement.setString(26, entity.getWorkkind());
            statement.setString(27, entity.getWeeksmin());
            statement.setString(28, entity.getWeeksmax());
            statement.setString(29, entity.getFrom());
            statement.setString(30, entity.getTo());
            statement.setString(31, entity.getStudycompletedBeginning());
            statement.setString(32, entity.getStudycompletedMiddle());
            statement.setString(33, entity.getStudycompletedEnd());
            statement.setString(34, entity.getWorktypeP());
            statement.setString(35, entity.getWorktypeR());
            statement.setString(36, entity.getWorktypeW());
            statement.setString(37, entity.getWorktypeN());
            statement.setString(38, entity.getLanguage1());
            statement.setString(39, entity.getLanguage1level());
            statement.setString(40, entity.getLanguage1or());
            statement.setString(41, entity.getLanguage2());
            statement.setString(42, entity.getLanguage2level());
            statement.setString(43, entity.getLanguage2or());
            statement.setString(44, entity.getLanguage3());
            statement.setString(45, entity.getLanguage3level());
            statement.setString(46, entity.getCurrency());
            statement.setString(47, entity.getPayment());
            statement.setString(48, entity.getPaymentfrequency());
            statement.setString(49, entity.getDeduction());
            statement.setString(50, entity.getLodging());
            statement.setString(51, entity.getLodgingcost());
            statement.setString(52, entity.getLodgingcostfrequency());
            statement.setString(53, entity.getLivingcost());
            statement.setString(54, entity.getLivingcostfrequency());
            statement.setString(55, entity.getNohardcopies());
            statement.setString(56, entity.getStatus());
            statement.setString(57, entity.getPeriod2From());
            statement.setString(58, entity.getPeriod2To());
            statement.setString(59, entity.getHolidaysFrom());
            statement.setString(60, entity.getHolidaysTo());
            statement.setString(61, entity.getAdditionalInfo());
            statement.setString(62, entity.getShared());
            statement.setString(63, entity.getLastModified());
            statement.setString(64, entity.getNsFirstName());
            statement.setString(65, entity.getNsLastName());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new LeargasException(e.getMessage(), e);
        }
    }

    public boolean updateOffer(final OfferEntity entity) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.OFFER_UPDATE)) {
            statement.setString( 1, entity.getDeadline());
            statement.setString( 2, entity.getComment());
            statement.setString( 3, entity.getEmployer());
            statement.setString( 4, entity.getAddress1());
            statement.setString( 5, entity.getAddress2());
            statement.setString( 6, entity.getPostbox());
            statement.setString( 7, entity.getPostalcode());
            statement.setString( 8, entity.getCity());
            statement.setString( 9, entity.getState());
            statement.setString(10, entity.getCountry());
            statement.setString(11, entity.getWebsite());
            statement.setString(12, entity.getWorkplace());
            statement.setString(13, entity.getBusiness());
            statement.setString(14, entity.getResponsible());
            statement.setString(15, entity.getAirport());
            statement.setString(16, entity.getTransport());
            statement.setString(17, entity.getEmployees());
            statement.setString(18, entity.getHoursweekly());
            statement.setString(19, entity.getHoursdaily());
            statement.setString(20, entity.getCanteen());
            statement.setString(21, entity.getFaculty());
            statement.setString(22, entity.getSpecialization());
            statement.setString(23, entity.getTrainingrequired());
            statement.setString(24, entity.getOtherrequirements());
            statement.setString(25, entity.getWorkkind());
            statement.setString(26, entity.getWeeksmin());
            statement.setString(27, entity.getWeeksmax());
            statement.setString(28, entity.getFrom());
            statement.setString(29, entity.getTo());
            statement.setString(30, entity.getStudycompletedBeginning());
            statement.setString(31, entity.getStudycompletedMiddle());
            statement.setString(32, entity.getStudycompletedEnd());
            statement.setString(33, entity.getWorktypeP());
            statement.setString(34, entity.getWorktypeR());
            statement.setString(35, entity.getWorktypeW());
            statement.setString(36, entity.getWorktypeN());
            statement.setString(37, entity.getLanguage1());
            statement.setString(38, entity.getLanguage1level());
            statement.setString(39, entity.getLanguage1or());
            statement.setString(40, entity.getLanguage2());
            statement.setString(41, entity.getLanguage2level());
            statement.setString(42, entity.getLanguage2or());
            statement.setString(43, entity.getLanguage3());
            statement.setString(44, entity.getLanguage3level());
            statement.setString(45, entity.getCurrency());
            statement.setString(46, entity.getPayment());
            statement.setString(47, entity.getPaymentfrequency());
            statement.setString(48, entity.getDeduction());
            statement.setString(49, entity.getLodging());
            statement.setString(50, entity.getLodgingcost());
            statement.setString(51, entity.getLodgingcostfrequency());
            statement.setString(52, entity.getLivingcost());
            statement.setString(53, entity.getLivingcostfrequency());
            statement.setString(54, entity.getNohardcopies());
            statement.setString(55, entity.getStatus());
            statement.setString(56, entity.getPeriod2From());
            statement.setString(57, entity.getPeriod2To());
            statement.setString(58, entity.getHolidaysFrom());
            statement.setString(59, entity.getHolidaysTo());
            statement.setString(60, entity.getAdditionalInfo());
            statement.setString(61, entity.getShared());
            statement.setString(62, entity.getLastModified());
            statement.setString(63, entity.getNsFirstName());
            statement.setString(64, entity.getNsLastName());
            statement.setString(65, entity.getRefNo());

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new LeargasException(e.getMessage(), e);
        }
    }

    public boolean deleteOffer(final String refno) {
        try (PreparedStatement statement = connection.prepareStatement(Queries.OFFER_UPDATE)) {
            statement.setString(1, refno);

            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new LeargasException(e.getMessage(), e);
        }
    }
}
