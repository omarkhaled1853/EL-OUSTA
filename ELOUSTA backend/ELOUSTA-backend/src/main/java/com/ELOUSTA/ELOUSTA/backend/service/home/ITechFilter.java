package com.ELOUSTA.ELOUSTA.backend.service.home;

import com.example.Backend.classes.Technician;

import java.util.ArrayList;

public interface ITechFilter {
ArrayList<Technician>Filter(final String query,final ArrayList<Technician> technicians);

}
