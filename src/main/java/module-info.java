/* doesn't work with source level 1.8:
module gestionemagazzino.mylibrary {
    requires javafx.controls;
    requires javafx.fxml;

    opens gestionemagazzino.mylibrary to javafx.fxml;
    exports gestionemagazzino.mylibrary;
}
*/
