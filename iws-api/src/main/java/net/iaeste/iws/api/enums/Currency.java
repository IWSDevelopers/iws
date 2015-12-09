/*
 * =============================================================================
 * Copyright 1998-2015, IAESTE Internet Development Team. All rights reserved.
 * ----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-api) - net.iaeste.iws.api.enums.Currency
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

import javax.xml.bind.annotation.XmlType;

/**
*  All currencies that can be used in the system
*
* @author  Marko Cilimkovic / last $Author:$
* @version $Revision:$ / $Date:$
* @since   IWS 1.0
*/
@XmlType(name = "currency")
public enum Currency implements Descriptable<Currency> {

    AED("United Arab Emirates Dirham", 2),
    AFN("Afghanistan Afghani", 2),
    ALL("Albania Lek", 2),
    AMD("Armenia Dram", 2),
    ANG("Netherlands Antilles Guilder", 2),
    AOA("Angola Kwanza", 2),
    ARS("Argentina Peso", 2),
    AUD("Australia Dollar", 2),
    AWG("Aruba Guilder", 2),
    AZN("Azerbaijan New Manat", 2),
    BAM("Bosnia and Herzegovina Convertible Marka", 2),
    BBD("Barbados Dollar", 2),
    BDT("Bangladesh Taka", 2),
    BGN("Bulgaria Lev", 2),
    BHD("Bahrain Dinar", 2),
    BIF("Burundi Franc", 2),
    BMD("Bermuda Dollar", 2),
    BND("Brunei Darussalam Dollar", 2),
    BOB("Bolivia Boliviano", 2),
    BRL("Brazil Real", 2),
    BSD("Bahamas Dollar", 2),
    BTN("Bhutan Ngultrum", 2),
    BWP("Botswana Pula", 2),
    BYR("Belarus Ruble", 2),
    BZD("Belize Dollar", 2),
    CAD("Canada Dollar", 2),
    CDF("Congo/Kinshasa Franc", 2),
    CHF("Switzerland Franc", 2),
    CLP("Chile Peso", 2),
    CNY("China Yuan Renminbi", 2),
    COP("Colombia Peso", 2),
    CRC("Costa Rica Colon", 2),
    CUC("Cuba Convertible Peso", 2),
    CUP("Cuba Peso", 2),
    CVE("Cape Verde Escudo", 2),
    CZK("Czech Republic Koruna", 2),
    DJF("Djibouti Franc", 2),
    DKK("Denmark Krone", 2),
    DOP("Dominican Republic Peso", 2),
    DZD("Algeria Dinar", 2),
    EGP("Egypt Pound", 2),
    ERN("Eritrea Nakfa", 2),
    ETB("Ethiopia Birr", 2),
    EUR("Euro Member Countries", 2),
    FJD("Fiji Dollar", 2),
    FKP("Falkland Islands (Malvinas) Pound", 2),
    GBP("United Kingdom Pound", 2),
    GEL("Georgia Lari", 2),
    GGP("Guernsey Pound", 2),
    GHS("Ghana Cedi", 2),
    GIP("Gibraltar Pound", 2),
    GMD("Gambia Dalasi", 2),
    GNF("Guinea Franc", 2),
    GTQ("Guatemala Quetzal", 2),
    GYD("Guyana Dollar", 2),
    HKD("Hong Kong Dollar", 2),
    HNL("Honduras Lempira", 2),
    HRK("Croatia Kuna", 2),
    HTG("Haiti Gourde", 2),
    HUF("Hungary Forint", 2),
    IDR("Indonesia Rupiah", 2),
    ILS("Israel Shekel", 2),
    IMP("Isle of Man Pound", 2),
    INR("India Rupee", 2),
    IQD("Iraq Dinar", 2),
    IRR("Iran Rial", 2),
    ISK("Iceland Krona", 2),
    JEP("Jersey Pound", 2),
    JMD("Jamaica Dollar", 2),
    JOD("Jordan Dinar", 2),
    JPY("Japan Yen", 0),
    KES("Kenya Shilling", 2),
    KGS("Kyrgyzstan Som", 2),
    KHR("Cambodia Riel", 2),
    KMF("Comoros Franc", 2),
    KPW("Korea (North) Won", 2),
    KRW("Korea (South) Won", 2),
    KWD("Kuwait Dinar", 2),
    KYD("Cayman Islands Dollar", 2),
    KZT("Kazakhstan Tenge", 2),
    LAK("Laos Kip", 2),
    LBP("Lebanon Pound", 2),
    LKR("Sri Lanka Rupee", 2),
    LRD("Liberia Dollar", 2),
    LSL("Lesotho Loti", 2),
    LTL("Lithuania Litas", 2),
    LVL("Latvia Lat", 2),
    LYD("Libya Dinar", 2),
    MAD("Morocco Dirham", 2),
    MDL("Moldova Leu", 2),
    MGA("Madagascar Ariary", 2),
    MKD("Macedonia Denar", 2),
    MMK("Myanmar (Burma) Kyat", 2),
    MNT("Mongolia Tughrik", 2),
    MOP("Macau Pataca", 2),
    MRO("Mauritania Ouguiya", 2),
    MUR("Mauritius Rupee", 2),
    MVR("Maldives (Maldive Islands) Rufiyaa", 2),
    MWK("Malawi Kwacha", 2),
    MXN("Mexico Peso", 2),
    MYR("Malaysia Ringgit", 2),
    MZN("Mozambique Metical", 2),
    NAD("Namibia Dollar", 2),
    NGN("Nigeria Naira", 2),
    NIO("Nicaragua Cordoba", 2),
    NOK("Norway Krone", 2),
    NPR("Nepal Rupee", 2),
    NZD("New Zealand Dollar", 2),
    OMR("Oman Rial", 2),
    PAB("Panama Balboa", 2),
    PEN("Peru Nuevo Sol", 2),
    PGK("Papua New Guinea Kina", 2),
    PHP("Philippines Peso", 2),
    PKR("Pakistan Rupee", 2),
    PLN("Poland Zloty", 2),
    PYG("Paraguay Guarani", 2),
    QAR("Qatar Riyal", 2),
    RON("Romania New Leu", 2),
    RSD("Serbia Dinar", 2),
    RUB("Russia Ruble", 2),
    RWF("Rwanda Franc", 2),
    SAR("Saudi Arabia Riyal", 2),
    SBD("Solomon Islands Dollar", 2),
    SCR("Seychelles Rupee", 2),
    SDG("Sudan Pound", 2),
    SEK("Sweden Krona", 2),
    SGD("Singapore Dollar", 2),
    SHP("Saint Helena Pound", 2),
    SLL("Sierra Leone Leone", 2),
    SOS("Somalia Shilling", 2),
    SPL("Seborga Luigino", 2),
    SRD("Suriname Dollar", 2),
    STD("São Tomé and Príncipe Dobra", 2),
    SVC("El Salvador Colon", 2),
    SYP("Syria Pound", 2),
    SZL("Swaziland Lilangeni", 2),
    THB("Thailand Baht", 2),
    TJS("Tajikistan Somoni", 2),
    TMT("Turkmenistan Manat", 2),
    TND("Tunisia Dinar", 2),
    TOP("Tonga Pa'anga", 2),
    TRY("Turkey Lira", 2),
    TTD("Trinidad and Tobago Dollar", 2),
    TVD("Tuvalu Dollar", 2),
    TWD("Taiwan New Dollar", 2),
    TZS("Tanzania Shilling", 2),
    UAH("Ukraine Hryvna", 2),
    UGX("Uganda Shilling", 2),
    USD("United States Dollar", 2),
    UYU("Uruguay Peso", 2),
    UZS("Uzbekistan Som", 2),
    VEF("Venezuela Bolivar", 2),
    VND("Viet Nam Dong", 2),
    VUV("Vanuatu Vatu", 2),
    WST("Samoa Tala", 2),
    XAF("Communauté Financière Africaine (BEAC) CFA Franc BEAC", 2),
    XCD("East Caribbean Dollar", 2),
    XDR("International Monetary Fund (IMF) Special Drawing Rights", 2),
    XOF("Communauté Financière Africaine (BCEAO) Franc", 2),
    XPF("Comptoirs Français du Pacifique (CFP) Franc", 2),
    YER("Yemen Rial", 2),
    ZAR("South Africa Rand", 2),
    ZMW("Zambia Kwacha", 2),
    ZWD("Zimbabwe Dollar", 2);

    // =========================================================================
    // Private Constructor & functionality
    // =========================================================================

    private final String description;
    private final int decimals;

    Currency(final String description, final int decimals) {
        this.description = description;
        this.decimals = decimals;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDescription() {
        return description;
    }

    public int getDecimals() {
        return decimals;
    }
}
