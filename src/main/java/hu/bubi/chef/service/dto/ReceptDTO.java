package hu.bubi.chef.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;

import hu.bubi.chef.domain.ReceptToOsszetevo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Recept entity.
 */
public class ReceptDTO implements Serializable {

    private Long id;

    private String nev;

    @Size(max = 10000)
    private String leiras;

    @Lob
    private byte[] kep;
    private String kepContentType;

    private LocalDate feltoltve;

    private Long kategoriaId;

    private Set<HashTagDTO> hashtageks = new HashSet<>();
    
    private Set<ReceptToOsszetevo> osszetevoks = new HashSet<>();

	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getLeiras() {
        return leiras;
    }

    public void setLeiras(String leiras) {
        this.leiras = leiras;
    }

    public byte[] getKep() {
        return kep;
    }

    public void setKep(byte[] kep) {
        this.kep = kep;
    }

    public String getKepContentType() {
        return kepContentType;
    }

    public void setKepContentType(String kepContentType) {
        this.kepContentType = kepContentType;
    }

    public LocalDate getFeltoltve() {
        return feltoltve;
    }

    public void setFeltoltve(LocalDate feltoltve) {
        this.feltoltve = feltoltve;
    }

    public Long getKategoriaId() {
        return kategoriaId;
    }

    public void setKategoriaId(Long kategoriaId) {
        this.kategoriaId = kategoriaId;
    }

    public Set<HashTagDTO> getHashtageks() {
        return hashtageks;
    }

    public void setHashtageks(Set<HashTagDTO> hashTags) {
        this.hashtageks = hashTags;
    }

    public Set<ReceptToOsszetevo> getOsszetevoks() {
		return osszetevoks;
	}

	public void setOsszetevoks(Set<ReceptToOsszetevo> osszetevoks) {
		this.osszetevoks = osszetevoks;
	}
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ReceptDTO receptDTO = (ReceptDTO) o;
        if (receptDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), receptDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReceptDTO{" +
            "id=" + getId() +
            ", nev='" + getNev() + "'" +
            ", leiras='" + getLeiras() + "'" +
            ", kep='" + getKep() + "'" +
            ", feltoltve='" + getFeltoltve() + "'" +
            ", kategoria=" + getKategoriaId() +
            "}";
    }
}
