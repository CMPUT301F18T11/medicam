package ca.ualberta.cmput301f18t11.medicam;

import java.util.UUID;
import io.searchbox.annotations.JestId;

public class Foo {

    @JestId
    private String UUID;



    public String message;

    public Foo(String m, String UUID)
    {
        this.UUID = UUID;
        this.message = m;
    }


}
