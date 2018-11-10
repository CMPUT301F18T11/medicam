package ca.ualberta.cmput301f18t11.medicam.controllers;

import io.searchbox.annotations.JestId;

// Example Class
public class Bar {

    @JestId
    private String UUID;



    public String message;
    public int x;

    public Bar(String m, String UUID)
    {
        this.UUID = UUID;
        this.message = m;
    }

}
