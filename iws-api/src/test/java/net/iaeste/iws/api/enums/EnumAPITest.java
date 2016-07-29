/*
 * Licensed to IAESTE A.s.b.l. (IAESTE) under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership. The Authors
 * (See the AUTHORS file distributed with this work) licenses this file to
 * You under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License.  You may obtain a
 * copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.iaeste.iws.api.enums;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import net.iaeste.iws.api.Administration;
import org.junit.Test;

/**
 * <p>This test class, will verify the correctness of all existing enumerated
 * types, this is done, to avoid that changes is made lightly, since the enums
 * are part of the public API, and thus any change will have implications for
 * all Clients.</p>
 *
 * <p>Since the enumerated values is used both by Clients and internally in the
 * database, it is very important that any change is handled in a controlled
 * manner.</p>
 *
 * @author  Kim Jensen / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since   IWS 1.2
 */
public final class EnumAPITest {

    @Test
    public void testActionEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(Action.CREATE.name(), is("CREATE"));
        assertThat(Action.UPDATE.name(), is("UPDATE"));
        assertThat(Action.UPGRADE.name(), is("UPGRADE"));
        assertThat(Action.PROCESS.name(), is("PROCESS"));
        assertThat(Action.MOVE.name(), is("MOVE"));
        assertThat(Action.CHANGE_NS.name(), is("CHANGE_NS"));
        assertThat(Action.MERGE.name(), is("MERGE"));
        assertThat(Action.ACTIVATE.name(), is("ACTIVATE"));
        assertThat(Action.SUSPEND.name(), is("SUSPEND"));
        assertThat(Action.DELETE.name(), is("DELETE"));
        assertThat(Action.REMOVE.name(), is("REMOVE"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(Action.CREATE.ordinal(), is(0));
        assertThat(Action.UPDATE.ordinal(), is(1));
        assertThat(Action.UPGRADE.ordinal(), is(2));
        assertThat(Action.PROCESS.ordinal(), is(3));
        assertThat(Action.MOVE.ordinal(), is(4));
        assertThat(Action.CHANGE_NS.ordinal(), is(5));
        assertThat(Action.MERGE.ordinal(), is(6));
        assertThat(Action.ACTIVATE.ordinal(), is(7));
        assertThat(Action.SUSPEND.ordinal(), is(8));
        assertThat(Action.DELETE.ordinal(), is(9));
        assertThat(Action.REMOVE.ordinal(), is(10));
    }

    @Test
    public void testContactsTypeEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(ContactsType.USER.name(), is("USER"));
        assertThat(ContactsType.GROUP.name(), is("GROUP"));
        assertThat(ContactsType.OTHER.name(), is("OTHER"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(ContactsType.USER.ordinal(), is(0));
        assertThat(ContactsType.GROUP.ordinal(), is(1));
        assertThat(ContactsType.OTHER.ordinal(), is(2));
    }

    @Test
    public void testCountryTypeEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(CountryType.COMMITTEES.name(), is("COMMITTEES"));
        assertThat(CountryType.COUNTRIES.name(), is("COUNTRIES"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(CountryType.COMMITTEES.ordinal(), is(0));
        assertThat(CountryType.COUNTRIES.ordinal(), is(1));
    }

    @Test
    public void testCurrencyEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(Currency.AED.name(), is("AED"));
        assertThat(Currency.AFN.name(), is("AFN"));
        assertThat(Currency.ALL.name(), is("ALL"));
        assertThat(Currency.AMD.name(), is("AMD"));
        assertThat(Currency.ANG.name(), is("ANG"));
        assertThat(Currency.AOA.name(), is("AOA"));
        assertThat(Currency.ARS.name(), is("ARS"));
        assertThat(Currency.AUD.name(), is("AUD"));
        assertThat(Currency.AWG.name(), is("AWG"));
        assertThat(Currency.AZN.name(), is("AZN"));
        assertThat(Currency.BAM.name(), is("BAM"));
        assertThat(Currency.BBD.name(), is("BBD"));
        assertThat(Currency.BDT.name(), is("BDT"));
        assertThat(Currency.BGN.name(), is("BGN"));
        assertThat(Currency.BHD.name(), is("BHD"));
        assertThat(Currency.BIF.name(), is("BIF"));
        assertThat(Currency.BMD.name(), is("BMD"));
        assertThat(Currency.BND.name(), is("BND"));
        assertThat(Currency.BOB.name(), is("BOB"));
        assertThat(Currency.BRL.name(), is("BRL"));
        assertThat(Currency.BSD.name(), is("BSD"));
        assertThat(Currency.BTN.name(), is("BTN"));
        assertThat(Currency.BWP.name(), is("BWP"));
        assertThat(Currency.BYR.name(), is("BYR"));
        assertThat(Currency.BZD.name(), is("BZD"));
        assertThat(Currency.CAD.name(), is("CAD"));
        assertThat(Currency.CDF.name(), is("CDF"));
        assertThat(Currency.CHF.name(), is("CHF"));
        assertThat(Currency.CLP.name(), is("CLP"));
        assertThat(Currency.CNY.name(), is("CNY"));
        assertThat(Currency.COP.name(), is("COP"));
        assertThat(Currency.CRC.name(), is("CRC"));
        assertThat(Currency.CUC.name(), is("CUC"));
        assertThat(Currency.CUP.name(), is("CUP"));
        assertThat(Currency.CVE.name(), is("CVE"));
        assertThat(Currency.CZK.name(), is("CZK"));
        assertThat(Currency.DJF.name(), is("DJF"));
        assertThat(Currency.DKK.name(), is("DKK"));
        assertThat(Currency.DOP.name(), is("DOP"));
        assertThat(Currency.DZD.name(), is("DZD"));
        assertThat(Currency.EGP.name(), is("EGP"));
        assertThat(Currency.ERN.name(), is("ERN"));
        assertThat(Currency.ETB.name(), is("ETB"));
        assertThat(Currency.EUR.name(), is("EUR"));
        assertThat(Currency.FJD.name(), is("FJD"));
        assertThat(Currency.FKP.name(), is("FKP"));
        assertThat(Currency.GBP.name(), is("GBP"));
        assertThat(Currency.GEL.name(), is("GEL"));
        assertThat(Currency.GGP.name(), is("GGP"));
        assertThat(Currency.GHS.name(), is("GHS"));
        assertThat(Currency.GIP.name(), is("GIP"));
        assertThat(Currency.GMD.name(), is("GMD"));
        assertThat(Currency.GNF.name(), is("GNF"));
        assertThat(Currency.GTQ.name(), is("GTQ"));
        assertThat(Currency.GYD.name(), is("GYD"));
        assertThat(Currency.HKD.name(), is("HKD"));
        assertThat(Currency.HNL.name(), is("HNL"));
        assertThat(Currency.HRK.name(), is("HRK"));
        assertThat(Currency.HTG.name(), is("HTG"));
        assertThat(Currency.HUF.name(), is("HUF"));
        assertThat(Currency.IDR.name(), is("IDR"));
        assertThat(Currency.ILS.name(), is("ILS"));
        assertThat(Currency.IMP.name(), is("IMP"));
        assertThat(Currency.INR.name(), is("INR"));
        assertThat(Currency.IQD.name(), is("IQD"));
        assertThat(Currency.IRR.name(), is("IRR"));
        assertThat(Currency.ISK.name(), is("ISK"));
        assertThat(Currency.JEP.name(), is("JEP"));
        assertThat(Currency.JMD.name(), is("JMD"));
        assertThat(Currency.JOD.name(), is("JOD"));
        assertThat(Currency.JPY.name(), is("JPY"));
        assertThat(Currency.KES.name(), is("KES"));
        assertThat(Currency.KGS.name(), is("KGS"));
        assertThat(Currency.KHR.name(), is("KHR"));
        assertThat(Currency.KMF.name(), is("KMF"));
        assertThat(Currency.KPW.name(), is("KPW"));
        assertThat(Currency.KRW.name(), is("KRW"));
        assertThat(Currency.KWD.name(), is("KWD"));
        assertThat(Currency.KYD.name(), is("KYD"));
        assertThat(Currency.KZT.name(), is("KZT"));
        assertThat(Currency.LAK.name(), is("LAK"));
        assertThat(Currency.LBP.name(), is("LBP"));
        assertThat(Currency.LKR.name(), is("LKR"));
        assertThat(Currency.LRD.name(), is("LRD"));
        assertThat(Currency.LSL.name(), is("LSL"));
        assertThat(Currency.LTL.name(), is("LTL"));
        assertThat(Currency.LVL.name(), is("LVL"));
        assertThat(Currency.LYD.name(), is("LYD"));
        assertThat(Currency.MAD.name(), is("MAD"));
        assertThat(Currency.MDL.name(), is("MDL"));
        assertThat(Currency.MGA.name(), is("MGA"));
        assertThat(Currency.MKD.name(), is("MKD"));
        assertThat(Currency.MMK.name(), is("MMK"));
        assertThat(Currency.MNT.name(), is("MNT"));
        assertThat(Currency.MOP.name(), is("MOP"));
        assertThat(Currency.MRO.name(), is("MRO"));
        assertThat(Currency.MUR.name(), is("MUR"));
        assertThat(Currency.MVR.name(), is("MVR"));
        assertThat(Currency.MWK.name(), is("MWK"));
        assertThat(Currency.MXN.name(), is("MXN"));
        assertThat(Currency.MYR.name(), is("MYR"));
        assertThat(Currency.MZN.name(), is("MZN"));
        assertThat(Currency.NAD.name(), is("NAD"));
        assertThat(Currency.NGN.name(), is("NGN"));
        assertThat(Currency.NIO.name(), is("NIO"));
        assertThat(Currency.NOK.name(), is("NOK"));
        assertThat(Currency.NPR.name(), is("NPR"));
        assertThat(Currency.NZD.name(), is("NZD"));
        assertThat(Currency.OMR.name(), is("OMR"));
        assertThat(Currency.PAB.name(), is("PAB"));
        assertThat(Currency.PEN.name(), is("PEN"));
        assertThat(Currency.PGK.name(), is("PGK"));
        assertThat(Currency.PHP.name(), is("PHP"));
        assertThat(Currency.PKR.name(), is("PKR"));
        assertThat(Currency.PLN.name(), is("PLN"));
        assertThat(Currency.PYG.name(), is("PYG"));
        assertThat(Currency.QAR.name(), is("QAR"));
        assertThat(Currency.RON.name(), is("RON"));
        assertThat(Currency.RSD.name(), is("RSD"));
        assertThat(Currency.RUB.name(), is("RUB"));
        assertThat(Currency.RWF.name(), is("RWF"));
        assertThat(Currency.SAR.name(), is("SAR"));
        assertThat(Currency.SBD.name(), is("SBD"));
        assertThat(Currency.SCR.name(), is("SCR"));
        assertThat(Currency.SDG.name(), is("SDG"));
        assertThat(Currency.SEK.name(), is("SEK"));
        assertThat(Currency.SGD.name(), is("SGD"));
        assertThat(Currency.SHP.name(), is("SHP"));
        assertThat(Currency.SLL.name(), is("SLL"));
        assertThat(Currency.SOS.name(), is("SOS"));
        assertThat(Currency.SPL.name(), is("SPL"));
        assertThat(Currency.SRD.name(), is("SRD"));
        assertThat(Currency.STD.name(), is("STD"));
        assertThat(Currency.SVC.name(), is("SVC"));
        assertThat(Currency.SYP.name(), is("SYP"));
        assertThat(Currency.SZL.name(), is("SZL"));
        assertThat(Currency.THB.name(), is("THB"));
        assertThat(Currency.TJS.name(), is("TJS"));
        assertThat(Currency.TMT.name(), is("TMT"));
        assertThat(Currency.TND.name(), is("TND"));
        assertThat(Currency.TOP.name(), is("TOP"));
        assertThat(Currency.TRY.name(), is("TRY"));
        assertThat(Currency.TTD.name(), is("TTD"));
        assertThat(Currency.TVD.name(), is("TVD"));
        assertThat(Currency.TWD.name(), is("TWD"));
        assertThat(Currency.TZS.name(), is("TZS"));
        assertThat(Currency.UAH.name(), is("UAH"));
        assertThat(Currency.UGX.name(), is("UGX"));
        assertThat(Currency.USD.name(), is("USD"));
        assertThat(Currency.UYU.name(), is("UYU"));
        assertThat(Currency.UZS.name(), is("UZS"));
        assertThat(Currency.VEF.name(), is("VEF"));
        assertThat(Currency.VND.name(), is("VND"));
        assertThat(Currency.VUV.name(), is("VUV"));
        assertThat(Currency.WST.name(), is("WST"));
        assertThat(Currency.XAF.name(), is("XAF"));
        assertThat(Currency.XCD.name(), is("XCD"));
        assertThat(Currency.XDR.name(), is("XDR"));
        assertThat(Currency.XOF.name(), is("XOF"));
        assertThat(Currency.XPF.name(), is("XPF"));
        assertThat(Currency.YER.name(), is("YER"));
        assertThat(Currency.ZAR.name(), is("ZAR"));
        assertThat(Currency.ZMW.name(), is("ZMW"));
        assertThat(Currency.ZWD.name(), is("ZWD"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(Currency.AED.ordinal(), is(0));
        assertThat(Currency.AFN.ordinal(), is(1));
        assertThat(Currency.ALL.ordinal(), is(2));
        assertThat(Currency.AMD.ordinal(), is(3));
        assertThat(Currency.ANG.ordinal(), is(4));
        assertThat(Currency.AOA.ordinal(), is(5));
        assertThat(Currency.ARS.ordinal(), is(6));
        assertThat(Currency.AUD.ordinal(), is(7));
        assertThat(Currency.AWG.ordinal(), is(8));
        assertThat(Currency.AZN.ordinal(), is(9));
        assertThat(Currency.BAM.ordinal(), is(10));
        assertThat(Currency.BBD.ordinal(), is(11));
        assertThat(Currency.BDT.ordinal(), is(12));
        assertThat(Currency.BGN.ordinal(), is(13));
        assertThat(Currency.BHD.ordinal(), is(14));
        assertThat(Currency.BIF.ordinal(), is(15));
        assertThat(Currency.BMD.ordinal(), is(16));
        assertThat(Currency.BND.ordinal(), is(17));
        assertThat(Currency.BOB.ordinal(), is(18));
        assertThat(Currency.BRL.ordinal(), is(19));
        assertThat(Currency.BSD.ordinal(), is(20));
        assertThat(Currency.BTN.ordinal(), is(21));
        assertThat(Currency.BWP.ordinal(), is(22));
        assertThat(Currency.BYR.ordinal(), is(23));
        assertThat(Currency.BZD.ordinal(), is(24));
        assertThat(Currency.CAD.ordinal(), is(25));
        assertThat(Currency.CDF.ordinal(), is(26));
        assertThat(Currency.CHF.ordinal(), is(27));
        assertThat(Currency.CLP.ordinal(), is(28));
        assertThat(Currency.CNY.ordinal(), is(29));
        assertThat(Currency.COP.ordinal(), is(30));
        assertThat(Currency.CRC.ordinal(), is(31));
        assertThat(Currency.CUC.ordinal(), is(32));
        assertThat(Currency.CUP.ordinal(), is(33));
        assertThat(Currency.CVE.ordinal(), is(34));
        assertThat(Currency.CZK.ordinal(), is(35));
        assertThat(Currency.DJF.ordinal(), is(36));
        assertThat(Currency.DKK.ordinal(), is(37));
        assertThat(Currency.DOP.ordinal(), is(38));
        assertThat(Currency.DZD.ordinal(), is(39));
        assertThat(Currency.EGP.ordinal(), is(40));
        assertThat(Currency.ERN.ordinal(), is(41));
        assertThat(Currency.ETB.ordinal(), is(42));
        assertThat(Currency.EUR.ordinal(), is(43));
        assertThat(Currency.FJD.ordinal(), is(44));
        assertThat(Currency.FKP.ordinal(), is(45));
        assertThat(Currency.GBP.ordinal(), is(46));
        assertThat(Currency.GEL.ordinal(), is(47));
        assertThat(Currency.GGP.ordinal(), is(48));
        assertThat(Currency.GHS.ordinal(), is(49));
        assertThat(Currency.GIP.ordinal(), is(50));
        assertThat(Currency.GMD.ordinal(), is(51));
        assertThat(Currency.GNF.ordinal(), is(52));
        assertThat(Currency.GTQ.ordinal(), is(53));
        assertThat(Currency.GYD.ordinal(), is(54));
        assertThat(Currency.HKD.ordinal(), is(55));
        assertThat(Currency.HNL.ordinal(), is(56));
        assertThat(Currency.HRK.ordinal(), is(57));
        assertThat(Currency.HTG.ordinal(), is(58));
        assertThat(Currency.HUF.ordinal(), is(59));
        assertThat(Currency.IDR.ordinal(), is(60));
        assertThat(Currency.ILS.ordinal(), is(61));
        assertThat(Currency.IMP.ordinal(), is(62));
        assertThat(Currency.INR.ordinal(), is(63));
        assertThat(Currency.IQD.ordinal(), is(64));
        assertThat(Currency.IRR.ordinal(), is(65));
        assertThat(Currency.ISK.ordinal(), is(66));
        assertThat(Currency.JEP.ordinal(), is(67));
        assertThat(Currency.JMD.ordinal(), is(68));
        assertThat(Currency.JOD.ordinal(), is(69));
        assertThat(Currency.JPY.ordinal(), is(70));
        assertThat(Currency.KES.ordinal(), is(71));
        assertThat(Currency.KGS.ordinal(), is(72));
        assertThat(Currency.KHR.ordinal(), is(73));
        assertThat(Currency.KMF.ordinal(), is(74));
        assertThat(Currency.KPW.ordinal(), is(75));
        assertThat(Currency.KRW.ordinal(), is(76));
        assertThat(Currency.KWD.ordinal(), is(77));
        assertThat(Currency.KYD.ordinal(), is(78));
        assertThat(Currency.KZT.ordinal(), is(79));
        assertThat(Currency.LAK.ordinal(), is(80));
        assertThat(Currency.LBP.ordinal(), is(81));
        assertThat(Currency.LKR.ordinal(), is(82));
        assertThat(Currency.LRD.ordinal(), is(83));
        assertThat(Currency.LSL.ordinal(), is(84));
        assertThat(Currency.LTL.ordinal(), is(85));
        assertThat(Currency.LVL.ordinal(), is(86));
        assertThat(Currency.LYD.ordinal(), is(87));
        assertThat(Currency.MAD.ordinal(), is(88));
        assertThat(Currency.MDL.ordinal(), is(89));
        assertThat(Currency.MGA.ordinal(), is(90));
        assertThat(Currency.MKD.ordinal(), is(91));
        assertThat(Currency.MMK.ordinal(), is(92));
        assertThat(Currency.MNT.ordinal(), is(93));
        assertThat(Currency.MOP.ordinal(), is(94));
        assertThat(Currency.MRO.ordinal(), is(95));
        assertThat(Currency.MUR.ordinal(), is(96));
        assertThat(Currency.MVR.ordinal(), is(97));
        assertThat(Currency.MWK.ordinal(), is(98));
        assertThat(Currency.MXN.ordinal(), is(99));
        assertThat(Currency.MYR.ordinal(), is(100));
        assertThat(Currency.MZN.ordinal(), is(101));
        assertThat(Currency.NAD.ordinal(), is(102));
        assertThat(Currency.NGN.ordinal(), is(103));
        assertThat(Currency.NIO.ordinal(), is(104));
        assertThat(Currency.NOK.ordinal(), is(105));
        assertThat(Currency.NPR.ordinal(), is(106));
        assertThat(Currency.NZD.ordinal(), is(107));
        assertThat(Currency.OMR.ordinal(), is(108));
        assertThat(Currency.PAB.ordinal(), is(109));
        assertThat(Currency.PEN.ordinal(), is(110));
        assertThat(Currency.PGK.ordinal(), is(111));
        assertThat(Currency.PHP.ordinal(), is(112));
        assertThat(Currency.PKR.ordinal(), is(113));
        assertThat(Currency.PLN.ordinal(), is(114));
        assertThat(Currency.PYG.ordinal(), is(115));
        assertThat(Currency.QAR.ordinal(), is(116));
        assertThat(Currency.RON.ordinal(), is(117));
        assertThat(Currency.RSD.ordinal(), is(118));
        assertThat(Currency.RUB.ordinal(), is(119));
        assertThat(Currency.RWF.ordinal(), is(120));
        assertThat(Currency.SAR.ordinal(), is(121));
        assertThat(Currency.SBD.ordinal(), is(122));
        assertThat(Currency.SCR.ordinal(), is(123));
        assertThat(Currency.SDG.ordinal(), is(124));
        assertThat(Currency.SEK.ordinal(), is(125));
        assertThat(Currency.SGD.ordinal(), is(126));
        assertThat(Currency.SHP.ordinal(), is(127));
        assertThat(Currency.SLL.ordinal(), is(128));
        assertThat(Currency.SOS.ordinal(), is(129));
        assertThat(Currency.SPL.ordinal(), is(130));
        assertThat(Currency.SRD.ordinal(), is(131));
        assertThat(Currency.STD.ordinal(), is(132));
        assertThat(Currency.SVC.ordinal(), is(133));
        assertThat(Currency.SYP.ordinal(), is(134));
        assertThat(Currency.SZL.ordinal(), is(135));
        assertThat(Currency.THB.ordinal(), is(136));
        assertThat(Currency.TJS.ordinal(), is(137));
        assertThat(Currency.TMT.ordinal(), is(138));
        assertThat(Currency.TND.ordinal(), is(139));
        assertThat(Currency.TOP.ordinal(), is(140));
        assertThat(Currency.TRY.ordinal(), is(141));
        assertThat(Currency.TTD.ordinal(), is(142));
        assertThat(Currency.TVD.ordinal(), is(143));
        assertThat(Currency.TWD.ordinal(), is(144));
        assertThat(Currency.TZS.ordinal(), is(145));
        assertThat(Currency.UAH.ordinal(), is(146));
        assertThat(Currency.UGX.ordinal(), is(147));
        assertThat(Currency.USD.ordinal(), is(148));
        assertThat(Currency.UYU.ordinal(), is(149));
        assertThat(Currency.UZS.ordinal(), is(150));
        assertThat(Currency.VEF.ordinal(), is(151));
        assertThat(Currency.VND.ordinal(), is(152));
        assertThat(Currency.VUV.ordinal(), is(153));
        assertThat(Currency.WST.ordinal(), is(154));
        assertThat(Currency.XAF.ordinal(), is(155));
        assertThat(Currency.XCD.ordinal(), is(156));
        assertThat(Currency.XDR.ordinal(), is(157));
        assertThat(Currency.XOF.ordinal(), is(158));
        assertThat(Currency.XPF.ordinal(), is(159));
        assertThat(Currency.YER.ordinal(), is(160));
        assertThat(Currency.ZAR.ordinal(), is(161));
        assertThat(Currency.ZMW.ordinal(), is(162));
        assertThat(Currency.ZWD.ordinal(), is(163));

        // Check internal values
        assertThat(Currency.EUR.getDescription(), is("Euro Member Countries"));
        assertThat(Currency.EUR.getDecimals(), is(2));
    }

    @Test
    public void testFetchTypeEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(FetchType.DOMESTIC.name(), is("DOMESTIC"));
        assertThat(FetchType.SHARED.name(), is("SHARED"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(FetchType.DOMESTIC.ordinal(), is(0));
        assertThat(FetchType.SHARED.ordinal(), is(1));
    }

    @Test
    public void testGenderEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(Gender.MALE.name(), is("MALE"));
        assertThat(Gender.FEMALE.name(), is("FEMALE"));
        assertThat(Gender.UNKNOWN.name(), is("UNKNOWN"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(Gender.MALE.ordinal(), is(0));
        assertThat(Gender.FEMALE.ordinal(), is(1));
        assertThat(Gender.UNKNOWN.ordinal(), is(2));
    }

    @Test
    public void testGroupStatusEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(GroupStatus.ACTIVE.name(), is("ACTIVE"));
        assertThat(GroupStatus.SUSPENDED.name(), is("SUSPENDED"));
        assertThat(GroupStatus.DELETED.name(), is("DELETED"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(GroupStatus.ACTIVE.ordinal(), is(0));
        assertThat(GroupStatus.SUSPENDED.ordinal(), is(1));
        assertThat(GroupStatus.DELETED.ordinal(), is(2));
    }

    @Test
    public void testGroupTypeEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(GroupType.ADMINISTRATION.name(), is("ADMINISTRATION"));
        assertThat(GroupType.PRIVATE.name(), is("PRIVATE"));
        assertThat(GroupType.MEMBER.name(), is("MEMBER"));
        assertThat(GroupType.INTERNATIONAL.name(), is("INTERNATIONAL"));
        assertThat(GroupType.NATIONAL.name(), is("NATIONAL"));
        assertThat(GroupType.LOCAL.name(), is("LOCAL"));
        assertThat(GroupType.WORKGROUP.name(), is("WORKGROUP"));
        assertThat(GroupType.STUDENT.name(), is("STUDENT"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(GroupType.ADMINISTRATION.ordinal(), is(0));
        assertThat(GroupType.PRIVATE.ordinal(), is(1));
        assertThat(GroupType.MEMBER.ordinal(), is(2));
        assertThat(GroupType.INTERNATIONAL.ordinal(), is(3));
        assertThat(GroupType.NATIONAL.ordinal(), is(4));
        assertThat(GroupType.LOCAL.ordinal(), is(5));
        assertThat(GroupType.WORKGROUP.ordinal(), is(6));
        assertThat(GroupType.STUDENT.ordinal(), is(7));

        // Check internal values
        assertThat(GroupType.ADMINISTRATION.getWhoMayJoin(), is(GroupType.WhoMayJoin.ALL));
        assertThat(GroupType.ADMINISTRATION.getMayHavePrivateMailinglist(), is(true));
        assertThat(GroupType.ADMINISTRATION.getMayHavePublicMailinglist(), is(true));
        assertThat(GroupType.ADMINISTRATION.getFolderType(), is(GroupType.FolderType.PUBLIC));
    }

    @Test
    public void testInstitutionEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(Institution.ASSOCIATION.name(), is("ASSOCIATION"));
        assertThat(Institution.UNIVERSITY.name(), is("UNIVERSITY"));
        assertThat(Institution.NGO.name(), is("NGO"));
        assertThat(Institution.GOVERNMENT_INSTITUTIONS.name(), is("GOVERNMENT_INSTITUTIONS"));
        assertThat(Institution.OTHER.name(), is("OTHER"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(Institution.ASSOCIATION.ordinal(), is(0));
        assertThat(Institution.UNIVERSITY.ordinal(), is(1));
        assertThat(Institution.NGO.ordinal(), is(2));
        assertThat(Institution.GOVERNMENT_INSTITUTIONS.ordinal(), is(3));
        assertThat(Institution.OTHER.ordinal(), is(4));

        // Check the descriptable value
        assertThat(Institution.ASSOCIATION.getDescription(), is("Association"));
        assertThat(Institution.UNIVERSITY.getDescription(), is("University"));
        assertThat(Institution.NGO.getDescription(), is("NGO"));
        assertThat(Institution.GOVERNMENT_INSTITUTIONS.getDescription(), is("Government Institutions"));
        assertThat(Institution.OTHER.getDescription(), is("Other"));
    }

    @Test
    public void testLanguageEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(Language.ENGLISH.name(), is("ENGLISH"));
        assertThat(Language.ARABIC.name(), is("ARABIC"));
        assertThat(Language.BELARUSIAN.name(), is("BELARUSIAN"));
        assertThat(Language.BULGARIAN.name(), is("BULGARIAN"));
        assertThat(Language.CANTONESE_YUE_CHINESE.name(), is("CANTONESE_YUE_CHINESE"));
        assertThat(Language.CROATIAN.name(), is("CROATIAN"));
        assertThat(Language.CZECH.name(), is("CZECH"));
        assertThat(Language.DANISH.name(), is("DANISH"));
        assertThat(Language.DUTCH.name(), is("DUTCH"));
        assertThat(Language.ESTONIAN.name(), is("ESTONIAN"));
        assertThat(Language.FINNISH.name(), is("FINNISH"));
        assertThat(Language.FRENCH.name(), is("FRENCH"));
        assertThat(Language.GERMAN.name(), is("GERMAN"));
        assertThat(Language.GREEK.name(), is("GREEK"));
        assertThat(Language.HUNGARIAN.name(), is("HUNGARIAN"));
        assertThat(Language.INDONESIAN.name(), is("INDONESIAN"));
        assertThat(Language.ITALIAN.name(), is("ITALIAN"));
        assertThat(Language.JAPANESE.name(), is("JAPANESE"));
        assertThat(Language.KOREAN.name(), is("KOREAN"));
        assertThat(Language.LATVIAN.name(), is("LATVIAN"));
        assertThat(Language.LITHUANIAN.name(), is("LITHUANIAN"));
        assertThat(Language.MANDARIN_CHINESE.name(), is("MANDARIN_CHINESE"));
        assertThat(Language.NORWEGIAN.name(), is("NORWEGIAN"));
        assertThat(Language.PERSIAN.name(), is("PERSIAN"));
        assertThat(Language.POLISH.name(), is("POLISH"));
        assertThat(Language.PORTUGUESE.name(), is("PORTUGUESE"));
        assertThat(Language.ROMANIAN.name(), is("ROMANIAN"));
        assertThat(Language.RUSSIAN.name(), is("RUSSIAN"));
        assertThat(Language.SERBIAN.name(), is("SERBIAN"));
        assertThat(Language.SLOVAKIAN.name(), is("SLOVAKIAN"));
        assertThat(Language.SLOVENIAN.name(), is("SLOVENIAN"));
        assertThat(Language.SPANISH.name(), is("SPANISH"));
        assertThat(Language.SWEDISH.name(), is("SWEDISH"));
        assertThat(Language.TAJIK.name(), is("TAJIK"));
        assertThat(Language.THAI.name(), is("THAI"));
        assertThat(Language.TURKISH.name(), is("TURKISH"));
        assertThat(Language.VIETNAMESE.name(), is("VIETNAMESE"));
        assertThat(Language.UKRAINIAN.name(), is("UKRAINIAN"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(Language.ENGLISH.ordinal(), is(0));
        assertThat(Language.ARABIC.ordinal(), is(1));
        assertThat(Language.BELARUSIAN.ordinal(), is(2));
        assertThat(Language.BULGARIAN.ordinal(), is(3));
        assertThat(Language.CANTONESE_YUE_CHINESE.ordinal(), is(4));
        assertThat(Language.CROATIAN.ordinal(), is(5));
        assertThat(Language.CZECH.ordinal(), is(6));
        assertThat(Language.DANISH.ordinal(), is(7));
        assertThat(Language.DUTCH.ordinal(), is(8));
        assertThat(Language.ESTONIAN.ordinal(), is(9));
        assertThat(Language.FINNISH.ordinal(), is(10));
        assertThat(Language.FRENCH.ordinal(), is(11));
        assertThat(Language.GERMAN.ordinal(), is(12));
        assertThat(Language.GREEK.ordinal(), is(13));
        assertThat(Language.HUNGARIAN.ordinal(), is(14));
        assertThat(Language.INDONESIAN.ordinal(), is(15));
        assertThat(Language.ITALIAN.ordinal(), is(16));
        assertThat(Language.JAPANESE.ordinal(), is(17));
        assertThat(Language.KOREAN.ordinal(), is(18));
        assertThat(Language.LATVIAN.ordinal(), is(19));
        assertThat(Language.LITHUANIAN.ordinal(), is(20));
        assertThat(Language.MANDARIN_CHINESE.ordinal(), is(21));
        assertThat(Language.NORWEGIAN.ordinal(), is(22));
        assertThat(Language.PERSIAN.ordinal(), is(23));
        assertThat(Language.POLISH.ordinal(), is(24));
        assertThat(Language.PORTUGUESE.ordinal(), is(25));
        assertThat(Language.ROMANIAN.ordinal(), is(26));
        assertThat(Language.RUSSIAN.ordinal(), is(27));
        assertThat(Language.SERBIAN.ordinal(), is(28));
        assertThat(Language.SLOVAKIAN.ordinal(), is(29));
        assertThat(Language.SLOVENIAN.ordinal(), is(30));
        assertThat(Language.SPANISH.ordinal(), is(31));
        assertThat(Language.SWEDISH.ordinal(), is(32));
        assertThat(Language.TAJIK.ordinal(), is(33));
        assertThat(Language.THAI.ordinal(), is(34));
        assertThat(Language.TURKISH.ordinal(), is(35));
        assertThat(Language.VIETNAMESE.ordinal(), is(36));
        assertThat(Language.UKRAINIAN.ordinal(), is(37));
    }

    @Test
    public void testMailinglistTypeEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(MailinglistType.PRIVATE_LIST.name(), is("PRIVATE_LIST"));
        assertThat(MailinglistType.PUBLIC_LIST.name(), is("PUBLIC_LIST"));
        assertThat(MailinglistType.LIMITED_ALIAS.name(), is("LIMITED_ALIAS"));
        assertThat(MailinglistType.PERSONAL_ALIAS.name(), is("PERSONAL_ALIAS"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(MailinglistType.PRIVATE_LIST.ordinal(), is(0));
        assertThat(MailinglistType.PUBLIC_LIST.ordinal(), is(1));
        assertThat(MailinglistType.LIMITED_ALIAS.ordinal(), is(2));
        assertThat(MailinglistType.PERSONAL_ALIAS.ordinal(), is(3));
    }

    @Test
    public void testMailReplyEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(MailReply.REPLY_TO_SENDER.name(), is("REPLY_TO_SENDER"));
        assertThat(MailReply.REPLY_TO_LIST.name(), is("REPLY_TO_LIST"));
        assertThat(MailReply.NO_REPLY.name(), is("NO_REPLY"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(MailReply.REPLY_TO_SENDER.ordinal(), is(0));
        assertThat(MailReply.REPLY_TO_LIST.ordinal(), is(1));
        assertThat(MailReply.NO_REPLY.ordinal(), is(2));

        // Check the descriptable value
        assertThat(MailReply.REPLY_TO_SENDER.getDescription(), is("The Sender is receiving replies."));
        assertThat(MailReply.REPLY_TO_LIST.getDescription(), is("The List is receiving replies."));
        assertThat(MailReply.NO_REPLY.getDescription(), is("Replying is not possible."));
    }

    @Test
    public void testMembershipEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(Membership.UNKNOWN.name(), is("UNKNOWN"));
        assertThat(Membership.FULL_MEMBER.name(), is("FULL_MEMBER"));
        assertThat(Membership.ASSOCIATE_MEMBER.name(), is("ASSOCIATE_MEMBER"));
        assertThat(Membership.COOPERATING_INSTITUTION.name(), is("COOPERATING_INSTITUTION"));
        assertThat(Membership.FORMER_MEMBER.name(), is("FORMER_MEMBER"));
        assertThat(Membership.LISTED.name(), is("LISTED"));
        assertThat(Membership.UNLISTED.name(), is("UNLISTED"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(Membership.UNKNOWN.ordinal(), is(0));
        assertThat(Membership.FULL_MEMBER.ordinal(), is(1));
        assertThat(Membership.ASSOCIATE_MEMBER.ordinal(), is(2));
        assertThat(Membership.COOPERATING_INSTITUTION.ordinal(), is(3));
        assertThat(Membership.FORMER_MEMBER.ordinal(), is(4));
        assertThat(Membership.LISTED.ordinal(), is(5));
        assertThat(Membership.UNLISTED.ordinal(), is(6));
    }

    @Test
    public void testMonitoringLevelEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(MonitoringLevel.NONE.name(), is("NONE"));
        assertThat(MonitoringLevel.MARKED.name(), is("MARKED"));
        assertThat(MonitoringLevel.DETAILED.name(), is("DETAILED"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(MonitoringLevel.NONE.ordinal(), is(0));
        assertThat(MonitoringLevel.MARKED.ordinal(), is(1));
        assertThat(MonitoringLevel.DETAILED.ordinal(), is(2));
    }

    @Test
    public void testNotificationDeliveryModeEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(NotificationDeliveryMode.EMAIL.name(), is("EMAIL"));
        assertThat(NotificationDeliveryMode.IM.name(), is("IM"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(NotificationDeliveryMode.EMAIL.ordinal(), is(0));
        assertThat(NotificationDeliveryMode.IM.ordinal(), is(1));
    }

    @Test
    public void testNotificationFrequencyEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(NotificationFrequency.IMMEDIATELY.name(), is("IMMEDIATELY"));
        assertThat(NotificationFrequency.DAILY.name(), is("DAILY"));
        assertThat(NotificationFrequency.WEEKLY.name(), is("WEEKLY"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(NotificationFrequency.IMMEDIATELY.ordinal(), is(0));
        assertThat(NotificationFrequency.DAILY.ordinal(), is(1));
        assertThat(NotificationFrequency.WEEKLY.ordinal(), is(2));
    }

    @Test
    public void testNotificationMessageStatusEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(NotificationMessageStatus.NEW.name(), is("NEW"));
        assertThat(NotificationMessageStatus.PROCESSING.name(), is("PROCESSING"));
        assertThat(NotificationMessageStatus.SENT.name(), is("SENT"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(NotificationMessageStatus.NEW.ordinal(), is(0));
        assertThat(NotificationMessageStatus.PROCESSING.ordinal(), is(1));
        assertThat(NotificationMessageStatus.SENT.ordinal(), is(2));
    }

    @Test
    public void testNotificationSubjectEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(NotificationSubject.OFFER.name(), is("OFFER"));
        assertThat(NotificationSubject.GROUP.name(), is("GROUP"));
        assertThat(NotificationSubject.USER.name(), is("USER"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(NotificationSubject.OFFER.ordinal(), is(0));
        assertThat(NotificationSubject.GROUP.ordinal(), is(1));
        assertThat(NotificationSubject.USER.ordinal(), is(2));
    }

    @Test
    public void testPermissionIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(Permission.FETCH_COUNTRIES.name(), is("FETCH_COUNTRIES"));
        assertThat(Permission.PROCESS_COUNTRY.name(), is("PROCESS_COUNTRY"));
        assertThat(Permission.CONTROL_USER_ACCOUNT.name(), is("CONTROL_USER_ACCOUNT"));
        assertThat(Permission.CREATE_STUDENT_ACCOUNT.name(), is("CREATE_STUDENT_ACCOUNT"));
        assertThat(Permission.FETCH_USER.name(), is("FETCH_USER"));
        assertThat(Permission.CHANGE_ACCOUNT_NAME.name(), is("CHANGE_ACCOUNT_NAME"));
        assertThat(Permission.PROCESS_ROLE.name(), is("PROCESS_ROLE"));
        assertThat(Permission.PROCESS_GROUP.name(), is("PROCESS_GROUP"));
        assertThat(Permission.CHANGE_GROUP_OWNER.name(), is("CHANGE_GROUP_OWNER"));
        assertThat(Permission.DELETE_GROUP.name(), is("DELETE_GROUP"));
        assertThat(Permission.PROCESS_USER_GROUP_ASSIGNMENT.name(), is("PROCESS_USER_GROUP_ASSIGNMENT"));
        assertThat(Permission.FETCH_EMERGENCY_LIST.name(), is("FETCH_EMERGENCY_LIST"));
        assertThat(Permission.FETCH_COMMITTEES.name(), is("FETCH_COMMITTEES"));
        assertThat(Permission.PROCESS_COMMITTEE.name(), is("PROCESS_COMMITTEE"));
        assertThat(Permission.FETCH_INTERNATIONAL_GROUPS.name(), is("FETCH_INTERNATIONAL_GROUPS"));
        assertThat(Permission.PROCESS_INTERNATIONAL_GROUP.name(), is("PROCESS_INTERNATIONAL_GROUP"));
        assertThat(Permission.FETCH_SURVEY_OF_COUNTRIES.name(), is("FETCH_SURVEY_OF_COUNTRIES"));
        assertThat(Permission.PROCESS_SURVEY_OF_COUNTRIES.name(), is("PROCESS_SURVEY_OF_COUNTRIES"));
        assertThat(Permission.PROCESS_FILE.name(), is("PROCESS_FILE"));
        assertThat(Permission.FETCH_FILE.name(), is("FETCH_FILE"));
        assertThat(Permission.PROCESS_FOLDER.name(), is("PROCESS_FOLDER"));
        assertThat(Permission.FETCH_FOLDER.name(), is("FETCH_FOLDER"));
        assertThat(Permission.FETCH_OFFER_STATISTICS.name(), is("FETCH_OFFER_STATISTICS"));
        assertThat(Permission.PROCESS_EMPLOYER.name(), is("PROCESS_EMPLOYER"));
        assertThat(Permission.FETCH_EMPLOYERS.name(), is("FETCH_EMPLOYERS"));
        assertThat(Permission.PROCESS_OFFER.name(), is("PROCESS_OFFER"));
        assertThat(Permission.FETCH_OFFERS.name(), is("FETCH_OFFERS"));
        assertThat(Permission.FETCH_GROUPS_FOR_SHARING.name(), is("FETCH_GROUPS_FOR_SHARING"));
        assertThat(Permission.PROCESS_OFFER_TEMPLATES.name(), is("PROCESS_OFFER_TEMPLATES"));
        assertThat(Permission.FETCH_OFFER_TEMPLATES.name(), is("FETCH_OFFER_TEMPLATES"));
        assertThat(Permission.PROCESS_PUBLISH_OFFER.name(), is("PROCESS_PUBLISH_OFFER"));
        assertThat(Permission.FETCH_PUBLISH_GROUPS.name(), is("FETCH_PUBLISH_GROUPS"));
        assertThat(Permission.APPLY_FOR_OPEN_OFFER.name(), is("APPLY_FOR_OPEN_OFFER"));
        assertThat(Permission.PROCESS_STUDENT.name(), is("PROCESS_STUDENT"));
        assertThat(Permission.FETCH_STUDENTS.name(), is("FETCH_STUDENTS"));
        assertThat(Permission.FETCH_STUDENT_APPLICATION.name(), is("FETCH_STUDENT_APPLICATION"));
        assertThat(Permission.PROCESS_STUDENT_APPLICATION.name(), is("PROCESS_STUDENT_APPLICATION"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(Permission.FETCH_COUNTRIES.ordinal(), is(0));
        assertThat(Permission.PROCESS_COUNTRY.ordinal(), is(1));
        assertThat(Permission.CONTROL_USER_ACCOUNT.ordinal(), is(2));
        assertThat(Permission.CREATE_STUDENT_ACCOUNT.ordinal(), is(3));
        assertThat(Permission.FETCH_USER.ordinal(), is(4));
        assertThat(Permission.CHANGE_ACCOUNT_NAME.ordinal(), is(5));
        assertThat(Permission.PROCESS_ROLE.ordinal(), is(6));
        assertThat(Permission.PROCESS_GROUP.ordinal(), is(7));
        assertThat(Permission.CHANGE_GROUP_OWNER.ordinal(), is(8));
        assertThat(Permission.DELETE_GROUP.ordinal(), is(9));
        assertThat(Permission.PROCESS_USER_GROUP_ASSIGNMENT.ordinal(), is(10));
        assertThat(Permission.FETCH_EMERGENCY_LIST.ordinal(), is(11));
        assertThat(Permission.FETCH_COMMITTEES.ordinal(), is(12));
        assertThat(Permission.PROCESS_COMMITTEE.ordinal(), is(13));
        assertThat(Permission.FETCH_INTERNATIONAL_GROUPS.ordinal(), is(14));
        assertThat(Permission.PROCESS_INTERNATIONAL_GROUP.ordinal(), is(15));
        assertThat(Permission.FETCH_SURVEY_OF_COUNTRIES.ordinal(), is(16));
        assertThat(Permission.PROCESS_SURVEY_OF_COUNTRIES.ordinal(), is(17));
        assertThat(Permission.PROCESS_FILE.ordinal(), is(18));
        assertThat(Permission.FETCH_FILE.ordinal(), is(19));
        assertThat(Permission.PROCESS_FOLDER.ordinal(), is(20));
        assertThat(Permission.FETCH_FOLDER.ordinal(), is(21));
        assertThat(Permission.FETCH_OFFER_STATISTICS.ordinal(), is(22));
        assertThat(Permission.PROCESS_EMPLOYER.ordinal(), is(23));
        assertThat(Permission.FETCH_EMPLOYERS.ordinal(), is(24));
        assertThat(Permission.PROCESS_OFFER.ordinal(), is(25));
        assertThat(Permission.FETCH_OFFERS.ordinal(), is(26));
        assertThat(Permission.FETCH_GROUPS_FOR_SHARING.ordinal(), is(27));
        assertThat(Permission.PROCESS_OFFER_TEMPLATES.ordinal(), is(28));
        assertThat(Permission.FETCH_OFFER_TEMPLATES.ordinal(), is(29));
        assertThat(Permission.PROCESS_PUBLISH_OFFER.ordinal(), is(30));
        assertThat(Permission.FETCH_PUBLISH_GROUPS.ordinal(), is(31));
        assertThat(Permission.APPLY_FOR_OPEN_OFFER.ordinal(), is(32));
        assertThat(Permission.PROCESS_STUDENT.ordinal(), is(33));
        assertThat(Permission.FETCH_STUDENTS.ordinal(), is(34));
        assertThat(Permission.FETCH_STUDENT_APPLICATION.ordinal(), is(35));
        assertThat(Permission.PROCESS_STUDENT_APPLICATION.ordinal(), is(36));

        // Check remainder functionality
        assertThat(Permission.FETCH_COUNTRIES.getName(), is("Fetch Countries"));
        assertThat(Permission.FETCH_COUNTRIES.getModule().getName(), is(Administration.class.getName()));
        assertThat(Permission.FETCH_COUNTRIES.isRestricted(), is(false));
        assertThat(Permission.FETCH_COUNTRIES.getRequests(), is(new String[]{ "fetchCountries" }));
    }

    @Test
    public void testPrivacyEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(Privacy.PUBLIC.name(), is("PUBLIC"));
        assertThat(Privacy.PROTECTED.name(), is("PROTECTED"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(Privacy.PUBLIC.ordinal(), is(0));
        assertThat(Privacy.PROTECTED.ordinal(), is(1));
    }

    @Test
    public void testSortingFieldEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(SortingField.CREATED.name(), is("CREATED"));
        assertThat(SortingField.NAME.name(), is("NAME"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(SortingField.CREATED.ordinal(), is(0));
        assertThat(SortingField.NAME.ordinal(), is(1));
    }

    @Test
    public void testSortingOrderEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(SortingOrder.ASC.name(), is("ASC"));
        assertThat(SortingOrder.DESC.name(), is("DESC"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(SortingOrder.ASC.ordinal(), is(0));
        assertThat(SortingOrder.DESC.ordinal(), is(1));
    }

    @Test
    public void testUserStatusEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(UserStatus.NEW.name(), is("NEW"));
        assertThat(UserStatus.ACTIVE.name(), is("ACTIVE"));
        assertThat(UserStatus.SUSPENDED.name(), is("SUSPENDED"));
        assertThat(UserStatus.DELETED.name(), is("DELETED"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(UserStatus.NEW.ordinal(), is(0));
        assertThat(UserStatus.ACTIVE.ordinal(), is(1));
        assertThat(UserStatus.SUSPENDED.ordinal(), is(2));
        assertThat(UserStatus.DELETED.ordinal(), is(3));
    }

    @Test
    public void testUserTypeEnumIsCorrect() {
        // Name value check, to ensure that they aren't renamed
        assertThat(UserType.VOLUNTEER.name(), is("VOLUNTEER"));
        assertThat(UserType.EMPLOYED.name(), is("EMPLOYED"));
        assertThat(UserType.STUDENT.name(), is("STUDENT"));
        assertThat(UserType.FUNCTIONAL.name(), is("FUNCTIONAL"));
        assertThat(UserType.UNKNOWN.name(), is("UNKNOWN"));

        // Ordinal value check, to ensure that the order hasn't changed
        assertThat(UserType.VOLUNTEER.ordinal(), is(0));
        assertThat(UserType.EMPLOYED.ordinal(), is(1));
        assertThat(UserType.STUDENT.ordinal(), is(2));
        assertThat(UserType.FUNCTIONAL.ordinal(), is(3));
        assertThat(UserType.UNKNOWN.ordinal(), is(4));
    }
}
