package pl.firaanki;

public class FileDaoFactory {
    private FileDaoFactory() {

    }

    public static FileDao getFile(String filename) {
        return new FileDao(filename);
    }
}
