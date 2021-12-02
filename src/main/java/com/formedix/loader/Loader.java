package com.formedix.loader;

import java.io.IOException;

public interface Loader<T> {
    T load() throws IOException;
}
