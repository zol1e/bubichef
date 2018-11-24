package hu.bubi.chef.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Osszetevo.
 */
@Entity
@Table(name = "osszetevo")
public class Osszetevo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nev")
    private String nev;

    @Column(name = "leiras")
    private String leiras;

    @Lob
    @Column(name = "kep")
    private byte[] kep;

    @Column(name = "kep_content_type")
    private String kepContentType;

    @OneToMany(mappedBy = "osszetevo")
    private Set<ReceptToOsszetevo> recepteks = new HashSet<>();
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

    public Osszetevo nev(String nev) {
        this.nev = nev;
        return this;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getLeiras() {
        return leiras;
    }

    public Osszetevo leiras(String leiras) {
        this.leiras = leiras;
        return this;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public byte[] getKep() {
        return kep;
    }

    public Osszetevo kep(byte[] kep) {
        this.kep = kep;
        return this;
    }

    public void setKep(byte[] kep) {
        this.kep = kep;
    }

    public String getKepContentType() {
        return kepContentType;
    }

    public Osszetevo kepContentType(String kepContentType) {
        this.kepContentType = kepContentType;
        return this;
    }

    public void setKepContentType(String kepContentType) {
        this.kepContentType = kepContentType;
    }

    public Set<ReceptToOsszetevo> getRecepteks() {
        return recepteks;
    }

    public Osszetevo recepteks(Set<ReceptToOsszetevo> receptToOsszetevos) {
        this.recepteks = receptToOsszetevos;
        return this;
    }

    public Osszetevo addReceptek(ReceptToOsszetevo receptToOsszetevo) {
        this.recepteks.add(receptToOsszetevo);
        receptToOsszetevo.setOsszetevo(this);
        return this;
    }

    public Osszetevo removeReceptek(ReceptToOsszetevo receptToOsszetevo) {
        this.recepteks.remove(receptToOsszetevo);
        receptToOsszetevo.setOsszetevo(null);
        return this;
    }

    public void setRecepteks(Set<ReceptToOsszetevo> receptToOsszetevos) {
        this.recepteks = receptToOsszetevos;
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
        Osszetevo osszetevo = (Osszetevo) o;
        if (osszetevo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), osszetevo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Osszetevo{" +
            "id=" + getId() +
            ", nev='" + getNev() + "'" +
            ", leiras='" + getLeiras() + "'" +
            ", kep='" + getKep() + "'" +
            ", kepContentType='" + getKepContentType() + "'" +
            "}";
    }
}
