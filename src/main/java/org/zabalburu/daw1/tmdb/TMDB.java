/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.zabalburu.daw1.tmdb;

import javax.swing.UIManager;
import jiconfont.icons.google_material_design_icons.GoogleMaterialDesignIcons;
import jiconfont.swing.IconFontSwing;
import mdlaf.MaterialLookAndFeel;
import mdlaf.themes.JMarsDarkTheme;
import mdlaf.themes.MaterialOceanicTheme;
import org.zabalburu.daw1.tmdb.views.FrmLogin;

/**
 *
 * @author ichueca
 */
public class TMDB {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(new MaterialLookAndFeel(
                    new MaterialOceanicTheme()));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        IconFontSwing.register(GoogleMaterialDesignIcons.getIconFont());
        new FrmLogin().setVisible(true);
    }
}
