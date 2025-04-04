package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.base.WindowMatchers;
import org.testfx.matcher.control.LabeledMatchers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Labeled;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import main.Main;

@ExtendWith(ApplicationExtension.class)

public class TestJava {

    @Start
    public void start(Stage stage) throws IOException {

    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FrameLoggin.fxml"));
        Pane root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        stage.toFront();

    }

    @Test
    public void testTexto(FxRobot robot) {
        robot.clickOn("#txtGmail");
        robot.write("ej prueba pasada");

        TextField textField = robot.lookup("#txtGmail").queryAs(TextField.class);
        assertEquals("ej prueba pasada", textField.getText());
    }

    @Test
    public void testCambioRegister(FxRobot robot) {
        robot.clickOn("#btnRegistro");
        FxAssert.verifyThat(robot.window("Registro"), WindowMatchers.isShowing());
    }

    @Test
    public void testLoginTrue(FxRobot robot) {
        robot.clickOn("#txtGmail").write("ej");
        robot.clickOn("#txtContrasena").write("1234");
        robot.clickOn("#btnLoggin");

        robot.lookup(".dialog-pane").tryQuery().ifPresent(dialogPane -> {
            DialogPane pane = (DialogPane) dialogPane;
            FxAssert.verifyThat(pane.getContentText(), LabeledMatchers.hasText("Usuario no encontrado"));

            robot.clickOn(pane.lookupButton(ButtonType.OK));
        });
    }
    /**
    @Test
    public void testRegistroVacioError(FxRobot robot) {
        robot.clickOn("#buttonRegister");
        robot.clickOn("#btnRegistro");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane, "El cuadro de diálogo de error no apareció");
        String mensajeError = ((Labeled) dialogPane.lookup(".content")).getText();
        Assertions.assertThat(mensajeError).isEqualTo("Por favor, complete todos los campos.");
        robot.clickOn(dialogPane.lookupButton(ButtonType.OK));
    }
    
    @Test
    public void testRegistroContrasenaIncorrecta(FxRobot robot) {
        robot.clickOn("#buttonRegister");
        robot.clickOn("#nombreId").write("Juan");
        robot.clickOn("#ApellidosId").write("Pérez");
        robot.clickOn("#FechaNacId").write("04/10/2002");

        robot.clickOn("#nicknameId").write("juanperez95");

        robot.clickOn("#emailId").write("juan.perez@example.com");
        robot.clickOn("#confirmarEmailId").write("juan.perez@example.com"); 

        robot.clickOn("#contraseñaId").write("1234");
        robot.clickOn("#confirmarContraseñaId").write("12"); 
        
        robot.clickOn("#btnRegistro");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane, "El cuadro de diálogo de error no apareció");
        String mensajeError = ((Labeled) dialogPane.lookup(".content")).getText();
        Assertions.assertThat(mensajeError).isEqualTo("Las contraseñas no coinciden.");
        robot.clickOn(dialogPane.lookupButton(ButtonType.OK));
    }
    
    
    @Test
    public void testRegistroCorrecto(FxRobot robot) {
        robot.clickOn("#buttonRegister");
        robot.clickOn("#nombreId").write("Juan");
        robot.clickOn("#ApellidosId").write("Pérez");
        robot.clickOn("#FechaNacId").write("04/10/2002");

        robot.clickOn("#nicknameId").write("juanperez95");

        robot.clickOn("#emailId").write("juan.perez@example.com");
        robot.clickOn("#confirmarEmailId").write("juan.perez@example.com"); 

        robot.clickOn("#contraseñaId").write("1234");
        robot.clickOn("#confirmarContraseñaId").write("1234"); 
        
        robot.clickOn("#btnRegistro");

        DialogPane dialogPane = robot.lookup(".dialog-pane").queryAs(DialogPane.class);
        assertNotNull(dialogPane, "El cuadro de diálogo de error no apareció");
        String mensajeError = ((Labeled) dialogPane.lookup(".content")).getText();
        Assertions.assertThat(mensajeError).isEqualTo("Se ha registrado el usuario Juan");
        robot.clickOn(dialogPane.lookupButton(ButtonType.OK));
    }
*/
}