/*
 * Copyright © 2007 Free Software Foundation, Inc. <http://fsf.org/>
 *
 * Everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not allowed.
 *
 * This version of the GNU Lesser General Public License incorporates the terms and conditions of version 3 of the GNU General Public License, supplemented by the additional permissions listed below.
 *
 * 0. Additional Definitions.
 * As used herein, “this License” refers to version 3 of the GNU Lesser General Public License, and the “GNU GPL” refers to version 3 of the GNU General Public License.
 *
 * “The Library” refers to a covered work governed by this License, other than an Application or a Combined Work as defined below.
 *
 * An “Application” is any work that makes use of an interface provided by the Library, but which is not otherwise based on the Library. Defining a subclass of a class defined by the Library is deemed a mode of using an interface provided by the Library.
 *
 * A “Combined Work” is a work produced by combining or linking an Application with the Library. The particular version of the Library with which the Combined Work was made is also called the “Linked Version”.
 *
 * The “Minimal Corresponding Source” for a Combined Work means the Corresponding Source for the Combined Work, excluding any source code for portions of the Combined Work that, considered in isolation, are based on the Application, and not on the Linked Version.
 *
 * The “Corresponding Application Code” for a Combined Work means the object code and/or source code for the Application, including any data and utility programs needed for reproducing the Combined Work from the Application, but excluding the System Libraries of the Combined Work.
 *
 * 1. Exception to Section 3 of the GNU GPL.
 * You may convey a covered work under sections 3 and 4 of this License without being bound by section 3 of the GNU GPL.
 *
 * 2. Conveying Modified Versions.
 * If you modify a copy of the Library, and, in your modifications, a facility refers to a function or data to be supplied by an Application that uses the facility (other than as an argument passed when the facility is invoked), then you may convey a copy of the modified version:
 *
 * a) under this License, provided that you make a good faith effort to ensure that, in the event an Application does not supply the function or data, the facility still operates, and performs whatever part of its purpose remains meaningful, or
 * b) under the GNU GPL, with none of the additional permissions of this License applicable to that copy.
 * 3. Object Code Incorporating Material from Library Header Files.
 * The object code form of an Application may incorporate material from a header file that is part of the Library. You may convey such object code under terms of your choice, provided that, if the incorporated material is not limited to numerical parameters, data structure layouts and accessors, or small macros, inline functions and templates (ten or fewer lines in length), you do both of the following:
 *
 * a) Give prominent notice with each copy of the object code that the Library is used in it and that the Library and its use are covered by this License.
 * b) Accompany the object code with a copy of the GNU GPL and this license document.
 * 4. Combined Works.
 * You may convey a Combined Work under terms of your choice that, taken together, effectively do not restrict modification of the portions of the Library contained in the Combined Work and reverse engineering for debugging such modifications, if you also do each of the following:
 *
 * a) Give prominent notice with each copy of the Combined Work that the Library is used in it and that the Library and its use are covered by this License.
 * b) Accompany the Combined Work with a copy of the GNU GPL and this license document.
 * c) For a Combined Work that displays copyright notices during execution, include the copyright notice for the Library among these notices, as well as a reference directing the user to the copies of the GNU GPL and this license document.
 * d) Do one of the following:
 * 0) Convey the Minimal Corresponding Source under the terms of this License, and the Corresponding Application Code in a form suitable for, and under terms that permit, the user to recombine or relink the Application with a modified version of the Linked Version to produce a modified Combined Work, in the manner specified by section 6 of the GNU GPL for conveying Corresponding Source.
 * 1) Use a suitable shared library mechanism for linking with the Library. A suitable mechanism is one that (a) uses at run time a copy of the Library already present on the user's computer system, and (b) will operate properly with a modified version of the Library that is interface-compatible with the Linked Version.
 * e) Provide Installation Information, but only if you would otherwise be required to provide such information under section 6 of the GNU GPL, and only to the extent that such information is necessary to install and execute a modified version of the Combined Work produced by recombining or relinking the Application with a modified version of the Linked Version. (If you use option 4d0, the Installation Information must accompany the Minimal Corresponding Source and Corresponding Application Code. If you use option 4d1, you must provide the Installation Information in the manner specified by section 6 of the GNU GPL for conveying Corresponding Source.)
 * 5. Combined Libraries.
 * You may place library facilities that are a work based on the Library side by side in a single library together with other library facilities that are not Applications and are not covered by this License, and convey such a combined library under terms of your choice, if you do both of the following:
 *
 * a) Accompany the combined library with a copy of the same work based on the Library, uncombined with any other library facilities, conveyed under the terms of this License.
 * b) Give prominent notice with the combined library that part of it is a work based on the Library, and explaining where to find the accompanying uncombined form of the same work.
 * 6. Revised Versions of the GNU Lesser General Public License.
 * The Free Software Foundation may publish revised and/or new versions of the GNU Lesser General Public License from time to time. Such new versions will be similar in spirit to the present version, but may differ in detail to address new problems or concerns.
 *
 * Each version is given a distinguishing version number. If the Library as you received it specifies that a certain numbered version of the GNU Lesser General Public License “or any later version” applies to it, you have the option of following the terms and conditions either of that published version or of any later version published by the Free Software Foundation. If the Library as you received it does not specify a version number of the GNU Lesser General Public License, you may choose any version of the GNU Lesser General Public License ever published by the Free Software Foundation.
 *
 * If the Library as you received it specifies that a proxy can decide whether future versions of the GNU Lesser General Public License shall apply, that proxy's public statement of acceptance of any version is permanent authorization for you to choose that version for the Library.
 */

