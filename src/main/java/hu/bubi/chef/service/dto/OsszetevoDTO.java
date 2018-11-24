package hu.bubi.chef.service.dto;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Osszetevo entity.
 */
public class OsszetevoDTO implements Serializable {

    private Long id;

    private String nev;

    private String leiras;

    @Lob
    private byte[] kep;
    private String kepContentType;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OsszetevoDTO osszetevoDTO = (OsszetevoDTO) o;
        if (osszetevoDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), osszetevoDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "OsszetevoDTO{" +
            "id=" + getId() +
            ", nev='" + getNev() + "'" +
            ", leiras='" + getLeiras() + "'" +
            ", kep='" + getKep() + "'" +
            "}";
    }
}
