package interfaces;

import classes.Technician;

import java.util.ArrayList;

public interface ITechFilter {
ArrayList<Technician>Filter(final String query,final ArrayList<Technician> technicians);

}
