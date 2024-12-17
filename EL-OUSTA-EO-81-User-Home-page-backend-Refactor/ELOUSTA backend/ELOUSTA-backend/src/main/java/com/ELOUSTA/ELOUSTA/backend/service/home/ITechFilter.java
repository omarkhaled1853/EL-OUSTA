package com.ELOUSTA.ELOUSTA.backend.service.home;

import com.ELOUSTA.ELOUSTA.backend.model.Technician;

import java.util.ArrayList;

public interface ITechFilter {
    ArrayList<Technician>Filter(final String query, final ArrayList<Technician> technicians);
}
