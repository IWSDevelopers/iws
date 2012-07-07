/*
 * =============================================================================
 * Copyright 1998-2012, IAESTE Internet Development Team. All rights reserved.
 * -----------------------------------------------------------------------------
 * Project: IntraWeb Services (iws-fe) - net.iaeste.iws.fe.model.base.Theme
 * -----------------------------------------------------------------------------
 * This software is provided by the members of the IAESTE Internet Development
 * Team (IDT) to IAESTE A.s.b.l. It is for internal use only and may not be
 * redistributed. IAESTE A.s.b.l. is not permitted to sell this software.
 *
 * This software is provided "as is"; the IDT or individuals within the IDT
 * cannot be held legally responsible for any problems the software may cause.
 * =============================================================================
 */
package net.iaeste.iws.fe.model.base;

import java.util.HashMap;
import java.util.Map;

/**
 * Provides all available Themes
 *
 * @author Matej Kosco / last $Author:$
 * @version $Revision:$ / $Date:$
 * @since 1.7
 */
public enum Theme {

    AFTERDARK("afterdark"),
    AFTERNOON("afternoon"),
    AFTERWORK("afterwork"),
    ARISTO("aristo"),
    BLACK_TIE("black-tie"),
    BLITZER("blitzer"),
    BLUESKY("bluesky"),
    CASABLANCA("casablanca"),
    CRUZE("cruze"),
    CUPERTINO("cupertino"),
    DARK_HIVE("dark-hive"),
    DOT_LUV("dot-luv"),
    EGGPLANT("eggplant"),
    EXCITE_BIKE("excite-bike"),
    FLICK("flick"),
    GLASS_X("glass-x"),
    HOME("home"),
    HOT_SNEAKS("hot-sneaks"),
    HUMANITY("humanity"),
    LE_FROG("le-frog"),
    MIDNIGHt("midnight"),
    MINT_CHOC("mint-choc"),
    OVERCAST("overcast"),
    PEPPER_GRINDER("pepper-grinder"),
    REDMOND("redmond"),
    ROCKET("rocket"),
    SAM("sam"),
    SMOOTHNESS("smoothness"),
    SOUTH_STREET("south-street"),
    START("start"),
    SUNNY("sunny"),
    SWANKY_PURSE("swanky-purse"),
    TRONTASTIC("trontastic"),
    UI_DARKNESS("ui-darkness"),
    UI_LIGHTNESS("ui-lightness"),
    VADER("vader");

    private final String code;

    private Theme(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Theme fromCode(String code) {
        for (Theme t : values()) {
            if (t.getCode().equals(code)) {
                return t;
            }
        }
        // TODO use IWSException
        throw new RuntimeException("No Theme for code " + code);
    }

    public static Map<String, String> getThemeMap() {
        Map<String, String> themes = new HashMap<String, String>();
        for (Theme t : values()) {
            themes.put(t.name(), t.getCode());
        }
        return themes;
    }
}
