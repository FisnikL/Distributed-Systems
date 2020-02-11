package september2015me;

import java.util.Objects;

public class Movie {
    private String name;
    private long frames;

    public Movie(String name){
        this.name = name;
    }

    public Movie(String name, long frames) {
        this.name = name;
        this.frames = frames;
    }

    public String getName() {
        return name;
    }

    public long getFrames() {
        return frames;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return getName().equals(movie.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return "Movie{" +
                "name='" + name + '\'' +
                ", frames=" + frames +
                '}';
    }
}
