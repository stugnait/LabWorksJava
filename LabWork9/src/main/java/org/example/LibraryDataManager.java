package org.example;

public interface LibraryDataManager {
    boolean doesLibraryExist(String libraryName);
    void save(Library library);
    Library read(String libraryName);
    void delete(String libraryName);
}