package de.caluga.ergodox.macrodialog;

import de.caluga.ergodox.macros.TypeMacro;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * User: Stephan Bösebeck
 * Date: 11.04.16
 * Time: 23:06
 * <p>
 * TODO: Add documentation here
 */
public class MacroEditor {

    public void showEditor() {
        Stage macroEditor = new Stage();


        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(20);
        grid.setPadding(new Insets(20, 150, 10, 10));

        ToggleGroup tg = new ToggleGroup();
        RadioButton typeMacroRB = new RadioButton("Typing macro");
        typeMacroRB.setToggleGroup(tg);
        RadioButton longPMacroRB = new RadioButton("Long press type macro");
        longPMacroRB.setToggleGroup(tg);
        RadioButton holdKeyMacroRB = new RadioButton("Hold key macro");
        holdKeyMacroRB.setToggleGroup(tg);
        RadioButton customMacro = new RadioButton("Custom macro");
        customMacro.setToggleGroup(tg);
        GridPane content = new GridPane();
        content.setHgap(10);
        content.setVgap(10);

        FlowPane p = new FlowPane(10, 10);
        p.getChildren().add(typeMacroRB);
        p.getChildren().add(longPMacroRB);
        p.getChildren().add(holdKeyMacroRB);
        p.getChildren().add(customMacro);

        typeMacroRB.setSelected(true);
        showTypeMacro(content);

        typeMacroRB.addEventHandler(ActionEvent.ACTION, event -> {
            showTypeMacro(content);
            content.requestLayout();
            p.requestLayout();
        });

        longPMacroRB.addEventHandler(ActionEvent.ACTION, event -> {
            showLongPressMacro(content);
        });

        holdKeyMacroRB.addEventHandler(ActionEvent.ACTION, event -> {
            showHoldKeyMacro(content);
        });

        customMacro.addEventHandler(ActionEvent.ACTION, event -> {
            showCustomMacro(content);
        });

        TextField nameTF = new TextField();
        grid.add(new Label("Macro name:"), 0, 0);
        grid.add(nameTF, 1, 0);
        grid.add(new Label("Type:"), 0, 1);
        grid.add(p, 1, 1);
        grid.add(content, 0, 2, 2, 1);


        FlowPane fp = new FlowPane();
        Button ok = new Button("OK");
        Button cancel = new Button("cancel");
        fp.getChildren().add(ok);
        fp.getChildren().add(cancel);

        grid.add(fp, 1, 3);


//        grid.add(new Label("Key typed:"), 0, 2);
//        grid.add(keyCBX, 1, 2);
//        grid.add(new Label("Layer when held:"), 0, 1);
//        grid.add(layerCBX, 1, 1);

// Enable/Disable login button depending on whether a username was entered.
        Scene scene = new Scene(grid, 600, 300);

        macroEditor.setTitle("MacroEditor");
        macroEditor.setScene(scene);
        macroEditor.show();

        cancel.addEventHandler(ActionEvent.ACTION, event -> {
            macroEditor.close();
        });
        ok.addEventHandler(ActionEvent.ACTION, event -> {
            TypeMacro tm = new TypeMacro();

            macroEditor.close();
        });



// Request focus on the username field by default.
//        Platform.runLater(() -> keyCBX.requestFocus());


    }

