package hu.bubi.chef.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A ReceptToOsszetevo.
 */
@Entity
@Table(name = "recept_to_osszetevo")
public class ReceptToOsszetevo implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Size(max = 50)
    @Column(name = "mennyiseg", length = 50)
    private String mennyiseg;

    @Column(name = "megjegyzes")
    private String megjegyzes;

    @ManyToOne
    @JsonIgnoreProperties("osszetevoks")
    private Recept recept;

    @ManyToOne
    @JsonIgnoreProperties("recepteks")
    private Osszetevo osszetevo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMennyiseg() {
        return mennyiseg;
    }

    public ReceptToOsszetevo mennyiseg(String mennyiseg) {
        this.mennyiseg = mennyiseg;
        return this;
    }

    public void setMennyiseg(String mennyiseg) {
        this.mennyiseg = mennyiseg;
    }

    public String getMegjegyzes() {
        return megjegyzes;
    }

    public ReceptToOsszetevo megjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
        return this;
    }

    public void setMegjegyzes(String megjegyzes) {
        this.megjegyzes = megjegyzes;
    }

    public Recept getRecept() {
        return recept;
    }

    public ReceptToOsszetevo recept(Recept recept) {
        this.recept = recept;
        return this;
    }

    public void setRecept(Recept recept) {
        this.recept = recept;
    }

    public Osszetevo getOsszetevo() {
        return osszetevo;
    }

    public ReceptToOsszetevo osszetevo(Osszetevo osszetevo) {
        this.osszetevo = osszetevo;
        return this;
    }

    public void setOsszetevo(Osszetevo osszetevo) {
        this.osszetevo = osszetevo;
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
        ReceptToOsszetevo receptToOsszetevo = (ReceptToOsszetevo) o;
        if (receptToOsszetevo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), receptToOsszetevo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReceptToOsszetevo{" +
            "id=" + getId() +
            ", mennyiseg='" + getMennyiseg() + "'" +
            ", megjegyzes='" + getMegjegyzes() + "'" +
            "}";
    }
}
