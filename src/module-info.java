module Restaurent.managment.system {
    requires javafx.fxml;
    requires javafx.controls;
    requires java.sql;

    opens sample;
    opens home;
    opens changePassword;
    opens forgetPassword;
    opens menu;
    opens user;
    opens employees;
    opens costumers;
    opens reservations;
    opens orders;
}