    private void showCustomMacro(GridPane content) {
        content.getChildren().clear();
        TextArea txt = new TextArea();
        content.add(new Label("Custom macro content"), 0, 0);
        content.add(txt, 0, 1);
    }

    private void showHoldKeyMacro(GridPane content) {

    }

    private void showLongPressMacro(GridPane content) {

    }

    private void showTypeMacro(GridPane content) {
        content.getChildren().clear();
        content.add(new Label("Type macro:"), 0, 0);
        TextField tf = new TextField("");
        tf.setPrefWidth(500);
        content.add(tf, 0, 1);
        Button btn = new Button("Convert text to macro");
        content.add(btn, 0, 2);
        btn.addEventHandler(ActionEvent.ACTION, event -> {
            String txt = tf.getText();
            String newTxt = macronifyString(txt);
            tf.setText(newTxt);
        });
        content.add(new Label("Description: Keycodes in Macros do not have the KC_PREFIX, hence only KC_KEYCODES work\nD(KEYCODE) - DOWN key\nU(KEYCODE) - Release key\nT(KEYCODE) - Type key\nW(MILLIES) - wait milliseconds"), 0, 3, 2, 1);


    }

    private String macronifyString(String txt) {
        StringBuilder b = new StringBuilder();
        boolean shift = false;
        for (int i = 0; i < txt.length(); i++) {
            char c = txt.charAt(i);
            if (Character.isAlphabetic(c) && Character.isUpperCase(c)) {
                if (!shift) {
                    b.append("D(LSFT),");
                }
                b.append("T(").append(Character.toString(c)).append(")");
                shift = true;
            } else if (Character.isAlphabetic(c) && !Character.isUpperCase(c)) {
                if (shift) {
                    b.append("U(LSFT),");
                }
                b.append("T(").append(Character.toString(c)).append(")");
                shift = false;
            } else if (Character.isDigit(c)) {
                if (shift) {
                    b.append("U(LSFT),");
                }
                shift = false;
                b.append("T(" + Character.toString(c) + ")");
            } else if (Character.isSpaceChar(c)) {
                if (shift) {
                    b.append("U(LSFT),");
                }
                shift = false;
                b.append("T(SPC)");
            } else {
                if (shift) {
                    b.append("U(LSFT),");
                }
                shift = false;
                switch (c) {
                    case '!':
                        b.append("T(EXLM)");
                        break;
                    case ':':
                        b.append("T(COLN)");
                        break;
                    case '#':
                        b.append("T(HASH)");
                        break;
                    case ';':
                        b.append("T(SCLN)");
                        break;
                    case '$':
                        b.append("T(DLR)");
                        break;
                    case '-':
                        b.append("T(MINS)");
                        break;
                    case '(':
                        b.append("T(LBRC)");
                        break;
                    case ')':
                        b.append("T(RBRC)");
                        break;
                    case '{':
                        b.append("T(LCBRK)");
                        break;
                    case '}':
                        b.append("T(RCBRK)");
                        break;
                    default:
                        b.append("?");
                        b.append(Character.toString(c));
                        b.append("?");
                }
            }
            b.append(",");
        }
        b.setLength(b.length() - 1);
        return b.toString();
    }
}
