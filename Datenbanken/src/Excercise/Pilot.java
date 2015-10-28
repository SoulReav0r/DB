package Excercise;

public class Pilot {
    private String m_name;
    private int m_points;

    public Pilot(final String name, final int points) {
        m_name = name;
        m_points = points;
    }

    public int getPoints() {
        return m_points;
    }

    public void addPoints(final int points) {
        m_points += points;
    }

    public String getName() {
        return m_name;
    }

    public String toString() {
        return m_name + "/" + m_points;
    }
}
