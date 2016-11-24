package com.egeniq.state.search;

import java.io.Serializable;
import java.util.Random;

/**
 * Created by andrei on 24/11/16.
 */

public class SearchData implements Serializable {
    private String mTitle;
    private String mDescription;
    private int mCount;

    public SearchData(Random random) {
        mTitle = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.";
        mDescription = "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.";
        mCount = random.nextInt(99);
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public int getCount() {
        return mCount;
    }

    /* if needed, optimise
    public void readExternal(ObjectInput stream) throws IOException, ClassNotFoundException {
       ...
    }

    public void writeExternal(ObjectOutput stream) throws IOException {
        ...
    }
    */
}
