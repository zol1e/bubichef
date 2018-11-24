package hu.bubi.chef.domain;


import javax.persistence.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Kategoria.
 */
@Entity
@Table(name = "kategoria")
public class Kategoria implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nev")
    private String nev;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public Kategoria nev(String nev) {
        this.nev = nev;
        return this;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Kategoria kategoria = (Kategoria) o;
        if (kategoria.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), kategoria.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Kategoria{" +
            "id=" + getId() +
            ", nev='" + getNev() + "'" +
            "}";
    }
}
