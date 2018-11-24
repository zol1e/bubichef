package hu.bubi.chef.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A HashTag.
 */
@Entity
@Table(name = "hash_tag")
public class HashTag implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nev")
    private String nev;

    @ManyToMany(mappedBy = "hashtageks")
    @JsonIgnore
    private Set<Recept> recepteks = new HashSet<>();

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

    public HashTag nev(String nev) {
        this.nev = nev;
        return this;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public Set<Recept> getRecepteks() {
        return recepteks;
    }

    public HashTag recepteks(Set<Recept> recepts) {
        this.recepteks = recepts;
        return this;
    }

    public HashTag addReceptek(Recept recept) {
        this.recepteks.add(recept);
        recept.getHashtageks().add(this);
        return this;
    }

    public HashTag removeReceptek(Recept recept) {
        this.recepteks.remove(recept);
        recept.getHashtageks().remove(this);
        return this;
    }

    public void setRecepteks(Set<Recept> recepts) {
        this.recepteks = recepts;
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
        HashTag hashTag = (HashTag) o;
        if (hashTag.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), hashTag.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "HashTag{" +
            "id=" + getId() +
            ", nev='" + getNev() + "'" +
            "}";
    }
}
