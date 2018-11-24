package hu.bubi.chef.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Recept.
 */
@Entity
@Table(name = "recept")
public class Recept implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "nev")
    private String nev;

    @Size(max = 10000)
    @Column(name = "leiras", length = 10000)
    private String leiras;

    @Lob
    @Column(name = "kep")
    private byte[] kep;

    @Column(name = "kep_content_type")
    private String kepContentType;

    @Column(name = "feltoltve")
    private LocalDate feltoltve;

    @ManyToOne
    @JsonIgnoreProperties("")
    private Kategoria kategoria;

    @ManyToMany
    @JoinTable(name = "recept_hashtagek",
               joinColumns = @JoinColumn(name = "recepts_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "hashtageks_id", referencedColumnName = "id"))
    private Set<HashTag> hashtageks = new HashSet<>();

    @OneToMany(mappedBy = "recept")
    private Set<ReceptToOsszetevo> osszetevoks = new HashSet<>();
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

    public Recept nev(String nev) {
        this.nev = nev;
        return this;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getLeiras() {
        return leiras;
    }

    public Recept leiras(String leiras) {
        this.leiras = leiras;
        return this;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public byte[] getKep() {
        return kep;
    }

    public Recept kep(byte[] kep) {
        this.kep = kep;
        return this;
    }

    public void setKep(byte[] kep) {
        this.kep = kep;
    }

    public String getKepContentType() {
        return kepContentType;
    }

    public Recept kepContentType(String kepContentType) {
        this.kepContentType = kepContentType;
        return this;
    }

    public void setKepContentType(String kepContentType) {
        this.kepContentType = kepContentType;
    }

    public LocalDate getFeltoltve() {
        return feltoltve;
    }

    public Recept feltoltve(LocalDate feltoltve) {
        this.feltoltve = feltoltve;
        return this;
    }

    public void setFeltoltve(LocalDate feltoltve) {
        this.feltoltve = feltoltve;
    }

    public Kategoria getKategoria() {
        return kategoria;
    }

    public Recept kategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
        return this;
    }

    public void setKategoria(Kategoria kategoria) {
        this.kategoria = kategoria;
    }

    public Set<HashTag> getHashtageks() {
        return hashtageks;
    }

    public Recept hashtageks(Set<HashTag> hashTags) {
        this.hashtageks = hashTags;
        return this;
    }

    public Recept addHashtagek(HashTag hashTag) {
        this.hashtageks.add(hashTag);
        hashTag.getRecepteks().add(this);
        return this;
    }

    public Recept removeHashtagek(HashTag hashTag) {
        this.hashtageks.remove(hashTag);
        hashTag.getRecepteks().remove(this);
        return this;
    }

    public void setHashtageks(Set<HashTag> hashTags) {
        this.hashtageks = hashTags;
    }

    public Set<ReceptToOsszetevo> getOsszetevoks() {
        return osszetevoks;
    }

    public Recept osszetevoks(Set<ReceptToOsszetevo> receptToOsszetevos) {
        this.osszetevoks = receptToOsszetevos;
        return this;
    }

    public Recept addOsszetevok(ReceptToOsszetevo receptToOsszetevo) {
        this.osszetevoks.add(receptToOsszetevo);
        receptToOsszetevo.setRecept(this);
        return this;
    }

    public Recept removeOsszetevok(ReceptToOsszetevo receptToOsszetevo) {
        this.osszetevoks.remove(receptToOsszetevo);
        receptToOsszetevo.setRecept(null);
        return this;
    }

    public void setOsszetevoks(Set<ReceptToOsszetevo> receptToOsszetevos) {
        this.osszetevoks = receptToOsszetevos;
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
        Recept recept = (Recept) o;
        if (recept.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), recept.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Recept{" +
            "id=" + getId() +
            ", nev='" + getNev() + "'" +
            ", leiras='" + getLeiras() + "'" +
            ", kep='" + getKep() + "'" +
            ", kepContentType='" + getKepContentType() + "'" +
            ", feltoltve='" + getFeltoltve() + "'" +
            "}";
    }
}
