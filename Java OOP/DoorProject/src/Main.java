public class Main {
    public static void main(String[] args) {
        Vrata glavnaVrata = new Vrata();
        Vrata sporednaVrata = new Vrata();
        RepositoryOfExecutables.getInstance().add(new OtvaranjeVrata(glavnaVrata));
        RepositoryOfExecutables.getInstance().add(new OtvaranjeVrata(sporednaVrata));
        RepositoryOfExecutables.getInstance().add(new ZatvaranjeVrata(glavnaVrata));
        RepositoryOfExecutables.getInstance().add(new OtvaranjeVrata(glavnaVrata));
        RepositoryOfExecutables.getInstance().add(new ZatvaranjeVrata(glavnaVrata));
        RepositoryOfExecutables.getInstance().add(new OtvaranjeVrata(sporednaVrata));
        RepositoryOfExecutables.getInstance().add(new OtvaranjeVrata(sporednaVrata));
        RepositoryOfExecutables.getInstance().add(new ZatvaranjeVrata(sporednaVrata));
        RepositoryOfExecutables.getInstance().add(new ZatvaranjeVrata(sporednaVrata));


        while ( RepositoryOfExecutables.getInstance().count() > 0 ) {
            RepositoryOfExecutables.getInstance().executeNext();
        }

    }
}