package com.sonu.diary.remote;


import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class UnixEpochDateTypeAdapter
        extends TypeAdapter<Date> {

    private static final TypeAdapter<Date> unixEpochDateTypeAdapter = new UnixEpochDateTypeAdapter();

    private UnixEpochDateTypeAdapter() {
    }

    public static TypeAdapter<Date> getUnixEpochDateTypeAdapter() {
        return unixEpochDateTypeAdapter;
    }

    @Override
    public Date read(final JsonReader in)
            throws IOException {
        return new Date(TimeUnit.SECONDS.toMillis(in.nextLong()));
    }

    @Override
    public void write(final JsonWriter out, final Date value)
            throws IOException {
        out.value(null == value ? 0 : value.getTime()/1000);
    }

}